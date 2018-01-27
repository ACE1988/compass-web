package com.sdxd.api.vo.lending;

import com.sdxd.pay.response.ShengpayMerchantBalance;
import com.sdxd.repayment.dubbo.response.StatisticsCompensatoryDetailResponse;

import java.math.BigDecimal;

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
 * 2017/9/19     melvin                 Created
 */
public class CompensatorySummary {

    private String capitalProviderId;//资方
    /**
     * 可用账户余额
     */
    private BigDecimal capitalProviderAvailableAmount;

    /**
     * 冻结账户余额
     */
    private BigDecimal capitalProviderFrozenAmount;
    /**
     * 保证金账户余额
     */
    private BigDecimal capitalProviderDepositAmount;
    private Integer count;//代偿笔数
    private BigDecimal amount;//未代偿总金额金额
    private Integer countOfSuccess;//已代偿笔数
    private BigDecimal amountOfSuccess;//已代偿总金额金额

    public CompensatorySummary(ShengpayMerchantBalance merchantBalance, StatisticsCompensatoryDetailResponse summary) {
        if (merchantBalance != null) {
            this.capitalProviderAvailableAmount = merchantBalance.getAvailableAmount();
            this.capitalProviderFrozenAmount = merchantBalance.getFrozenAmount();
            this.capitalProviderDepositAmount = merchantBalance.getDepositAmount();
        }
        if (summary != null) {
            this.capitalProviderId = summary.getCapitalProviderId();
            this.count = summary.getCount();
            this.amount = summary.getAmount();
            this.countOfSuccess = summary.getCountOfSuccess();
            this.amountOfSuccess = summary.getAmountOfSuccess();
        }
    }

    public String getCapitalProviderId() {
        return capitalProviderId;
    }

    public BigDecimal getCapitalProviderAvailableAmount() {
        return capitalProviderAvailableAmount;
    }

    public BigDecimal getCapitalProviderFrozenAmount() {
        return capitalProviderFrozenAmount;
    }

    public BigDecimal getCapitalProviderDepositAmount() {
        return capitalProviderDepositAmount;
    }

    public Integer getCount() {
        return count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getCountOfSuccess() {
        return countOfSuccess;
    }

    public BigDecimal getAmountOfSuccess() {
        return amountOfSuccess;
    }
}
