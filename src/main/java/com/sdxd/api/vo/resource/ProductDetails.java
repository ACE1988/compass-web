package com.sdxd.api.vo.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdxd.product.response.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.product
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/21     melvin                 Created
 */
@ApiModel("产品详情")
public class ProductDetails extends ProductInfo {

    @JsonIgnore
    private boolean containsServiceCharge;

    public ProductDetails(boolean containsServiceCharge) {
        this.containsServiceCharge = containsServiceCharge;
    }

    @ApiModelProperty("产品ID")
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getCode() {
        return super.getCode();
    }

    @Override
    public String getCategory() {
        return super.getCategory();
    }

    @ApiModelProperty("产品名称")
    @Override
    public String getName() {
        return super.getName();
    }

    @ApiModelProperty("金额")
    @Override
    public Integer getAmount() {
        return super.getAmount();
    }

    @ApiModelProperty("周期")
    @Override
    public Integer getPeriod() {
        return super.getPeriod();
    }

    @ApiModelProperty("是否砍头息：0-否 1-是")
    @Override
    public Boolean getCutInterest() {
        return super.getCutInterest();
    }

    @Override
    public Integer getRollOver() {
        return super.getRollOver();
    }

    @Override
    public String getRepaymentType() {
        return super.getRepaymentType();
    }

    @ApiModelProperty("计息方式：daily（按日计息）")
    @Override
    public String getInterestCalculationType() {
        return super.getInterestCalculationType();
    }

    @ApiModelProperty("版本号，每次修改后加1")
    @Override
    public Integer getVersion() {
        return super.getVersion();
    }

    @Override
    public Boolean getStatus() {
        return super.getStatus();
    }

    @ApiModelProperty("总的息费")
    @Override
    public BigDecimal getTotalCharge() {
        return super.getTotalCharge();
    }

    @ApiModelProperty("到期应还")
    public BigDecimal getTotalRepayable() {
        BigDecimal capital = super.getCapital() == null ? ZERO : new BigDecimal(super.getCapital());
        BigDecimal interest = super.getInterest() == null ? ZERO : super.getInterest();
        BigDecimal totalCharge = getTotalCharge() == null ? ZERO : getTotalCharge();
        return containsServiceCharge ? capital.add(interest) : capital.add(totalCharge);
    }
}
