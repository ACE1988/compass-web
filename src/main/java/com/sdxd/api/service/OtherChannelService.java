package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.admin.AddOtherChannel;
import com.sdxd.api.vo.admin.OnOffOtherChannel;
import com.sdxd.api.vo.admin.QueryOtherChannels;
import com.sdxd.api.vo.admin.UpdateOtherChannel;
import com.sdxd.cms.dubbo.api.OtherChannelDetailDubboService;
import com.sdxd.cms.dubbo.api.pojo.OtherChannelDetailVo;
import com.sdxd.cms.dubbo.api.request.*;
import com.sdxd.cms.dubbo.api.response.OtherChannelDetailResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.BaseRequest;
import com.sdxd.framework.dubbo.DubboResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * @author liujie
 * @Date 2017/5/26
 * 盛大小贷
 */
@Service
public class OtherChannelService {

    private Logger LOGGER = LoggerFactory.getLogger(OtherChannelService.class);

    @Reference(version = "1.0.0")
    private OtherChannelDetailDubboService otherChannelDetailDubboService;


    public DubboResponse<OtherChannelDetailResponse> add(AddOtherChannel query) {
        OtherChannelDetailRequest request = new OtherChannelDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setChannelType(query.getChannelType());
        request.setChannelSlogn(query.getChannelSlogn());
        request.setChannelLogo(query.getChannelLogo());
        request.setChannelIndex(query.getChannelIndex());
        request.setChannelName(query.getChannelName());
        request.setChannelUrl(query.getChannelUrl());
        request.setChannelShow(query.getChannelShow());
        request.setChannelImage(query.getChannelImage());
        List channelShowWay = query.getChannelShowWay();
        if (channelShowWay != null && channelShowWay.size() == 1) {
            request.setChannelShowType(channelShowWay.get(0).toString());
        } else {
            request.setChannelShowType(channelShowWay.get(0).toString() + "," + channelShowWay.get(1).toString());
        }
        DubboResponse<OtherChannelDetailResponse> response = otherChannelDetailDubboService.save(request);
        return response;
    }

    public Integer count(QueryOtherChannels query) throws ProcessBizException {
        BaseRequest request = new BaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<Integer> response = otherChannelDetailDubboService.queryCount(request);
        return data(response);
    }

    public PaginationSupport<OtherChannelDetailVo> queryChannels(QueryOtherChannels query) throws ProcessBizException {
        QueryOtherChannelDetailRequest request = new QueryOtherChannelDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(query.getPageNo() == null ? 1 : query.getPageNo());
        request.setPageSize(query.getPageSize() == null ? 15 : query.getPageSize());
        DubboResponse<PaginationSupport<OtherChannelDetailVo>> response = otherChannelDetailDubboService.query(request);
        return data(response);
    }

    public DubboResponse<OtherChannelDetailResponse> update(UpdateOtherChannel query, String id) {
        UpdateOtherChannelDetailRequest request = new UpdateOtherChannelDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setChannelUrl(query.getChannelUrl());
        request.setId(id);
        request.setChannelName(query.getChannelName());
        request.setChannelIndex(query.getChannelIndex());
        request.setChannelLogo(query.getChannelLogo());
        request.setChannelSlogn(query.getChannelSlogn());
        request.setChannelType(query.getChannelType());
        request.setChannelShow(query.getChannelShow());
        request.setChannelImage(query.getChannelImage());
        List channelShowWay = query.getChannelShowWay();
        if (channelShowWay.size() == 1) {
            request.setChannelShowType(channelShowWay.get(0).toString());
        } else {
            request.setChannelShowType(channelShowWay.get(0).toString() + "," + channelShowWay.get(1).toString());
        }
        DubboResponse<OtherChannelDetailResponse> response = otherChannelDetailDubboService.update(request);
        return response;
    }

    public DubboResponse<OtherChannelDetailResponse> updateChannelDeleteFlag(String id) {
        DeleteOtherChannelDetailRequest request = new DeleteOtherChannelDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(id);
        request.setDeleteFlag(1);//强制删除
        DubboResponse<OtherChannelDetailResponse> response = otherChannelDetailDubboService.updateDeleteFlag(request);
        return response;
    }

    public DubboResponse<Boolean> onOff(OnOffOtherChannel query) {
        OnOffOtherChannelRequest request = new OnOffOtherChannelRequest();
        request.setOnOff(query.getOnOff());
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<Boolean> response = otherChannelDetailDubboService.onOff(request);
        return response;
    }

    public DubboResponse<Integer> offStatus() {
        BaseRequest request = new BaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<Integer> response = otherChannelDetailDubboService.offStatus(request);
        return response;
    }
}