package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdxd.api.vo.lending.PayOfflineLogsQuery;
import com.sdxd.api.vo.lending.RepaymentOfflineApproveCommit;
import com.sdxd.api.vo.lending.RepaymentOfflineDetailQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.RepaymentOfflineDocumentDubboService;
import com.sdxd.repayment.dubbo.request.*;
import com.sdxd.repayment.dubbo.response.RepaymentOfflineLogResponse;
import com.sdxd.repayment.dubbo.response.RepaymentOfflineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/6/26
 * 盛大小贷
 */
@Service
public class RepaymentOfflineDetailService {

    private Logger LOGGER = LoggerFactory.getLogger(RepaymentOfflineDetailService.class);

    @Reference(version = "1.0.0")
    private RepaymentOfflineDocumentDubboService repaymentOfflineDocumentDubboService;

    //单批次数
    private static final Integer CONSTANT = 1000;

    /**
     * 查询线下对公管理列表
     *
     * @param query
     * @return
     * @throws ProcessBizException
     */
    public PaginationSupport<RepaymentOfflineResponse> queryRepaymentOfflineDetail(RepaymentOfflineDetailQuery query)
            throws ProcessBizException {
        LOGGER.info("【线下对公管理】====================》查询对公列表参数,{}" + JSONObject.toJSON(query));
        RepaymentOfflineQueryRequest request = new RepaymentOfflineQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStatus(query.getStatus());
        request.setCardName(query.getCardName());
        request.setAbstractComment(query.getAbstractDesc());
        request.setRemarks(query.getRemarks());
        request.setStartAmount(query.getAmountStart());
        request.setEndAmount(query.getAmountEnd());
        request.setStartActualRepaymentTime(query.getStartTime());
        request.setEndActualRepaymentTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo() == null ? 1 : query.getPageNo());
        request.setPageSize(query.getPageSize() == null ? 15 : query.getPageSize());
        request.setPhone(query.getPhone());
        request.setChannel(query.getChannel());

        DubboResponse<PaginationSupport<RepaymentOfflineResponse>> response = repaymentOfflineDocumentDubboService.queryPage(request);
        return data(response);
    }

    /**
     * 发送数据给DUBBO服务
     *
     * @param list
     * @return
     */
    public DubboResponse<Boolean> repaymentOfflineUploadFile(List<OfflineBankDocument> list) {
        LOGGER.info("【线下对公管理】====================》上传对公管理列表条数,{}" + list.size());
        RepaymentOfflineUploadRequest request = new RepaymentOfflineUploadRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<Boolean> response = sendFileToDubbo(list, request);
        return response;
    }

    public DubboResponse<Boolean> sendFileToDubbo
            (List<OfflineBankDocument> list, RepaymentOfflineUploadRequest request) {
        List<OfflineBankDocument> uploadList = new ArrayList<>(CONSTANT);
        int page = list.size() % CONSTANT != 0 ? (list.size() / CONSTANT + 1) : list.size() / CONSTANT;
        int size = 1;
        DubboResponse<Boolean> response = null;
        //分批上传
        for (OfflineBankDocument pojo : list) {
            uploadList.add(pojo);
            if (uploadList.size() == CONSTANT) {
                size++;
                request.setOfflineBankDocuments(uploadList);
                response = repaymentOfflineDocumentDubboService.saveOfflineDocument(request);
                uploadList.clear();
            }
        }
        if (size == page) {
            request.setOfflineBankDocuments(uploadList);
            response = repaymentOfflineDocumentDubboService.saveOfflineDocument(request);
        }
        return response;
    }

    public DubboResponse<Boolean> approveOnFinance
            (RepaymentOfflineApproveCommit query, String account, String accountName) {
        RepaymentOfflineApproveRequest request = new RepaymentOfflineApproveRequest();
        LOGGER.info("【线下对公管理】====================》 审核、删除 参数" + JSON.toJSON(query));
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setOperatorId(account);
        request.setOperatorName(accountName);
        request.setIds(query.getIds());
        request.setStatus(query.getType());
        DubboResponse<Boolean> response = repaymentOfflineDocumentDubboService.approveOnFinance(request);
        return response;
    }

    public DubboResponse<PaginationSupport<RepaymentOfflineLogResponse>> queryLogPage(PayOfflineLogsQuery query) {
        LOGGER.info("【线下对公管理】====================》 查询线下订单操作日志" + query.getId());
        RepaymentOfflineLogQueryRequest request = new RepaymentOfflineLogQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setOfflineId(query.getId());
        DubboResponse<PaginationSupport<RepaymentOfflineLogResponse>> response =
                repaymentOfflineDocumentDubboService.queryLogPage(request);
        return response;
    }

    public DubboResponse<Boolean> approveOffline( String operatorId,String applyId
    ,Integer status,List<String> ids) {
        RepaymentOfflineApproveRequest request =
                new RepaymentOfflineApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(applyId);
        request.setOperatorId(operatorId);
        request.setOperatorName(operatorId);
        request.setIds(ids);
        request.setStatus(status);
        return repaymentOfflineDocumentDubboService.approveOffline(request);
    }
}
