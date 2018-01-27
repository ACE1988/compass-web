package com.sdxd.api.vo.support;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.ws.rs.FormParam;

import io.swagger.annotations.ApiParam;

/**
 * Created by yaojun on 2017/3/20.
 */
public class JobAdd {
  private @ApiParam(value = "任务名", required = true) @FormParam("jobName") String jobName;
  private @ApiParam(value = "任务组名", required = true) @FormParam("groupName") String groupName;
  private @ApiParam(value = "任务描述", required = true) @FormParam("description")String description;
  private @ApiParam(value = "任务执行表达式", required = true) @FormParam("expression")String expression;
//  private @ApiParam(value = "任务开始时间", required = true) @FormParam("startTime")Date startTime;
  private @ApiParam(value = "调用接口名称", required = true) @FormParam("interfaceName")String interfaceName;
  private @ApiParam(value = "方法名称", required = true) @FormParam("methodName")String methodName;
  private @ApiParam(value = "调用版本", required = true) @FormParam("version")String version;
  private @ApiParam(value = "参数类型", required = false) @FormParam("dubboParamClass")String dubboParamClass;
//  private @ApiParam(value = "返回类型", required = false) @FormParam("dubboRespClass")String dubboRespClass;
//  private @ApiParam(value = "结束时间", required = false) @FormParam("endTime") Date endTime;
  private @ApiParam(value = "参数", required = false) @FormParam( "param")String param;

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getExpression() {
    return expression;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDubboParamClass() {
    return dubboParamClass;
  }

  public void setDubboParamClass(String dubboParamClass) {
    this.dubboParamClass = dubboParamClass;
  }

}
