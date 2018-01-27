package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.admin.Feed;
import com.sdxd.api.vo.admin.FeedWithoutId;
import com.sdxd.api.vo.admin.QueryFeedList;
import com.sdxd.cms.dubbo.api.CmsFeedDubboService;
import com.sdxd.cms.dubbo.api.pojo.CmsFeedVo;
import com.sdxd.cms.dubbo.api.request.CmsFeedPageRequest;
import com.sdxd.cms.dubbo.api.request.CmsFeedRequest;
import com.sdxd.cms.dubbo.api.response.CmsFeedResponse;
import com.sdxd.common.utils.BeanUtils;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.vo.ErrorCode.ApiError.PARAMETER_VALIDATION_ERROR;
import static com.sdxd.common.web.vo.ErrorCode.SystemError.SERVER_INTERNAL_ERROR;

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
 * 2017/8/18    wenzhou.xu              Created
 */
@Service
public class FeedService {

    @Reference(version = "1.0.0")
    private CmsFeedDubboService cmsFeedDubboService;

    /**
     * 查询Feed素材列表
     *
     * @param query
     * @return
     */
    public PaginationSupport<Feed> searchCmsFeedList(QueryFeedList query) throws ProcessBizException {
        CmsFeedPageRequest request = new CmsFeedPageRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(query.getPageNo());
        request.setPageSize(query.getPageSize());
        request.setDisplay(query.getDisplay());
        request.setTemplateList(query.getTemplateList());

        DubboResponse<CmsFeedResponse> response = cmsFeedDubboService.searchCmsFeedList(request);
        PaginationSupport page = data(response).getPage();
        List<CmsFeedVo> voList = page.getList();
        List<Feed> feedList = new ArrayList<>();
        for(CmsFeedVo vo : voList){
            Feed feed = new Feed();
            try {
                BeanUtils.copyProperties(feed, vo);
                feedList.add(feed);
            }
            catch (Exception e){
                throw new ProcessBizException(SERVER_INTERNAL_ERROR);
            }
        }

        page.setList(feedList);
        return page;
    }

    /**
     * 查询Feed素材明细
     *
     * @param id
     * @return
     */
    public CmsFeedVo queryCmsFeed(String id) throws ProcessBizException {
        CmsFeedRequest request = new CmsFeedRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(id);

        DubboResponse<CmsFeedResponse> response = cmsFeedDubboService.queryCmsFeed(request);
        return data(response).getCmsFeedVo();
    }

    /**
     * 更新Feed素材
     *
     * @param feedWithoutId
     * @return
     */
    public Boolean saveCmsFeed(FeedWithoutId feedWithoutId) throws ProcessBizException {
        List<String> imgList = feedWithoutId.getImgList();
        for(String imgUrl : imgList)
            if (StringUtils.isBlank(imgUrl))
                throw new ProcessBizException(PARAMETER_VALIDATION_ERROR);

        CmsFeedRequest request = new CmsFeedRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        CmsFeedVo cmsFeedVo = new CmsFeedVo();
        try {
            BeanUtils.copyProperties(cmsFeedVo, feedWithoutId);
        }
        catch (Exception e){
            throw new ProcessBizException(SERVER_INTERNAL_ERROR);
        }
        request.setCmsFeedVo(cmsFeedVo);

        DubboResponse<CmsFeedResponse> response = cmsFeedDubboService.saveCmsFeed(request);
        return data(response).getSuccess();
    }

    /**
     * 删除Feed素材
     *
     * @param id
     * @return
     */
    public Boolean deleteCmsFeed(String id) throws ProcessBizException{
        CmsFeedRequest request = new CmsFeedRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(id);

        DubboResponse<CmsFeedResponse> response = cmsFeedDubboService.deleteCmsFeed(request);

        return data(response).getSuccess();
    }
}
