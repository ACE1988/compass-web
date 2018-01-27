package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.lending.PaymentExcDetailQuery;
import com.sdxd.api.vo.lending.PaymentProxyQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.pay.PayDZExcDetailDubboService;
import com.sdxd.data.dubbo.api.request.PayDZExcDetailRequest;
import com.sdxd.data.dubbo.api.request.PayDkCommandRequest;
import com.sdxd.data.dubbo.api.response.pojo.PayDZExcDetailBo;
import com.sdxd.data.dubbo.api.response.pojo.PayDkCommandBo;
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
 * 17/3/30     liujie                 Created
 */
@Service
public class AccountVerifyService {

    @Reference(version = "1.0.0")
    private PayDZExcDetailDubboService payDZExcDetailDubboService ;

    public PaginationSupport<PayDZExcDetailBo> query(PaymentExcDetailQuery query) throws ProcessBizException {
        PayDZExcDetailRequest request = new PayDZExcDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<PaginationSupport<PayDZExcDetailBo>> response = payDZExcDetailDubboService.query(request);
        return data(response);
    }

    public Integer count(PaymentExcDetailQuery query) throws ProcessBizException {
        PayDZExcDetailRequest request = new PayDZExcDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<Integer> response = payDZExcDetailDubboService.count(request);
        return data(response);
    }
}
