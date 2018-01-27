package com.sdxd.api.vo.loan;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
public class MortgageHouseLoanApplication {

    @ApiModelProperty(value = "姓名", required = true)
    @NotEmpty(message="姓名不能为空")
    private String name;//姓名

    @ApiModelProperty(value = "身份证号码", required = true)
    @NotEmpty(message="身份证号码不能为空")
    private String idNo;//身份证号码

    @ApiModelProperty(value = "家庭住址", required = true)
    @NotEmpty(message="家庭住址不能为空")
    private String homeAddress;//家庭住址

    @ApiModelProperty(value = "工作单位", required = true)
    @NotEmpty(message="工作单位不能为空")
    private String companyName;//工作单位

    @ApiModelProperty(value = "单位地址", required = true)
    @NotEmpty(message="单位地址不能为空")
    private String companyAddress;//单位地址

    @ApiModelProperty(value = "抵押房屋地址", required = true)
    @NotEmpty(message="抵押房屋地址不能为空")
    private String mortgageHouseAddress;//抵押房屋地址

    @ApiModelProperty(value = "联系电话", required = true)
    @NotEmpty(message="联系电话不能为空")
    private String phone;//联系电话

    @ApiModelProperty(value = "申请金额", required = true)
    @NotNull(message="申请金额不能为空")
    private BigDecimal applyAmount;//申请金额

    @ApiModelProperty(value = "用途", required = true)
    @NotEmpty(message="用途不能为空")
    private String purpose;//用途

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getMortgageHouseAddress() {
        return mortgageHouseAddress;
    }

    public void setMortgageHouseAddress(String mortgageHouseAddress) {
        this.mortgageHouseAddress = mortgageHouseAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
