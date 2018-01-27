package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.api.vo.cms.Banner;
import com.sdxd.api.vo.cms.BannerQuery;
import com.sdxd.cms.dubbo.api.CmsBannerDubboService;
import com.sdxd.cms.dubbo.api.request.CmsBannerRequest;
import com.sdxd.cms.dubbo.api.request.DeleteCmsBannerRequest;
import com.sdxd.cms.dubbo.api.request.QueryCmsBannerRequest;
import com.sdxd.cms.dubbo.api.response.CmsBannerResponse;
import com.sdxd.cms.dubbo.api.response.QueryCmsBannerResponse;
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
@Api(value = "Banner-API", produces = "application/json")
@Controller
@RequestMapping(value = "/banners", produces = "application/json")
public class BannerController {

    @Reference(version = "1.0.0")
    private CmsBannerDubboService cmsBannerDubboService;

    @ApiOperation(value = "获取Banner列表", notes = "获取Banner列表")
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
    public RestResponse<QueryCmsBannerResponse> get(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody(required = false) BannerQuery query) {
        QueryCmsBannerRequest request = new QueryCmsBannerRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        if (query != null) {
            request.setId(query.getId());
            request.setName(query.getName());
            request.setType(query.getType());
            request.setImageCode(query.getImageCode());
            request.setPageId(query.getPageId());
            request.setPosition(query.getPosition());
        }
        DubboResponse<QueryCmsBannerResponse> response = cmsBannerDubboService.queryCmsBanner(request);
        return rest(response);
    }

    @ApiOperation(value = "创建Banner", notes = "创建Banner")
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
    @ResourcePush(pushKey = "BANNER_PUSH_URL", accessKey = "BANNER_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<CmsBannerResponse> create(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody Banner banner) {
        CmsBannerRequest request = new CmsBannerRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setName(banner.getName());
        request.setType(banner.getType());
        request.setImageCode(banner.getImageCode());
        request.setImageUrl(banner.getImageUrl());
        request.setImageLink(banner.getImageLink());
        request.setImageOrder(banner.getImageOrder());
        request.setPageId(banner.getPageId());
        request.setPosition(banner.getPosition());
        DubboResponse<CmsBannerResponse> response = cmsBannerDubboService.addCmsBanner(request);
        return rest(response);
    }

    @ApiOperation(value = "修改Banner", notes = "修改Banner")
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
    @ResourcePush(pushKey = "BANNER_PUSH_URL", accessKey = "BANNER_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<CmsBannerResponse> update(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Banner ID", required = true) @PathVariable("id") String bannerId,
            @Valid @RequestBody Banner banner) {
        CmsBannerRequest request = new CmsBannerRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(bannerId);
        request.setName(banner.getName());
        request.setType(banner.getType());
        request.setImageCode(banner.getImageCode());
        request.setImageUrl(banner.getImageUrl());
        request.setImageLink(banner.getImageLink());
        request.setImageOrder(banner.getImageOrder());
        request.setPageId(banner.getPageId());
        request.setPosition(banner.getPosition());
        DubboResponse<CmsBannerResponse> response = cmsBannerDubboService.updataCmsBanner(request);
        return rest(response);
    }

    @ApiOperation(value = "删除Banner", notes = "删除Banner")
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
    @ResourcePush(pushKey = "BANNER_PUSH_URL", accessKey = "BANNER_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<CmsBannerResponse> delete(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Banner ID", required = true) @PathVariable("id") String bannerId) {
        DeleteCmsBannerRequest request = new DeleteCmsBannerRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(bannerId);
        DubboResponse<CmsBannerResponse> response = cmsBannerDubboService.deleteCmsBanner(request);
        return rest(response);
    }
}
