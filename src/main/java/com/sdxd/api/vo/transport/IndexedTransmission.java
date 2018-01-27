package com.sdxd.api.vo.transport;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.HeaderParam;

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
 * 16/11/11     melvin                 Created
 */
public class IndexedTransmission extends Transmission {

    @ApiParam(value = "文件索引", required = true)
    @NotEmpty(message = "文件索引未填写")
    @HeaderParam(value = "transmission_file_index")
    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
