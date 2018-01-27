package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.RepaymentDetailService;
import com.sdxd.api.vo.lending.RepaymentDetailQuery;
import com.sdxd.api.vo.lending.RepaymentLanXinQuery;
import com.sdxd.api.vo.lending.RepaymentSdxdQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.finance.LanxinRepaymentDetailDubboService;
import com.sdxd.data.dubbo.api.finance.SdxdRepaymentDetailDubboService;
import com.sdxd.data.dubbo.api.request.LanxinRepaymentDetailRequest;
import com.sdxd.data.dubbo.api.request.SdxdRepaymentDetailRequest;
import com.sdxd.data.dubbo.api.response.pojo.*;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.lending.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/15     melvin                 Created
 */
@Api(value = "Repayment-Details-API", produces = "application/json")
@Controller
@RequestMapping(value = "/repayment-details", produces = "application/json")
public class    RepaymentDetailsController {

    @Reference(version = "1.0.0")
    private SdxdRepaymentDetailDubboService sdxdRepaymentDetailDubboService;

    @Reference(version = "1.0.0")
    private LanxinRepaymentDetailDubboService lanxinRepaymentDetailDubboService;

    @Autowired
    private RepaymentDetailService repaymentDetailService;

    @ApiOperation(value = "获取还款详情列表（小贷）", notes = "获取还款详情列表（小贷）")
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
    @RequestMapping(value = "/sdxd", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ReportSdxdRepaymentDetailBo>> findSdxdRepayments(
            @BeanParam RepaymentSdxdQuery query
    ) {
        SdxdRepaymentDetailRequest request = new SdxdRepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setPhone(query.getPhone());
        request.setStatus(query.getStatus() == null ? null : query.getStatus().getValue());
        request.setStartLoanTime(query.getStartLoanTime());
        request.setEndLoanTime(query.getEndLoanTime());
        request.setStartRepaymentTime(query.getStartRepaymentTime());
        request.setEndRepaymentTime(query.getEndRepaymentTime());
        request.setMinOverdueDays(query.getMinOverdueDays());
        request.setMaxOverdueDays(query.getMaxOverdueDays());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<PaginationSupport<ReportSdxdRepaymentDetailBo>> response = sdxdRepaymentDetailDubboService.query(request);
        return rest(response);
    }

    @ApiOperation(value = "获取还款详情列表总计（小贷）", notes = "获取还款详情列表总计（小贷）")
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
    @RequestMapping(value = "/sdxd-sum", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ReportSdxdRepaymentDetailSumBo> sumSdxdRepayments(
            @BeanParam RepaymentSdxdQuery query
    ) {
        SdxdRepaymentDetailRequest request = new SdxdRepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setPhone(query.getPhone());
        request.setStatus(query.getStatus() == null ? null : query.getStatus().getValue());
        request.setStartLoanTime(query.getStartLoanTime());
        request.setEndLoanTime(query.getEndLoanTime());
        request.setStartRepaymentTime(query.getStartRepaymentTime());
        request.setEndRepaymentTime(query.getEndRepaymentTime());
        request.setMinOverdueDays(query.getMinOverdueDays());
        request.setMaxOverdueDays(query.getMaxOverdueDays());

        DubboResponse<ReportSdxdRepaymentDetailSumBo> response = sdxdRepaymentDetailDubboService.sum(request);
        return rest(response);
    }

    @ApiOperation(value = "获取还款详情列表（蓝薪）", notes = "获取还款详情列表（蓝薪）")
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
    @RequestMapping(value = "/lan-xin", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ReportLanxinRepaymentDetailBo>> findLanXinRepayments(
            @BeanParam RepaymentLanXinQuery query
    ) {
        LanxinRepaymentDetailRequest request = new LanxinRepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setPhone(query.getPhone());
        request.setStatus(query.getStatus() == null ? null : query.getStatus().getValue());
        request.setStartRepaymentCreateTime(query.getStartRepaymentTime());
        request.setEndRepaymentCreateTime(query.getEndRepaymentTime());
        request.setMinOverdueDays(query.getMinOverdueDays());
        request.setMaxOverdueDays(query.getMaxOverdueDays());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<PaginationSupport<ReportLanxinRepaymentDetailBo>> response = lanxinRepaymentDetailDubboService.query(request);
        return rest(response);
    }

    @ApiOperation(value = "获取还款详情列表总计（蓝薪）", notes = "获取还款详情列表总计（蓝薪）")
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
    @RequestMapping(value = "/lan-xin-sum", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ReportLanxinRepaymentDetailSumBo> sumLanXinRepayments(
            @BeanParam RepaymentLanXinQuery query
    ) {
        LanxinRepaymentDetailRequest request = new LanxinRepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setPhone(query.getPhone());
        request.setStatus(query.getStatus() == null ? null : query.getStatus().getValue());
        request.setStartRepaymentCreateTime(query.getStartRepaymentTime());
        request.setEndRepaymentCreateTime(query.getEndRepaymentTime());
        request.setMinOverdueDays(query.getMinOverdueDays());
        request.setMaxOverdueDays(query.getMaxOverdueDays());

        DubboResponse<ReportLanxinRepaymentDetailSumBo> response = lanxinRepaymentDetailDubboService.sum(request);
        return rest(response);
    }



    @ApiOperation(value = "还款单查询", notes = "还款单查询")
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
    @RequestMapping(value = "/repayment_detail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<RepaymentDetailBo>> repaymentDetail(
            @BeanParam RepaymentDetailQuery query
    ) {

        try {
            PaginationSupport<RepaymentDetailBo> page =
                    forValue(new PaginationSupport<RepaymentDetailBo>(null, 0)).
                            parallel(() -> repaymentDetailService.repaymentDetail(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> repaymentDetailService.repaymentDetailCount(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
