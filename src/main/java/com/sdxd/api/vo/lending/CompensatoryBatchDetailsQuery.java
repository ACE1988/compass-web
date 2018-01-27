package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/9/15     melvin                 Created
 */
public class CompensatoryBatchDetailsQuery extends RestRequest {

    @ApiParam(value = "批次号")
    @QueryParam(value = "batch_id")
    private String batchId;

    @ApiParam(value = "申请ID")
    @QueryParam(value = "apply_id")
    private String applyId;

    @ApiParam(
            value = "状态, 0: 未代偿, 1: 代偿中, 8: 代偿失败, 18: 已撤销, 19: 代偿失败",
            allowableValues = "0,1,8,18,19")
    @QueryParam(value = "compensatory_details_status")
    private Byte compensatoryDetailsStatus;

    @ApiParam(value = "借款时间（开始）")
//    @QueryParam(value = "loan_time_from")
    @QueryParam(value = "startLoanTime")
    private Date startLoanTime;//借款时间

    @ApiParam(value = "借款时间（结束）")
//    @QueryParam(value = "loan_time_to")
    @QueryParam(value = "endLoanTime")
    private Date endLoanTime;//

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Byte getCompensatoryDetailsStatus() {
        return compensatoryDetailsStatus;
    }

    public void setCompensatoryDetailsStatus(Byte compensatoryDetailsStatus) {
        this.compensatoryDetailsStatus = compensatoryDetailsStatus;
    }

    public Date getStartLoanTime() {
        return startLoanTime;
    }

    public void setStartLoanTime(Date startLoanTime) {
        this.startLoanTime = startLoanTime;
    }

    public Date getEndLoanTime() {
        return endLoanTime;
    }

    public void setEndLoanTime(Date endLoanTime) {
        this.endLoanTime = endLoanTime;
    }
}
