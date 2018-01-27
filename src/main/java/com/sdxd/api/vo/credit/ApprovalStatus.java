package com.sdxd.api.vo.credit;

import com.sdxd.approve.dubbo.api.enums.CreditStatus;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/24     melvin                 Created
 */
public enum ApprovalStatus {

    PASS(CreditStatus.TRIAL_PASS.getValue(), CreditStatus.CLOSED_PASS.getValue()),
    REJECT(CreditStatus.TRIAL_REFUSE.getValue(), CreditStatus.CLOSED_REFUSE.getValue());

    private int firstApprovalCode;
    private int finalApprovalCode;

    ApprovalStatus(int firstApprovalCode, int finalApprovalCode) {
        this.firstApprovalCode = firstApprovalCode;
        this.finalApprovalCode = finalApprovalCode;
    }

    public int getFirstApprovalCode() {
        return firstApprovalCode;
    }

    public int getFinalApprovalCode() {
        return finalApprovalCode;
    }
}
