package com.sdxd.api.vo.credit;

import com.sdxd.creditline.response.CreditLineInfo;
import io.swagger.annotations.ApiModelProperty;

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
 * 16/11/21     melvin                 Created
 */
public class CreditLine extends CreditLineInfo {

    @ApiModelProperty("用户ID")
    @Override
    public Long getUserId() {
        return super.getUserId();
    }

    @ApiModelProperty("总额度")
    @Override
    public Integer getTotal() {
        BigDecimal available = new BigDecimal(super.getTotal());
        BigDecimal used = new BigDecimal(getUsed());
        BigDecimal frozen = new BigDecimal(getFrozen());
        BigDecimal total = available.add(used).add(frozen);
        return total.intValue();
    }

    @ApiModelProperty("可用额度")
    public Integer getAvailable() {
        return super.getTotal() == null ?
                null : super.getTotal() < 0 ? 0 : super.getTotal();
    }

    @ApiModelProperty("已使用额度")
    @Override
    public Integer getUsed() {
        return super.getUsed();
    }

    @ApiModelProperty("冻结额度")
    @Override
    public Integer getFrozen() {
        return super.getFrozen();
    }

    @ApiModelProperty("额度状态: NEW: 创建, INIT: 初始化, PROCCESSIONG: 决策运行结束（处理中）, REFUSE: 审批拒绝, OVERDUE: 过期, ACTIVE: 激活")
    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    @ApiModelProperty("通过时间")
    @Override
    public Date getApprovedTime() {
        return super.getApprovedTime();
    }
}
