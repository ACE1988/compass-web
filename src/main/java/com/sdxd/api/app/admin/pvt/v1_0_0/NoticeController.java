package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.api.vo.cms.Notice;
import com.sdxd.api.vo.cms.NoticeQuery;
import com.sdxd.cms.dubbo.api.CmsNoticeDubboService;
import com.sdxd.cms.dubbo.api.request.CmsNoticeRequest;
import com.sdxd.cms.dubbo.api.request.CmstomNoticeRequest;
import com.sdxd.cms.dubbo.api.response.CmsNoticeResponse;
import com.sdxd.cms.dubbo.api.response.QueryCmsNoticeResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
@Api(value = "Notice-API", produces = "application/json")
@Controller
@RequestMapping(value = "/notice", produces = "application/json")
public class NoticeController {

    @Reference(version = "1.0.0")
    private CmsNoticeDubboService cmsNoticeDubboService;

    @ApiOperation(value = "获取Notice列表", notes = "获取Notice列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<QueryCmsNoticeResponse> get(
            @ContextParam(value = "principal") String userId) {
        CmsNoticeRequest request = new CmsNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<QueryCmsNoticeResponse> response = cmsNoticeDubboService.queryCmsNotic(request);
        return rest(response);
    }


    @ApiOperation(value = "获取是否下线的Notice列表", notes = "获取是否下线的Notice列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/getStatus", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<QueryCmsNoticeResponse> getNoticeStatus(
            @ContextParam(value = "principal") String userId,
            @RequestParam(value = "status") Boolean status) {
        CmstomNoticeRequest request = new CmstomNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStatus(status);
        DubboResponse<QueryCmsNoticeResponse> response = cmsNoticeDubboService.queryCmsNoticeStatus(request);
        return rest(response);
    }

    @ApiOperation(value = "创建Notice", notes = "创建Notice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @ResourcePush(pushKey = "NOTICE_PUSH_URL", accessKey = "NOTICE_ACCESS_URL")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<CmsNoticeResponse> create(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody Notice notice) {
        CmsNoticeRequest request = new CmsNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setTitle(notice.getTitle());
        request.setLink(notice.getLink());
        request.setOfflineTime(notice.getOfflineTime());
        DubboResponse<CmsNoticeResponse> response = cmsNoticeDubboService.addCmsNotic(request);
        return rest(response);
    }

    @ApiOperation(value = "修改Notice", notes = "修改Notice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @ResourcePush(pushKey = "NOTICE_PUSH_URL", accessKey = "NOTICE_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<CmsNoticeResponse> update(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Notice ID", required = true) @PathVariable("id") String noticeId,
            @Valid @RequestBody Notice notice) {
        CmsNoticeRequest request = new CmsNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(noticeId);
        request.setTitle(notice.getTitle());
        request.setLink(notice.getLink());
        request.setOfflineTime(notice.getOfflineTime());
        DubboResponse<CmsNoticeResponse> response = cmsNoticeDubboService.updataCmsNotic(request);
        return rest(response);
    }

    @ApiOperation(value = "删除Notice", notes = "删除Notice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @ResourcePush(pushKey = "NOTICE_PUSH_URL", accessKey = "NOTICE_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<CmsNoticeResponse> delete(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Notice ID", required = true) @PathVariable("id") String noticeId) {
        CmsNoticeRequest request = new CmsNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(noticeId);
        DubboResponse<CmsNoticeResponse> response = cmsNoticeDubboService.deleteCmsNotic(request);
        return rest(response);
    }
}
