package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.service.RepaymentOfflineDetailService;
import com.sdxd.api.util.RepaymentOfflinePrivateUtil;
import com.sdxd.api.util.RepaymentOfflineSheetUtil;
import com.sdxd.api.vo.lending.PayOfflineClaimQuery;
import com.sdxd.api.vo.lending.PayOfflineLogsQuery;
import com.sdxd.api.vo.lending.RepaymentOfflineApproveCommit;
import com.sdxd.api.vo.lending.RepaymentOfflineDetailQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.RepaymentOfflineDocumentDubboService;
import com.sdxd.repayment.dubbo.request.OfflineBankDocument;
import com.sdxd.repayment.dubbo.request.RepaymentOfflineDetailRequest;
import com.sdxd.repayment.dubbo.request.RepaymentOfflineQueryRequest;
import com.sdxd.repayment.dubbo.response.RepaymentOfflineDetailResponse;
import com.sdxd.repayment.dubbo.response.RepaymentOfflineLogResponse;
import com.sdxd.repayment.dubbo.response.RepaymentOfflineResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.RestResponse.ok;


/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.lending.pvt.v1_0_0
 * 系统名           ：线下对公还款管理
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/06/26    刘节                 Created
 */
@Api(value = "Parameter-API", produces = "application/json")
@Controller
@RequestMapping(value = "/repayment-offline", produces = "application/json")
public class RepaymentOfflineController {

    private Logger LOGGER = LoggerFactory.getLogger(RepaymentOfflineController.class);

    @Autowired
    private RepaymentOfflineDetailService repaymentOfflineDetailService;
    @Reference(version = "1.0.0")
    private RepaymentOfflineDocumentDubboService repaymentOfflineDocumentDubboService;

    @Autowired
    private AdminService adminService;


    @ApiOperation(value = "上传对公,私账单", notes = "上传对公,私账单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public synchronized RestResponse<Boolean> saveOrUpdate(HttpServletRequest request,
                                                           @RequestParam(value = "channel") String bankName,
                                                           @RequestParam(value = "acceptCardNo") String acceptCardNo,
                                                           @ContextParam(value = "principal") String account)
            throws IOException, ProcessBizException, ParseException {
        String type = request.getParameter("type");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = null;
        if ("1".equals(type)) {
            file = multipartRequest.getFile("file");// 获取上传文件名
        } else {
            file = multipartRequest.getFile("attachFile");// 获取上传文件名
        }
        InputStream inputStream = file.getInputStream();

        AdminUserResponse admin = adminService.getAdminById(account);
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<OfflineBankDocument> list = null;
        if (prefix.equalsIgnoreCase("xls") || prefix.equalsIgnoreCase("xlsx")) {
            if ("SPDB".equals(bankName)) {
//                  这是对公的数据返回
                list = new RepaymentOfflineSheetUtil(inputStream, prefix).parse(account, admin.getName(), bankName, acceptCardNo);
            } else {
//                  对私的数据返回
                list = new RepaymentOfflinePrivateUtil(inputStream, prefix).privateParse(account, admin.getName(), bankName, acceptCardNo);
            }
        }
        LOGGER.info("【财务系统-对公,私管理】=========================》 上传列表：" + JSON.toJSON(list));
        DubboResponse response = repaymentOfflineDetailService.repaymentOfflineUploadFile(list);
        return rest(response);
    }

    @ApiOperation(value = "对公列表查询", notes = "对公列表查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<RepaymentOfflineResponse>> payOfflineDetail(
            @BeanParam RepaymentOfflineDetailQuery query) {
        try {
            PaginationSupport<RepaymentOfflineResponse> page =
                    repaymentOfflineDetailService.queryRepaymentOfflineDetail(query);
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }


    @ApiOperation(value = "审核、删除线下对公单", notes = "审核、删除线下对公单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/approve", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> approve(
            @Valid @RequestBody RepaymentOfflineApproveCommit query, @ContextParam(value = "principal") String account
    ) {
        try {
            AdminUserResponse admin = adminService.getAdminById(account);
            DubboResponse<Boolean> response = repaymentOfflineDetailService.approveOnFinance(query, account, admin.getName());
            return rest(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }


    @ApiOperation(value = "订单日志", notes = "订单日志")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<RepaymentOfflineLogResponse>> logs(
            @BeanParam PayOfflineLogsQuery query
    ) {
        DubboResponse<PaginationSupport<RepaymentOfflineLogResponse>> response =
                repaymentOfflineDetailService.queryLogPage(query);
        return rest(response);
    }


    @ApiOperation(value = "线下还款认领(未逾期认领)", notes = "线下还款认领(未逾期认领)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/pay-offline-claim", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> payOfflineClaim(
            @ContextParam(value = "principal") String operatorId
            , @Valid @RequestBody PayOfflineClaimQuery query) {
        DubboResponse<Boolean> response
                = repaymentOfflineDetailService.approveOffline(
                operatorId, query.getApplyId()
                , query.getStatus(), query.getIds());
        return rest(response);
    }

    @ApiOperation(value = "认领细节查询", notes = "认领细节查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/pay-offline-detail-claim", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<RepaymentOfflineDetailResponse> getClaimDetail(
            @RequestParam(value = "applyId") String applyId,
            @RequestParam(value = "offLineId") String offLineId) {
        RepaymentOfflineDetailRequest request =
                new RepaymentOfflineDetailRequest();
        request.setApplyId(applyId);
        request.setOffLineId(Lists.newArrayList(offLineId));
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<RepaymentOfflineDetailResponse> reponse =
                repaymentOfflineDocumentDubboService.queryDetail(request);
        return rest(reponse);
    }

    @ApiOperation(value = "对公根据applyId详细查询", notes = "对公根据applyId详细查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "detail-by-docCode", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<RepaymentOfflineResponse>> getDetailById(
            @RequestParam(value = "docCode") String docCode) {
        RepaymentOfflineQueryRequest request = new RepaymentOfflineQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setDocCode(docCode);
        DubboResponse<PaginationSupport<RepaymentOfflineResponse>>
                response = repaymentOfflineDocumentDubboService.queryPage(request);
        return rest(response);
    }

}