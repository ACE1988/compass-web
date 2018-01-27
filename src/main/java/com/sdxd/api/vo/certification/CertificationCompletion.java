package com.sdxd.api.vo.certification;

import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certificate
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/8     melvin                 Created
 */
public class CertificationCompletion {

    @ApiModelProperty("必要认证信息完整度")
    private RequiredCertification required;
    private int total;
    private int passed;

    public CertificationCompletion() {}

    public CertificationCompletion(RequiredCertification required) {
        this.required = required;
    }

    public void setRequired(RequiredCertification required) {
        getRequired().merge(required);
    }

    public RequiredCertification getRequired() {
        if (required == null) {
            required = new RequiredCertification();
        }
        return required;
    }

    public boolean isComplete() {
        if (required == null) {
            return false;
        }
        return (required.isRealName() &&
                required.isIdentity() &&
                required.isContact() &&
                required.isJob() &&
                required.isResidence() &&
                required.isZhiMa());
    }

    public double getCompletion() {
        if (required != null) {
            count(required.isIdentity());
            count(required.isContact());
            count(required.isJob());
//            count(required.isRealName());
            count(required.isResidence());
            count(required.isZhiMa());
        }
        double value = (double) passed / (double) total;
        return value;
    }

    private void count(boolean pass) {
        total ++;
        if (pass) {
            passed ++;
        }
    }
}
