package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.cms.AgreementTemplate;
import com.sdxd.cms.dubbo.api.AgreementTemplateDubboService;
import com.sdxd.cms.dubbo.api.pojo.AgreementTemplateVo;
import com.sdxd.cms.dubbo.api.request.AgreementTemplateRequest;
import com.sdxd.cms.dubbo.api.request.DeleteAgreementTemplateRequest;
import com.sdxd.cms.dubbo.api.request.QueryAgreementTemplateRequest;
import com.sdxd.cms.dubbo.api.response.AgreementTemplateResponse;
import com.sdxd.cms.dubbo.api.response.DeleteAgreementTemplateResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.service
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/5     melvin                 Created
 */
@Service
public class AgreementTemplateService {

    @Reference(version = "1.0.0")
    private AgreementTemplateDubboService agreementTemplateDubboService;

    public PaginationSupport<AgreementTemplateVo> find(Integer pageNo, Integer pageSize) throws ProcessBizException {
        QueryAgreementTemplateRequest request = new QueryAgreementTemplateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(pageNo);
        request.setPageSize(pageSize);
        DubboResponse<PaginationSupport<AgreementTemplateVo>> response = agreementTemplateDubboService.queryAgreementTemplate(request);
        return data(response);
    }

    public Integer count() throws ProcessBizException {
        QueryAgreementTemplateRequest request = new QueryAgreementTemplateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<Integer> response = agreementTemplateDubboService.queryAgreementTemplateCount(request);
        return data(response);
    }

    public Boolean create(AgreementTemplate template) throws ProcessBizException {
        AgreementTemplateRequest request = new AgreementTemplateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPageId(template.getPageId());
        request.setUseType(template.getUseType());
        request.setAgreementTitle(template.getAgreementTitle());
        request.setAgreementUrl(template.getAgreementUrl());
        DubboResponse<AgreementTemplateResponse> response = agreementTemplateDubboService.createAgreementTemplate(request);
        return data(response, value -> value != null && value.getSuccess());
    }

    public Boolean delete(Integer id) throws ProcessBizException {
        if (id == null) {
            return false;
        }
        DeleteAgreementTemplateRequest request = new DeleteAgreementTemplateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(id);
        DubboResponse<DeleteAgreementTemplateResponse> response = agreementTemplateDubboService.deleteAgreementTemplate(request);
        return data(response, value -> value != null && value.isSuccess());
    }
}
