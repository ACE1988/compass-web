package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.sdxd.api.vo.lending.RepaymentDetailQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.finance.RepaymentDetailDubboService;
import com.sdxd.data.dubbo.api.request.RepaymentDetailRequest;
import com.sdxd.data.dubbo.api.response.pojo.RepaymentDetailBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/4/27
 * 盛大小贷 还款单查询
 */
@Service
public class RepaymentDetailService {

    private Logger LOGGER = LoggerFactory.getLogger(RepaymentDetailService.class);

    @Reference(version = "1.0.0")
    private RepaymentDetailDubboService repaymentDetailDubboService;

    public PaginationSupport<RepaymentDetailBo> repaymentDetail(RepaymentDetailQuery query) throws ProcessBizException {
        RepaymentDetailRequest request = new RepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setId(query.getId());
        request.setStatus(query.getStatus());
        request.setRepaymentMethod(query.getRepaymentMethod());
        request.setRepaymentChannel(query.getRepaymentChannel());
        request.setStartRepaymentTime(query.getStartRepaymentTime());
        request.setEndRepaymentTime(query.getEndRepaymentTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        LOGGER.info("【还款单查询】 ============================= 》参数,{}", JSONObject.toJSON(request));
        DubboResponse<PaginationSupport<RepaymentDetailBo>> response = repaymentDetailDubboService.select(request);
        LOGGER.info("【还款单查询】 ============================= 》查询结果 ,{}", JSONObject.toJSON(data(response)));
        return  data(response);
    }

    public Integer repaymentDetailCount(RepaymentDetailQuery query) throws ProcessBizException{
        RepaymentDetailRequest request = new RepaymentDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setApplyId(query.getApplyId());
        request.setUserName(query.getUserName());
        request.setId(query.getId());
        request.setStatus(query.getStatus());
        request.setRepaymentMethod(query.getRepaymentMethod());
        request.setRepaymentChannel(query.getRepaymentChannel());
        request.setStartRepaymentTime(query.getStartRepaymentTime());
        request.setEndRepaymentTime(query.getEndRepaymentTime());
        LOGGER.info("【还款单查询】 ============================= 》查询条件 ,{}",JSONObject.toJSON(request));
        DubboResponse<Integer> response = repaymentDetailDubboService.count(request);
        LOGGER.info("【还款单查询】 ============================= 》查询结果 ,{}",JSONObject.toJSON(data(response)));
        return data(response);
    }
}
