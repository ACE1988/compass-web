package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.approve.dubbo.api.PhoneNumInfoDubboService;
import com.sdxd.approve.dubbo.api.request.PhoneRequest;
import com.sdxd.approve.dubbo.api.response.CellInfoResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.risk.carrier.CarrierDataDubboService;
import com.sdxd.data.dubbo.api.risk.carrier.dto.*;
import com.sdxd.framework.dto.PaginationSupport;
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
    private CarrierDataDubboService carrierDataDubboService;

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

    public String getMobileOnlineTime(String phone) throws ProcessBizException {
        //获取在网时长
        CellDetailRequest request = new CellDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        DubboResponse<CellDetailInfoReponse> response = carrierDataDubboService.getCellDetailInfo(request);
        return data(response, value -> {
            Integer usedMonth = value.getUsedMonth();
            return usedMonth == null ? "未知" : String.valueOf(usedMonth);
        });
    }

    public PaginationSupport<Call> getUserMobileData(String phone, PageParameter page) throws ProcessBizException {
        CellInfoRequest request = new CellInfoRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        request.setCurrentPage(page.getPageNo());
        request.setPageSize(page.getPageSize());
        DubboResponse<PaginationSupport<Call>> response = carrierDataDubboService.cellInfoData(request);
        return data(response);
    }

    public PaginationSupport<Call> getContactMobileData(String cellphone, String contactPhone, PageParameter page)
            throws ProcessBizException {
        CellInfoRequest request = new CellInfoRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(cellphone);
        request.setContactNum(contactPhone);
        request.setCurrentPage(page.getPageNo());
        request.setPageSize(page.getPageSize());
        DubboResponse<PaginationSupport<Call>> response = carrierDataDubboService.cellInfoData(request);
        return data(response);
    }

    public PhoneCellInfoResponse getContactMobileMatchData(String cellphone, String contactPhone) {
        ContactInfoRequest request = new ContactInfoRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(cellphone);
        request.setContactNum(contactPhone);
        DubboResponse<PhoneCellInfoResponse> response = carrierDataDubboService.getCellCount(request);
        try {
            return data(response);
        } catch (ProcessBizException e) {
            return null;
        }
    }
}
