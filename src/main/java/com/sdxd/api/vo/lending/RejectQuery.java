package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/10
 * 盛大小贷
 */
public class RejectQuery extends RestRequest {

    @ApiParam(value = "退款单id")
    @QueryParam(value = "id")
    private String id ;

    @ApiParam(value = "驳回原由")
    @QueryParam(value = "comment")
    private String comment;

    @ApiParam(value = "审核人")
    @QueryParam(value = "approve_user_name")
    private String approveUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }
}
