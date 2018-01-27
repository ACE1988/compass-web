package com.sdxd.api.vo.credit;

import com.sdxd.api.vo.SignInSummary;

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
 * 16/11/28     melvin                 Created
 */
public class InternetTrace {

    private SignInSummary signInSummary;
    private String email;

    public InternetTrace(SignInSummary signInSummary, String email) {
        this.signInSummary = signInSummary;
        this.email = email;
    }

    public SignInSummary getSignInSummary() {
        return signInSummary;
    }

    public String getEMail() {
        return email;
    }
}
