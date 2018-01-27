package com.sdxd.api.vo.loan;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.loan
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/4     melvin                 Created
 */
public class MortgageYiLoanApplication {

    @ApiModelProperty(value = "姓名", required = true)
    @NotEmpty(message="姓名不能为空")
    private String name;//姓名

    @ApiModelProperty(value = "手机号码", required = true)
    @NotEmpty(message="手机号码不能为空")
    private String cellphone;//手机号码

    @ApiModelProperty(value = "文化程度: 硕士及以上, 本科, 大专, 中专/高中, 初中及以下", required = true)
    @NotEmpty(message="文化程度不能为空")
    @Size(max = 5, message = "文化程度最大长度为5位")
    private String education;//文化程度

    @ApiModelProperty(value = "月收入（元）", required = true)
    @NotNull(message="月收入不能为空")
    private BigDecimal monthlySalary;//月收入（元）

    @ApiModelProperty(value = "工作单位", required = true)
    @NotEmpty(message="工作单位不能为空")
    private String companyName;//工作单位

    @ApiModelProperty(value = "单位性质: 机关及事业单位, 国营单位, 民营单位, 三资企业, 其他", required = true)
    @NotEmpty(message="单位性质不能为空")
    @Size(max = 7, message = "单位性质最大长度为7位")
    private String companyType;//单位性质

    @ApiModelProperty(value = "职务: 法人或股东, 高级管理, 中级管理, 初级管理, 基层员工", required = true)
    @NotEmpty(message="职务不能为空")
    @Size(max = 5, message = "职务最大长度为5位")
    private String companyPosition;//职务

    @ApiModelProperty(value = "参加工作时间", required = true)
    @NotNull(message="参加工作时间不能为空")
    private Date takeWorkTime;//参加工作时间

    @ApiModelProperty(value = "现单位入职时间", required = true)
    @NotNull(message="现单位入职时间不能为空")
    private Date entryTime;//现单位入职时间

    @ApiModelProperty(value = "现居住地址", required = true)
    @NotEmpty(message="现居住地址不能为空")
    private String residenceAddress;//现居住地址

    @ApiModelProperty(value = "个人房产: 自有房产无按揭, 自有房产有按揭, 无房产", required = true)
    @NotEmpty(message="个人房产不能为空")
    @Size(max = 7, message = "个人房产最大长度为7位")
    private String personalProperty;//个人房产

    @ApiModelProperty(value = "自有房屋地址", required = true)
    @NotEmpty(message="自有房屋地址不能为空")
    private String personalPropertyAddress;//自有房屋地址

    @ApiModelProperty(value = "婚姻状况: 已婚, 未婚, 离异", required = true)
    @NotEmpty(message="婚姻状况不能为空")
    @Size(max = 2, message = "婚姻状况最大长度为2位")
    private String maritalStatus;//婚姻状况

    @ApiModelProperty(value = "推荐人姓名", required = true)
    private String refereeName;//推荐人姓名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

    public Date getTakeWorkTime() {
        return takeWorkTime;
    }

    public void setTakeWorkTime(Date takeWorkTime) {
        this.takeWorkTime = takeWorkTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getPersonalProperty() {
        return personalProperty;
    }

    public void setPersonalProperty(String personalProperty) {
        this.personalProperty = personalProperty;
    }

    public String getPersonalPropertyAddress() {
        return personalPropertyAddress;
    }

    public void setPersonalPropertyAddress(String personalPropertyAddress) {
        this.personalPropertyAddress = personalPropertyAddress;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }
}
