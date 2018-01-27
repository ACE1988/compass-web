package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.lending.DailyReportQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.finance.LoanDailyDubboService;
import com.sdxd.data.dubbo.api.finance.RepaymentDailyDubboService;
import com.sdxd.data.dubbo.api.request.LoanDailyRequest;
import com.sdxd.data.dubbo.api.request.RepaymentDailyRequest;
import com.sdxd.data.dubbo.api.response.pojo.ReportLoanDailyBo;
import com.sdxd.data.dubbo.api.response.pojo.ReportRepaymentDailyBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
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
 * 17/3/16     melvin                 Created
 */
@Service
public class ReportService {

    @Reference(version = "1.0.0")
    private LoanDailyDubboService loanDailyDubboService;

    @Reference(version = "1.0.0")
    private RepaymentDailyDubboService repaymentDailyDubboService;

    public PaginationSupport<ReportLoanDailyBo> getLendingDailyReport(DailyReportQuery query) throws ProcessBizException {
        LoanDailyRequest request = new LoanDailyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<PaginationSupport<ReportLoanDailyBo>> response = loanDailyDubboService.query(request);
        return data(response);
    }

    public Integer countLendingDailyReport(DailyReportQuery query) throws ProcessBizException {
        LoanDailyRequest request = new LoanDailyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<Integer> response = loanDailyDubboService.count(request);
        return data(response);
    }

    public PaginationSupport<ReportRepaymentDailyBo> getRepaymentDailyReport(DailyReportQuery query) throws ProcessBizException {
        RepaymentDailyRequest request = new RepaymentDailyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<PaginationSupport<ReportRepaymentDailyBo>> response = repaymentDailyDubboService.query(request);
        return data(response);
    }

    public Integer countRepaymentDailyReport(DailyReportQuery query) throws ProcessBizException {
        RepaymentDailyRequest request = new RepaymentDailyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());

        DubboResponse<Integer> response = repaymentDailyDubboService.count(request);
        return data(response);
    }
}
