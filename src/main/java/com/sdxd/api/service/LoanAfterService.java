package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.LoanAfterTemplate;
import com.sdxd.api.vo.admin.CreditCheck;
import com.sdxd.api.vo.admin.CreditCheckChannel;
import com.sdxd.api.vo.admin.QueryUserList;
import com.sdxd.approve.dubbo.api.CreditDubboService;
import com.sdxd.approve.dubbo.api.enums.CreditType;
import com.sdxd.approve.dubbo.api.pojo.CreditDetailVo;
import com.sdxd.approve.dubbo.api.request.*;
import com.sdxd.approve.dubbo.api.response.CreditBatchDetailResponse;
import com.sdxd.approve.dubbo.api.response.CreditBatchResponse;
import com.sdxd.approve.dubbo.api.response.CreditDetailResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * 2017/8/8    wenzhou.xu              Created
 */
@Service
public class LoanAfterService {

    @Reference(version = "1.0.0")
    private CreditDubboService creditDubboService;

    /**
     * 向approve组件推送本批次查询任务
     *
     * @param phoneList
     * @return
     */
    public String queryPhoneList(List<LoanAfterTemplate> phoneList) throws ProcessBizException {
        CreditDetailRequest request = new CreditDetailRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(request.getRequestId());
        request.setVos(phoneList.stream().map(template -> {
            CreditDetailVo vo = new CreditDetailVo();
            vo.setUserId(Long.valueOf(template.getUserId()));
            vo.setPhone(template.getPhone());
            vo.setUserName(template.getUserName());
            return vo;
        }).collect(Collectors.toList()));
        DubboResponse<String> response = creditDubboService.uploadCreditDatail(request);
        return data(response);
    }

    public String queryPhone(String phone) throws ProcessBizException {
        CreditPhoneNumRequest request = new CreditPhoneNumRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(request.getRequestId());
        request.setPhone(phone);

        DubboResponse<String> response = creditDubboService.queryOneCreditDetail(request);
        return data(response);
    }

    /**
     * 通过批次号分页查询本批次用户的信息
     *
     * @param query
     * @return
     */
    public PaginationSupport<CreditDetailResponse> queryUserList(QueryUserList query) throws ProcessBizException {
        CreditQueryRequest request = new CreditQueryRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(query.getBatchId());
        request.setCurrentPage(query.getCurrentPage());
        request.setPageSize(query.getPageSize());

        DubboResponse<PaginationSupport<CreditDetailResponse>> response = creditDubboService.queryCreditDetail(request);
        return data(response);
    }

    public Boolean saveCreditBatch(CreditCheck query) throws ProcessBizException {
        CreditBatchRequest request = new CreditBatchRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(query.getBatchId());
        List<CreditCheckChannel> list = query.getChannelList();
        Map<CreditType, String> map = new HashMap<>();
        map = list.stream().reduce(map, (all, value) -> {
            all.put(CreditType.getCreditType(value.getResource()), value.getReason());
            return all;
        }, (m1, m2) -> m2);
        request.setMap(map);

        DubboResponse<Boolean> response = creditDubboService.saveCreditBatch(request);
        return data(response);
    }

    /**
     * 查询批次列表
     *
     * @return
     * @throws ProcessBizException
     */
    public PaginationSupport<CreditBatchResponse> queryBatchList(PageParameter page) throws ProcessBizException {
        CreditQueryRequest request = new CreditQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(page.getPageNo());
        request.setPageSize(page.getPageSize());

        DubboResponse<PaginationSupport<CreditBatchResponse>> response = creditDubboService.queryCreditBatch(request);
        return data(response);
    }

    public CreditBatchDetailResponse queryCreditBatchDetail(String batchId) throws ProcessBizException {
        CreditBatchDetailRequest request = new CreditBatchDetailRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(batchId);

        DubboResponse<CreditBatchDetailResponse> response = creditDubboService.queryCreditBatchDetail(request);
        return data(response);
    }

    public List<CreditDetailResponse> queryCreditDetail(String batchId) throws ProcessBizException {
        CreditDetailQueryRequest request = new CreditDetailQueryRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchId(batchId);

        DubboResponse<List<CreditDetailResponse>> response = creditDubboService.queryCreditDetail(request);
        return data(response);
    }
}
