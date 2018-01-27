package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.service.RepaymentOfflineDetailService;
import com.sdxd.api.util.IncreaseLineHistoryBatchImportOfflineUtil;
import com.sdxd.api.vo.credit.IncreaseLineHistory;
import com.sdxd.api.vo.credit.IncreaseLineHistoryDetail;
import com.sdxd.api.vo.credit.UpLoadStatus;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.util.JsonUtil;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.creditline.api.CreditLineDubboService;
import com.sdxd.creditline.request.SingleUserIdRequest;
import com.sdxd.creditline.request.UpdateCreditTotalRequest;
import com.sdxd.creditline.response.CreditLineInfo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.response.UserProfileIdentityInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.sdxd.common.web.trace.HttpTracer.DEBUG;
import static com.sdxd.common.web.util.MapUtil.$;
import static com.sdxd.common.web.util.ResponseUtil.isSuccessfulResponse;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * Created by lenovo on 2017/7/24.
 */

@Api(value = "Increase-Line-History-API", produces = "application/json")
@Controller
@RequestMapping(value = "/increase-line")
public class IncreaseLineController {

    private static final Logger log = LoggerFactory.getLogger(IncreaseLineController.class);

    @Autowired
    private RepaymentOfflineDetailService repaymentOfflineDetailService;

    @Autowired
    private ProfileService profileService;

    @Reference(version = "1.0.0")
    private CreditLineDubboService creditLineDubboService;

    @ApiOperation(value = "提额批量上传", notes = "提额批量上传")
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
    @RequestMapping(value = "/batch-import", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<UpLoadStatus> batchImport(HttpServletRequest request) {
        UpLoadStatus upLoadStatus = new UpLoadStatus();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = null;
        file = multipartRequest.getFile("excel_file");// 获取上传文件名
        if (null == file) {
            //判断file是否为null
            upLoadStatus.addError("上传文件未找到");
            return ok(upLoadStatus);
        }
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("", e);
            return ok(upLoadStatus);
        }
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<IncreaseLineHistoryDetail> list
                = new IncreaseLineHistoryBatchImportOfflineUtil(inputStream, prefix).parse();
        if (null == list) {
            upLoadStatus.addError("文件解析失败");
            return ok(upLoadStatus);
        }
        //调用dubbo的接口
        for (IncreaseLineHistoryDetail increaseLineHistoryDetail : list) {
            DEBUG(log, "Increase credit for {}", JsonUtil.toJson(increaseLineHistoryDetail));
            if (StringUtils.isBlank(increaseLineHistoryDetail.getPhone())) {
                upLoadStatus.addError("手机号没填");
                break;
            }
            if (StringUtils.isBlank(increaseLineHistoryDetail.getCurrentCreditLine())) {
                upLoadStatus.addError("当前额度没填");
                break;
            }
            if (StringUtils.isBlank(increaseLineHistoryDetail.getTargetCreditLine())) {
                upLoadStatus.addError("提升后额度没填");
                break;
            }
            String phone = increaseLineHistoryDetail.getPhone();
            Long userId = null;
            try {
                userId = profileService.getUserId(phone);
                if (null == userId) {
                    upLoadStatus.addError(String.format("通过手机号码%s未找到用户", phone));
                    continue;
                }
            } catch (ProcessBizException e) {
                return e.toResult();
            }
            UpdateCreditTotalRequest updateCreditTotalRequest = new UpdateCreditTotalRequest();
            updateCreditTotalRequest.setRequestId(BillNoUtils.GenerateBillNo());
            updateCreditTotalRequest.setPhone(increaseLineHistoryDetail.getPhone());
            updateCreditTotalRequest.setUserName(increaseLineHistoryDetail.getName());
            updateCreditTotalRequest.setUserId(userId);
            int amount = 0;
            try {
                amount = Integer.valueOf(increaseLineHistoryDetail.getTargetCreditLine()) - Integer.valueOf(increaseLineHistoryDetail.getCurrentCreditLine());
//                if (amount <= 0) {
//                    //如果额度<0
//                    return ok(upLoadStatus);
//                }
            } catch (Exception e) {
                upLoadStatus.addError(String.format("当前额度%s或者目标额度%s不正确", increaseLineHistoryDetail.getCurrentCreditLine(), increaseLineHistoryDetail.getTargetCreditLine()));
                continue;

            }
            updateCreditTotalRequest.setAmount(amount);
            SingleUserIdRequest singleUserIdRequest = new SingleUserIdRequest();
            singleUserIdRequest.setUserId(userId);
            singleUserIdRequest.setRequestId(BillNoUtils.GenerateBillNo());
            DubboResponse<CreditLineInfo> dubboResponse = creditLineDubboService.getByUserId(singleUserIdRequest);
            if (null == dubboResponse || null == dubboResponse.getData()) {
                //如果为空，跳出
                upLoadStatus.addError($(phone, dubboResponse));
                break;
            }
            if (null != increaseLineHistoryDetail.getCurrentCreditLine()) {
                //如果不为null
                //当前额度
                int CurrentCreditLineIntValue =
                        Integer.valueOf(increaseLineHistoryDetail.getCurrentCreditLine()).intValue();
                CreditLineInfo creditLineInfo = dubboResponse.getData();
                int total = null == creditLineInfo.getTotal() ? 0 : creditLineInfo.getTotal();
                int used = null == creditLineInfo.getUsed() ? 0 : creditLineInfo.getUsed();
                int frozen = null == creditLineInfo.getFrozen() ? 0 : creditLineInfo.getFrozen();
                //数据库额度
                int storeCreditLineIntValue = total + used + frozen;
                if (CurrentCreditLineIntValue == storeCreditLineIntValue) {
                    //入库
                    DubboResponse<String> response =
                            creditLineDubboService.updateCreditTotal(updateCreditTotalRequest);
                    if (!isSuccessfulResponse(response)) {
                        upLoadStatus.addError($(phone, response));
                    }
                }
            }
        }
        return ok(upLoadStatus);
    }


    @ApiOperation(value = "查询提额历史", notes = "查询提额历史")
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
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<IncreaseLineHistory>> query(@ApiParam(name = "phone", value = "手机号", required = false)
                                                                      @RequestParam(value = "phone", required = false) String phone, @BeanParam PageParameter page) {
        Long userId = null;
        List<IncreaseLineHistory> list = new ArrayList<>();
        //返回空list
        PaginationSupport<IncreaseLineHistory> blankPaginationSupport
                = new PaginationSupport<>(list, -1);
        blankPaginationSupport.setCurrentIndex(page.getPageNo());
        blankPaginationSupport.setPageSize(page.getPageSize());
        if (!StringUtils.isBlank(phone)) {
            try {
                userId = profileService.getUserId(phone);
            } catch (Exception e) {
                return ok(blankPaginationSupport);
            }
        } else {
            return ok(blankPaginationSupport);
        }
        UserProfileIdentityInfo userProfileIdentityInfo;
        try {
            userProfileIdentityInfo = profileService.getIdentityInfo(userId);
        } catch (ProcessBizException e) {
            return ok(blankPaginationSupport);
        }
        String name = null == userProfileIdentityInfo ? "-" : userProfileIdentityInfo.getName();
        name = StringUtils.isBlank(name) ? "-" : name;
        //dubbo请求的request
        SingleUserIdRequest singleUserIdRequest = new SingleUserIdRequest();
        singleUserIdRequest.setUserId(userId);
        singleUserIdRequest.setRequestId(BillNoUtils.GenerateBillNo());
        //调用dubbo接口
        DubboResponse<CreditLineInfo> response =
                creditLineDubboService.getByUserId(singleUserIdRequest);
        //判断空指针
        if (null == response || null == response.getData()) {
            return ok(blankPaginationSupport);
        }
        CreditLineInfo creditLineInfo = response.getData();
        IncreaseLineHistory increaseLineHistory = new IncreaseLineHistory();
        increaseLineHistory.setId(1);
        increaseLineHistory.setPhone(phone);
        int total = null == creditLineInfo.getTotal() ? 0 : creditLineInfo.getTotal();
        int used = null == creditLineInfo.getUsed() ? 0 : creditLineInfo.getUsed();
        int frozen = null == creditLineInfo.getFrozen() ? 0 : creditLineInfo.getFrozen();
        //数据库额度
        Long storeCreditLineIntValue = new Long(total + used + frozen);
        increaseLineHistory.setCreditLine(storeCreditLineIntValue.toString());
        increaseLineHistory.setName(name);
        list.add(increaseLineHistory);
        PaginationSupport<IncreaseLineHistory> paginationSupport
                = new PaginationSupport<>(list, -1);
        paginationSupport.setCurrentIndex(page.getPageNo());
        paginationSupport.setPageSize(page.getPageSize());
        return ok(paginationSupport);
    }


}


