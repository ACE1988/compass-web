package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.admin
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/9    wenzhou.xu              Created
 */
public class QueryUserList extends RestRequest {
    @ApiParam(value = "批次号")
    @QueryParam(value = "batch_id")
    @NotEmpty(message = "批次号不能为空")
    private String batchId;

    @ApiParam(value = "页编号")
    @QueryParam(value = "page_no")
    private Integer currentPage;

    @ApiParam(value = "每页条数")
    @QueryParam(value = "page_size")
    private Integer pageSize;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
