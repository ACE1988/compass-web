package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.api.vo.customer.CustomerEvent;
import com.sdxd.api.vo.customer.CustomerEventQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.customer.dubbo.api.CustomerEventsDubboService;
import com.sdxd.customer.dubbo.api.request.CustomerEventsCreateRequest;
import com.sdxd.customer.dubbo.api.request.CustomerEventsRequest;
import com.sdxd.customer.dubbo.api.response.CustomerEventsCreateResponse;
import com.sdxd.customer.dubbo.api.response.CustomerEventsInfo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.response.UserInfo;
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
 * 17/1/11     melvin                 Created
 */
@Service
public class CustomerEventService {

    @Reference(version = "1.0.0")
    private CustomerEventsDubboService customerEventsDubboService;

    public PaginationSupport<CustomerEventsInfo> search(
            CustomerEventQuery query, Integer pageNo, Integer pageSize) throws ProcessBizException {
        CustomerEventsRequest request = new CustomerEventsRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(pageNo);
        request.setPageSize(pageSize);
        request.setUserId(query.getUserId());
        request.setPhoneNum(query.getIncomingPhone());
        request.setAnswerStartTime(query.getIncomingTimeFrom());
        request.setAnswerEndTime(query.getIncomingTimeTo());
        request.setCallMatter(query.getEventType());
        request.setCustomerId(query.getReceptionistId());
        request.setCustomerName(query.getReceptionist());
        request.setId(query.getEventNo());
        request.setType(query.getCustomerType() == null ? null : query.getCustomerType().getCode());
        request.setStatus(query.getStatus() == null ? null : query.getStatus().getCode());
        DubboResponse<PaginationSupport<CustomerEventsInfo>> response = customerEventsDubboService.queryCusEvensPage(request);
        return data(response);
    }

    public CustomerEventsCreateResponse createEvent(
            CustomerEvent event, UserInfo customer, AdminUserResponse admin, String operatorId) throws ProcessBizException {
        String customerName =
                (customer != null && customer.getUserProfileIdentityInfo() != null) ?
                        customer.getUserProfileIdentityInfo().getName() : null;
        CustomerEventsCreateRequest request = new CustomerEventsCreateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCustomerId(operatorId);
        request.setCustomerName(admin.getName());
        request.setMainProblem(event.getProblemType());
        request.setCallMatter(event.getEventType());
        request.setPhoneNum(event.getIncomingPhone());
        request.setAnswerTime(event.getReceptionTime());
        request.setDetailedContent(event.getContent());
        request.setStatus(event.getStatus() == null ? null : event.getStatus().getCode());
        request.setUserId(event.getUserId());
        request.setUserName(customerName);
        DubboResponse<CustomerEventsCreateResponse> response = customerEventsDubboService.saveCustomerEvens(request);
        return data(response);
    }
}
