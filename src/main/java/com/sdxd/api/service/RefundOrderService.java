package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.sdxd.api.vo.lending.*;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.RepaymentDocumentDubboService;
import com.sdxd.repayment.dubbo.RepaymentPlanDubboService;
import com.sdxd.repayment.dubbo.RepaymentRefundmentDubboService;
import com.sdxd.repayment.dubbo.request.*;
import com.sdxd.repayment.dubbo.response.DocumentSummaryResponse;
import com.sdxd.repayment.dubbo.response.RefundmentInfo;
import com.sdxd.repayment.dubbo.response.RepaymentPlanInfo;
import com.sdxd.repayment.dubbo.response.RepaymentPlanSummary;
import com.sdxd.user.api.UserCardService;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.BindCardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/5/9
 * 盛大小贷
 */
@Service
public class RefundOrderService {

    private Logger LOGGER = LoggerFactory.getLogger(RefundOrderService.class);

    @Reference(version = "1.0.0")
    private RepaymentRefundmentDubboService repaymentRefundmentDubboService;

    @Reference(version = "1.0.0")
    private RepaymentPlanDubboService repaymentPlanDubboService ;

    @Reference(version = "1.0.0")
    private RepaymentDocumentDubboService repaymentDocumentDubboService ;

    @Reference(version = "1.0.0")
    private UserCardService cardService;

    //查询还款单
    public PaginationSupport<RefundmentInfo> refundOrder(RefundOrderQuery query) throws ProcessBizException {
        RefundmentQueryRequest request = new RefundmentQueryRequest() ;
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserName(query.getUserName());//添加客户姓名
        request.setPhone(query.getPhone());
        request.setApplyId(query.getApplyId());
        request.setDocumentId(query.getDocumentId());
        request.setStatus(query.getStatus());
        request.setSubmitStartTime(query.getStartApplyTime());
        request.setSubmitEndTime(query.getEndApplyTime());
        request.setApproveStartTime(query.getStartApproveTime());
        request.setApproveEndTime(query.getEndApproveTime());
        request.setRefundStartTime(query.getStartRefundTime());
        request.setRefundEndTime(query.getEndRefundTime());
        request.setCreateStartTime(query.getCreateStartTime());
        request.setCreateEndTime(query.getCreateEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        LOGGER.info("【退款单查询】 ============================= 》参数,{}", JSONObject.toJSON(request));
        DubboResponse<PaginationSupport<RefundmentInfo>>  re = repaymentRefundmentDubboService.query(request);
        return  data(re);
    }

    public Integer refundOrderCount(RefundOrderQuery query) throws ProcessBizException{
        RefundmentQueryRequest request = new RefundmentQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserName(query.getUserName());//添加客户姓名
        request.setPhone(query.getPhone());
        request.setApplyId(query.getApplyId());
        request.setDocumentId(query.getDocumentId());
        request.setStatus(query.getStatus());
        request.setSubmitStartTime(query.getStartApplyTime());
        request.setSubmitEndTime(query.getEndApplyTime());
        request.setApproveStartTime(query.getStartApproveTime());
        request.setApproveEndTime(query.getEndApproveTime());
        request.setRefundStartTime(query.getStartRefundTime());
        request.setRefundEndTime(query.getEndRefundTime());
        request.setCreateStartTime(query.getCreateStartTime());
        request.setCreateEndTime(query.getCreateEndTime());

        DubboResponse<Integer> response = repaymentRefundmentDubboService.count(request);
        return data(response);
    }

    public DubboResponse<Boolean> refundApprove(ApproveQuery query){
        RefundmentApproveRequest request = new RefundmentApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setAmount(query.getAmount());
        request.setChannelCode(query.getChannelCode());
        request.setBankCode(query.getBankCode());
        request.setCardNo(query.getCardNo());
        request.setBankName(query.getBankName());
        request.setComment(query.getComment());
        request.setApproveUserName(query.getApproveUserName());
        LOGGER.info("【审核退款单】 ============================= 》参数,{}", JSONObject.toJSON(request));
        DubboResponse<Boolean> response =  repaymentRefundmentDubboService.approve(request);
        return response;
    }

    public DubboResponse<Boolean> refundReject(RejectQuery query){
        RefundmentRejectRequest request = new RefundmentRejectRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setComment(query.getComment());
        request.setApproveUserName(query.getApproveUserName());
        LOGGER.info("【驳回退款单】 ============================= 》参数,{}", JSONObject.toJSON(request));
        DubboResponse<Boolean> response = repaymentRefundmentDubboService.reject(request);
        return response ;
    }

    /**
     * 查询银行卡
     * @param query
     * @return
     */
    public DubboResponse<List<BindCardInfo>> getBankCards(BankCardQuery query) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(query.getUserId());
        try {
            DubboResponse<List<BindCardInfo>> response = cardService.queryBindCards(request);
            return response;
        } catch (Exception e) {
            LOGGER.error("调用Dubbo接口出错, userId={}", query.getUserId(), e);
        }
        return null;
    }

    /**
     * 按照还款计划ID查询还款计划
     */
    public DubboResponse<RepaymentPlanInfo> getById(RepaymentPlanQuery query ){
        RepaymentPlanRequest request = new RepaymentPlanRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        DubboResponse<RepaymentPlanInfo> response = repaymentPlanDubboService.getById(request);
        return response;
    }

    /**
     * 获取还款计划的总结信息
     * @param query
     * @return
     */
    public  DubboResponse<RepaymentPlanSummary> getPlanSummary(RepaymentPlanQuery query){
        RepaymentPlanRequest request  = new RepaymentPlanRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        DubboResponse<RepaymentPlanSummary> response = repaymentPlanDubboService.getPlanSummary(request);
        return response ;
    }

    public DubboResponse<DocumentSummaryResponse> getDocumentSummary(DocumentQuery query){
        DocumentRequest request = new DocumentRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPlanId(query.getPlanId());
        request.setApplyId(query.getApplyId());
        request.setUserId(query.getUserId());
        DubboResponse<DocumentSummaryResponse> response = repaymentDocumentDubboService.getDocumentSummary(request);
        return response ;
    }
}
