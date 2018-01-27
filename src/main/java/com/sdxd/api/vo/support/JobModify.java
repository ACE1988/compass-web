package com.sdxd.api.vo.support;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.FormParam;
import java.util.Date;

/**
 * Created by yaojun on 2017/3/20.
 */
public class JobModify {

    private @ApiParam(value = "任务名", required = true) @FormParam("jobName") String jobName;
    private @ApiParam(value = "任务组名", required = true) @FormParam("groupName") String groupName;
    private @ApiParam(value = "执行规则", required = true) @FormParam("expression") String expression;

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

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

}
