package com.sdxd.api.vo.cms;

import com.sdxd.cms.dubbo.api.pojo.AgreementTemplateVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.cms
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/8     melvin                 Created
 */
public class IndexedAgreementTemplate extends AgreementTemplate {

    @ApiModelProperty("ID")
    private String id ;

    public IndexedAgreementTemplate(AgreementTemplateVo vo) {
        if (vo != null) {
            this.setId(vo.getId());
            this.setPageId(vo.getPageId());
            this.setUseType(vo.getUseType());
            this.setAgreementTitle(vo.getAgreementTitle());
            this.setAgreementUrl(vo.getAgreementUrl());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
