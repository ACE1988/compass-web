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
public class DecisionRule {

    @ApiParam(value = "Name", required = true)
    @FormParam(value = "name")
    private String name;

    @ApiParam(value = "Code", required = true)
    @FormParam(value = "code")
    private String code;

    @ApiParam(value = "Remark", required = true)
    @FormParam(value = "remark")
    private String remark;

    @ApiParam(value = "Input Params", required = true)
    @FormParam(value = "input_params")
    private String inputParams;

    @ApiParam(value = "Script Context", required = true)
    @FormParam(value = "script_context")
    private String scriptContext;

    @ApiParam(value = "Version", required = true)
    @FormParam(value = "version")
    private String version;

    @ApiParam(value = "Policy Code", required = true)
    @FormParam(value = "policy_code")
    private String policyCode;

    @ApiParam(value = "Policy Version", required = true)
    @FormParam(value = "policy_version")
    private String policyVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }
}
