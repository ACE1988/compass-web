package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiModelProperty;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class PayOfflineClaimQuery extends RestRequest {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id未填写")
    private List<String> ids;
    @NotNull
    private Integer status;

    private String applyId;

    public Integer getStatus() {
        return status;
    }

    public List<String> getIds() {
        return ids;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
