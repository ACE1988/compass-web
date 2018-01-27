package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.credit.ChannelResourceQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.finance.XDChannelDubboService;
import com.sdxd.data.dubbo.api.request.XDChannelRequest;
import com.sdxd.data.dubbo.api.response.pojo.XDChannelBO;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/4/24
 * 盛大小贷
 */
@Service
public class ChannelResourceService {
    
    @Reference(version = "1.0.0")
    private XDChannelDubboService xdChannelDubboService;

    public PaginationSupport<XDChannelBO> getChannelResource(ChannelResourceQuery query) throws ProcessBizException {
        XDChannelRequest request = new XDChannelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setChannelName(query.getChannelName());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<PaginationSupport<XDChannelBO>> response = xdChannelDubboService.selectChannel(request);
        return data(response);
    }

    public Integer countChannelResource(ChannelResourceQuery query) throws ProcessBizException {
        XDChannelRequest request = new XDChannelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setChannelName(query.getChannelName());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        DubboResponse<Integer> response = xdChannelDubboService.count(request);
        return data(response);
    }
}
