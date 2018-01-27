package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/9/15     melvin                 Created
 */
public class CompensatoryBatchQuery extends RestRequest {

    @ApiParam(value = "批次号")
    @QueryParam(value = "batch_id")
    private String batchId;

    @ApiParam(value = "放款资金提供方ID")
    @QueryParam(value = "capital_provider_id")
    private String capitalProviderId;

    @ApiParam(value = "代偿指令状态, 0-未代偿  1-代偿中  2-代偿拒绝 8-全部失败  18-代偿部分成功  19-代偿全部成功", allowableValues = "0,1,2,8,18,19")
    @QueryParam(value = "compensatory_status")
    private Byte compensatoryStatus;

    @ApiParam(value = "代偿批次生成日期开始")
    @QueryParam(value = "generate_from")
    private Date generateFrom;

    @ApiParam(value = "代偿批次生成日期结束")
    @QueryParam(value = "generate_to")
    private Date generateTo;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCapitalProviderId() {
        return capitalProviderId;
    }

    public void setCapitalProviderId(String capitalProviderId) {
        this.capitalProviderId = capitalProviderId;
    }

    public Byte getCompensatoryStatus() {
        return compensatoryStatus;
    }

    public void setCompensatoryStatus(Byte compensatoryStatus) {
        this.compensatoryStatus = compensatoryStatus;
    }

    public Date getGenerateFrom() {
        return generateFrom;
    }

    public void setGenerateFrom(Date generateFrom) {
        this.generateFrom = generateFrom;
    }

    public Date getGenerateTo() {
        return generateTo;
    }

    public void setGenerateTo(Date generateTo) {
        this.generateTo = generateTo;
    }
}
