package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certificate
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/9     melvin                 Created
 */
public class QueryAdminList extends RestRequest {

    @ApiParam(value = "角色")
    @QueryParam(value = "role")
    private String role;

    @ApiParam(value = "账号")
    @QueryParam(value = "account")
    @Pattern(regexp = "[a-z|A-Z|\\d]+", message = "account 只能由a-z, A-Z或 数字组成")
    private String account;

    @ApiParam(value = "审批状态：" +
            "<ul><li>0-待审批</li> <li>1-通过</li> <li>2-拒绝</li></ul>",
            allowableValues = "0,1,2")
    @QueryParam(value = "status")
    private Integer status;

    @ApiParam(value = "姓名")
    @QueryParam(value = "name")
    private String name;

    @ApiParam(value = "手机号")
    @QueryParam(value = "phone")
    private String phone;

    @ApiParam(value = "页编号")
    @QueryParam(value = "page_no")
    private Integer currentPage;

    @ApiParam(value = "每页条数")
    @QueryParam(value = "page_size")
    private Integer pageSize;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentPage() {
        return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null ? 15 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    
}
