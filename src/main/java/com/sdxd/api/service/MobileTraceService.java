package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.approve.dubbo.api.PhoneNumInfoDubboService;
import com.sdxd.approve.dubbo.api.request.PhoneRequest;
import com.sdxd.approve.dubbo.api.response.CellInfoResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.UserQueryService;
import com.sdxd.user.api.UserRuleService;
import com.sdxd.user.api.request.UserBaseRequest;
import org.springframework.stereotype.Service;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.service
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/28     melvin                 Created
 */
@Service
public class MobileTraceService {

    @Reference(version = "1.0.0")
    private UserQueryService userQueryService;

    @Reference(version = "1.0.0")
    private UserRuleService userRuleService;

    @Reference(version = "1.0.0")
    private PhoneNumInfoDubboService phoneNumInfoDubboService;

    public Integer getCountOfSameDevice(long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<Integer> response = userRuleService.deviceMatchNum(request);
        return data(response);
    }

    public Integer getUserAddressCount(long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<Integer> response = userQueryService.addressListSize(request);
        return data(response);
    }

    public CellInfoResponse getUserMobileInfo(long userId) throws ProcessBizException {
        PhoneRequest request = new PhoneRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<CellInfoResponse> response = phoneNumInfoDubboService.analyzeRawData(request);
        return data(response);
    }
}
