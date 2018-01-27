package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.crm.ContractDetailQuery;
import com.sdxd.api.vo.crm.ContractQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.ContractDubboService;
import com.sdxd.data.dubbo.api.request.ContractDetailRequest;
import com.sdxd.data.dubbo.api.request.ContractRequest;
import com.sdxd.data.dubbo.api.response.pojo.ContractBo;
import com.sdxd.data.dubbo.api.response.pojo.ContractDetailBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.signature.api.SignaturePdfDownloadDubboService;
import com.sdxd.signature.api.request.SignaturePdfDownloadRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/6/15
 * 盛大小贷 合同协议管理
 */
@Service
public class ContractService {

    @Reference(version = "1.0.0")
    private ContractDubboService contractDubboService ;


    @Reference(version = "1.0.0")
    SignaturePdfDownloadDubboService signaturePdfDownloadDubboService;

    public PaginationSupport<ContractBo> select (ContractQuery query) throws ProcessBizException {
        ContractRequest request = new ContractRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        request.setApplyId(query.getApplyId());
        request.setPhone(query.getPhone());
        request.setUserName(query.getUserName());
        request.setStatus(query.getStatus() == null ?  0 :query.getStatus());
        DubboResponse<PaginationSupport<ContractBo>> response = contractDubboService.selectAgreement(request);
        return data(response);
    }

    public Integer count(ContractQuery query) throws ProcessBizException {
        ContractRequest request = new ContractRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setApplyId(query.getApplyId());
        request.setPhone(query.getPhone());
        request.setUserName(query.getUserName());
        request.setStatus(query.getStatus());
        DubboResponse<Integer> response = contractDubboService.count(request);
        return data(response);
    }

    public DubboResponse<List<ContractDetailBo>> agreementDetails (ContractDetailQuery query){
        ContractDetailRequest request = new ContractDetailRequest();
        request.setApplyId(query.getApplyId());
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<List<ContractDetailBo>> response = contractDubboService.agreementList(request);
        return response;
    }

    public DubboResponse <String> signaturePdfUrl(String applyId,Integer contractType) {
        SignaturePdfDownloadRequest request = new SignaturePdfDownloadRequest();
        request.setApplyId(applyId);
        request.setContractType(contractType);
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<String> response = signaturePdfDownloadDubboService.getSignaturePdfUrl(request);
        return response ;
    }
}
