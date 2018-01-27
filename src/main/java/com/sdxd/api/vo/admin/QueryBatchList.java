package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

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
public class QueryBatchList {
    @ApiParam(value = "页编号")
    @QueryParam(value = "page_no")
    private Integer currentPage;

    @ApiParam(value = "每页条数")
    @QueryParam(value = "page_size")
    private Integer pageSize;

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
