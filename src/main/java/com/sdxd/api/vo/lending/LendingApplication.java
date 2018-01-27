package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/30     melvin                 Created
 */
public class LendingApplication extends RestRequest {

    @ApiModelProperty(value = "放款记录ID", required = true)
    @NotNull(message = "放款记录ID未填写")
    private List<String> lendingIds;

    @ApiModelProperty(value = "资方ID", required = true)
    @NotEmpty(message = "资方ID未填写")
    private String capitalProviderId;

    public List<String> getLendingIds() {
        return lendingIds;
    }

    public void setLendingIds(List<String> lendingIds) {
        this.lendingIds = lendingIds;
    }

    public String getCapitalProviderId() {
        return capitalProviderId;
    }

    public void setCapitalProviderId(String capitalProviderId) {
        this.capitalProviderId = capitalProviderId;
    }
}
