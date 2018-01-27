package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/17      matteo                 Created
 */
public class AbatementBillQuery extends PageParameter {

    @ApiParam(value = "申请人")
    @QueryParam(value = "userName")
    private String userName;

    @ApiParam(value = "减免状态")
    @QueryParam(value = "statuses")
    private List<Byte> statuses;

    @ApiParam(value = "借款订单号")
    @QueryParam(value = "applyId")
    private String applyId;

    @ApiParam(value = "减免单创建日期")
    @QueryParam(value = "startCreateTime")
    private Date startCreateTime;


    @ApiParam(value = "减免结束日期")
    @QueryParam(value = "endCreateTime")
    private Date endCreateTime;

    @ApiParam(value = "减免单申请人")
    @QueryParam(value = "reporterName")
    private String reporterName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Byte> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Byte> statuses) {
        this.statuses = statuses;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
