package com.sdxd.api.vo.flow;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by matteo on 2017/7/21.
 */
public class FlowSdxdQuery extends PageParameter {

    //    渠道组的id    前端登录可以获得这个数据 group
    @ApiParam(value = "渠道组id")
    @QueryParam(value = "id")
    private String id;
    //    按时间查询的日期
    @ApiParam(value = "开始时间")
    @QueryParam(value = "start_Date")
    private Date startDate;

    @ApiParam(value = "截止时间")
    @QueryParam(value = "end_Date")
    private Date endDate;

    @ApiParam(value = "渠道结算方式id")
    @QueryParam(value = "way")
    private String way;

    @ApiParam(value = "具体渠道source")
    @QueryParam(value = "source")
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
