package com.sdxd.api.vo.certification;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

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
public class RequiredCertification {

    @ApiModelProperty("实名认证状态")
    private boolean realName;
    @ApiModelProperty("身份信息认证状态")
    private boolean identity;
    @ApiModelProperty("人脸识别认证状态")
    private boolean face;
    @ApiModelProperty("居住信息认证状态")
    private boolean residence;
    @ApiModelProperty("工作信息认证状态")
    private boolean job;
    @ApiModelProperty("联系人信息认证状态")
    private boolean contact;
    @ApiModelProperty("芝麻认证状态")
    private boolean zhiMa;
    @ApiModelProperty("是否绑卡")
    private boolean bindCard;

    @ApiModelProperty("手机认证是否通过")
    private boolean operators;

    @ApiModelProperty("芝麻分")
    private String zhiMaScore;

    @ApiModelProperty("百荣分")
    private String baiRongScore;

    RequiredCertification() {}

    public RequiredCertification(
            boolean realName, boolean identity, boolean face, boolean residence,
            boolean job, boolean contact, boolean zhiMa, boolean bindCard) {
        this.realName = realName;
        this.identity = identity;
        this.face = face;
        this.residence = residence;
        this.job = job;
        this.contact = contact;
        this.zhiMa = zhiMa;
        this.bindCard = bindCard;
    }

    public void merge(RequiredCertification certification) {
        this.realName = certification.realName;
        this.identity = certification.identity;
        this.residence = certification.residence;
        this.job = certification.job;
        this.contact = certification.contact;
        this.face = certification.face;
        this.bindCard = certification.bindCard;
        this.zhiMa = certification.zhiMa;
        if (StringUtils.isBlank(this.zhiMaScore)) {
            this.zhiMaScore = certification.zhiMaScore;
        }
    }

    public boolean isRealName() {
        return realName;
    }

    public boolean isIdentity() {
        return identity;
    }

    public boolean isFace() {
        return face;
    }

    public boolean isResidence() {
        return residence;
    }

    public boolean isJob() {
        return job;
    }

    public boolean isContact() {
        return contact;
    }

    public boolean isZhiMa() {
        return zhiMa;
    }

    public boolean isBindCard() {
        return bindCard;
    }

    public boolean isOperators() {
        return operators;
    }

    public void setOperators(boolean operators) {
        this.operators = operators;
    }

    public String getZhiMaScore() {
        return zhiMaScore;
    }

    public void setZhiMaScore(String zhiMaScore) {
        this.zhiMaScore = zhiMaScore;
    }

    public String getBaiRongScore() {
        return baiRongScore;
    }

    public void setBaiRongScore(String baiRongScore) {
        this.baiRongScore = baiRongScore;
    }
}
