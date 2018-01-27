package com.sdxd.api.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

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
 * 17/1/4     melvin                 Created
 */
public class CaptchaAnswer {

    @ApiModelProperty(value = "ID", required = true)
    @NotEmpty(message = "ID未填写")
    private String id;

    @ApiModelProperty(value = "答案", required = true)
    @NotEmpty(message = "答案未填写")
    private String answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
