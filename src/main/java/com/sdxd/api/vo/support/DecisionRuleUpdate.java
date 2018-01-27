package com.sdxd.api.vo.support;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.FormParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.support
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/17     melvin                 Created
 */
public class DecisionRuleUpdate {

    @ApiParam(value = "Code", required = true)
    @FormParam(value = "code")
    private String code;

    @ApiParam(value = "Input Params", required = true)
    @FormParam(value = "input_params")
    private String inputParams;

    @ApiParam(value = "Script Context", required = true)
    @FormParam(value = "script_context")
    private String scriptContext;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(String scriptContext) {
        this.scriptContext = scriptContext;
    }

}
