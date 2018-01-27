package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.lending.PaymentInstructLogQuery;
import com.sdxd.api.vo.lending.PaymentProxyQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.utils.DateUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.pay.PayDfComandDubboService;
import com.sdxd.data.dubbo.api.pay.PayDkComandDubboService;
import com.sdxd.data.dubbo.api.pay.PayInstructLogDubboService;
import com.sdxd.data.dubbo.api.request.PayDfCommandRequest;
import com.sdxd.data.dubbo.api.request.PayDkCommandRequest;
import com.sdxd.data.dubbo.api.request.PayInstructLogRequest;
import com.sdxd.data.dubbo.api.response.pojo.PayDfCommandBo;
import com.sdxd.data.dubbo.api.response.pojo.PayDkCommandBo;
import com.sdxd.data.dubbo.api.response.pojo.PayInstructLogBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
 * 17/3/17     melvin                 Created
 */
@Service
public class InstructionService {

    private Logger LOGGER = LoggerFactory.getLogger(InstructionService.class);

    @Reference(version = "1.0.0")
    private PayDfComandDubboService payDfComandDubboService;

    @Reference(version = "1.0.0")
    private PayDkComandDubboService payDkComandDubboService;

    @Reference(version = "1.0.0")
    private PayInstructLogDubboService payInstructLogDubboService;

    public PaginationSupport<PayDfCommandBo> findPaymentProxyResults(PaymentProxyQuery query) throws ProcessBizException {
        PayDfCommandRequest request = new PayDfCommandRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setPhone(query.getCellphone());
        request.setApplyId(query.getApplyId());
        request.setBankCode(query.getBankCode());
        request.setStatus(query.getStatus());
        request.setUserId(query.getUserId());
        request.setUserName(query.getUserName());
        Date startTime = query.getStartTime();
        Date endTime = query.getEndTime();
        if (startTime != null && endTime != null && DateUtils.getDayDiff(startTime, endTime) != 0) {
            //结束时间与开始时间不在同一天
            startTime = DateUtils.getStartDatetime(endTime);
            endTime = DateUtils.getEndDatetime(endTime);
        }
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<PaginationSupport<PayDfCommandBo>> response = payDfComandDubboService.query(request);
        return data(response);
    }

    public Integer countPaymentProxyResults(PaymentProxyQuery query) throws ProcessBizException {
        PayDfCommandRequest request = new PayDfCommandRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setPhone(query.getCellphone());
        request.setApplyId(query.getApplyId());
        request.setBankCode(query.getBankCode());
        request.setStatus(query.getStatus());
        request.setUserId(query.getUserId());
        request.setUserName(query.getUserName());
        //不传时间 默认查询当前日期
        Date startTime = query.getStartTime();
        Date endTime = query.getEndTime();
        if (startTime != null && endTime != null && DateUtils.getDayDiff(startTime, endTime) != 0) {
            //结束时间与开始时间不在同一天
            startTime = DateUtils.getStartDatetime(endTime);
            endTime = DateUtils.getEndDatetime(endTime);
        }
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<Integer> response = payDfComandDubboService.count(request);
        return data(response);
    }

    public PaginationSupport<PayDkCommandBo> findDeductProxyResults(PaymentProxyQuery query) throws ProcessBizException {
        PayDkCommandRequest request = new PayDkCommandRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setApplyId(query.getApplyId());
        request.setBankCode(query.getBankCode());
        request.setStatus(query.getStatus());
        request.setPhone(query.getCellphone());
        request.setChannel(query.getChannel() == null ? "" : query.getChannel());
        request.setUserName(query.getUserName());
        Date startTime = query.getStartTime();
        Date endTime = query.getEndTime();
        if (startTime != null && endTime != null && DateUtils.getDayDiff(startTime, endTime) != 0) {
            //结束时间与开始时间不在同一天
            startTime = DateUtils.getStartDatetime(endTime);
            endTime = DateUtils.getEndDatetime(endTime);
        }
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<PaginationSupport<PayDkCommandBo>> response = payDkComandDubboService.query(request);
        return data(response);
    }

    public Integer countDeductProxyResults(PaymentProxyQuery query) throws ProcessBizException {
        PayDkCommandRequest request = new PayDkCommandRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        request.setApplyId(query.getApplyId());
        request.setBankCode(query.getBankCode());
        request.setStatus(query.getStatus());
        request.setPhone(query.getCellphone());
        request.setChannel(query.getChannel() == null ? "" : query.getChannel());
        request.setUserName(query.getUserName());
        Date startTime = query.getStartTime();
        Date endTime = query.getEndTime();
        if (startTime != null && endTime != null && DateUtils.getDayDiff(startTime, endTime) != 0) {
            //结束时间与开始时间不在同一天
            startTime = DateUtils.getStartDatetime(endTime);
            endTime = DateUtils.getEndDatetime(endTime);
        }
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        DubboResponse<Integer> response = payDkComandDubboService.count(request);
        return data(response);
    }

    public DubboResponse<List<PayInstructLogBo>> instructLog(PaymentInstructLogQuery query) throws ProcessBizException {
        PayInstructLogRequest request = new PayInstructLogRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        LOGGER.info("代付、代收指令日志查询======================》" + query.getId());
        DubboResponse<List<PayInstructLogBo>> response = payInstructLogDubboService.instructLog(request);
        return response;
    }
}
