package com.sdxd.api.vo.credit;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
public class CreditLineIncrement {

    @NotNull(message = "用户ID未填写")
    private Long userId;

    @NotNull(message = "额度提升量未填写")
    @Digits(integer = 4, fraction = 0, message = "额度提升量格式错误, 长度为4, 不能有小数")
    @Max(value = 2200, message = "额度最大值为2200")
    @Min(value = 1, message = "额度最小值为1")
    private Integer increment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}
