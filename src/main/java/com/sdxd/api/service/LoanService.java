package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.sdxd.api.vo.credit.CreditLine;
import com.sdxd.apply.api.ApplyAdmittanceDubboService;
import com.sdxd.apply.api.ApplyDubboService;
import com.sdxd.apply.request.ApplyListPageRequest;
import com.sdxd.apply.request.BaseUserIdRequest;
import com.sdxd.apply.response.ApplyAdmittanceDetailResponse;
import com.sdxd.apply.response.ApplyMoneyInfo;
import com.sdxd.approve.dubbo.api.PagingQuery;
import com.sdxd.approve.dubbo.api.request.QueryApproveRequest;
import com.sdxd.approve.dubbo.api.response.ApproveApplicationResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.creditline.api.CreditLineDubboService;
import com.sdxd.creditline.request.SingleUserIdRequest;
import com.sdxd.creditline.response.CreditLineInfo;
import com.sdxd.data.dubbo.api.crm.ApplyQueryDubboService;
import com.sdxd.data.dubbo.api.request.ApplyStepStatusRequest;
import com.sdxd.data.dubbo.api.response.ApplyStepStatus;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.loan.api.LoanDubboService;
import com.sdxd.repayment.dubbo.RepaymentPlanDubboService;
import com.sdxd.repayment.dubbo.request.RepaymentRequest;
import com.sdxd.repayment.dubbo.response.RepaymentInfo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.copy;
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
 * 17/1/9     melvin                 Created
 */
@Service
public class LoanService {

    @Reference(version = "1.0.0")
    private ApplyAdmittanceDubboService applyAdmittanceDubboService;

    @Reference(version = "1.0.0")
    private LoanDubboService loanDubboService;

    @Reference(version = "1.0.0")
    private ApplyDubboService applyDubboService;

    @Reference(version = "1.0.0")
    private CreditLineDubboService creditLineDubboService;

    @Reference(version = "1.0.0")
    private RepaymentPlanDubboService repaymentPlanDubboService;

    @Reference(version = "1.0.0")
    private PagingQuery pagingQuery;

    @Reference(version = "1.0.0")
    private ApplyQueryDubboService applyQueryDubboService;//queryApplyStepStatus;

    public ApplyStepStatus getCustomerStatus(String applyAdmittanceId) throws ProcessBizException {
        ApplyStepStatusRequest request = new ApplyStepStatusRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(applyAdmittanceId);
        DubboResponse<ApplyStepStatus> response = applyQueryDubboService.queryApplyStepStatus(request);
        return data(response);
    }

    public ApproveApplicationResponse getAdmittanceByCustomer(Long userId, String applyAdmittanceId) throws ProcessBizException {
        QueryApproveRequest request = new QueryApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserIds(Lists.newArrayList(userId));
        request.setAdmittanceId(applyAdmittanceId);
        request.setCurrentPage(1);
        request.setPageSize(1);
        DubboResponse<PaginationSupport<ApproveApplicationResponse>> response = pagingQuery.queryOnPage(request);
        PaginationSupport<ApproveApplicationResponse> page = data(response);
        List<ApproveApplicationResponse> list = page == null ? null : page.getList();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public RepaymentInfo getRepaymentPlan(long userId) throws ProcessBizException {
        RepaymentRequest request = new RepaymentRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<RepaymentInfo> response = repaymentPlanDubboService.getRepayments(request);
        return data(response, result -> result == null || result.getRepaymentPlanInfoExt() == null ? null : result);
    }

    public CreditLine getCreditLine(long userId) throws ProcessBizException {
        SingleUserIdRequest request = new SingleUserIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<CreditLineInfo> response = creditLineDubboService.getByUserId(request);
        return data(response, creditLine -> copy(creditLine, new CreditLine()));
    }

    public Integer getInLoanCount(long userId) throws ProcessBizException {
        BaseUserIdRequest request = new BaseUserIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<Integer> response = applyDubboService.getProccessingAmount(request);
        return data(response);
    }

    public ApplyAdmittanceDetailResponse getAdmittanceApplicationByCustomer(Long userId) throws ProcessBizException {
        BaseUserIdRequest request = new BaseUserIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<ApplyAdmittanceDetailResponse> response = applyAdmittanceDubboService.getAdmittanceDetail(request);
        return data(response);
    }

    public PaginationSupport<ApplyMoneyInfo> findLoanInfoByCustomer(
            Long customerId, Integer pageNo, Integer pageSize) throws ProcessBizException {
        ApplyListPageRequest request = new ApplyListPageRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(pageNo);
        request.setPageSize(pageSize);
        request.setUserId(customerId);
        DubboResponse<PaginationSupport<ApplyMoneyInfo>> response = applyDubboService.getApplyPageList(request);
        return data(response);
    }
}
