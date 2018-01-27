package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.cms.dubbo.api.CmsSuspensionSettingDubboService;
import com.sdxd.cms.dubbo.api.pojo.CmsSuspensionSettingVo;
import com.sdxd.cms.dubbo.api.request.CmsSuspensionSettingRequest;
import com.sdxd.cms.dubbo.api.response.CmsSuspensionSettingResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.cms.CmsSuspensionSettingDataDubboService;
import com.sdxd.data.dubbo.api.response.CmsSuspensionSettingListResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sdxd.common.web.util.ResponseUtil.rest;

@Api(value = "SUSPENSION-API", produces = "application/json")
@Controller
@RequestMapping(value = "/suspension", produces = "application/json")
public class CmsSuspensionSettingController {

    @Reference(version = "1.0.0")
    private CmsSuspensionSettingDubboService cmsSuspensionSettingDubboService;

    @Reference(version = "1.0.0")
    private CmsSuspensionSettingDataDubboService cmsSuspensionSettingDataDubboService;

    @ApiOperation(value = "获取全部Suspension", notes = "获取全部Suspension")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getAll() {
        com.sdxd.data.dubbo.api.request.CmsSuspensionSettingRequest request = new com.sdxd.data.dubbo.api.request.CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<CmsSuspensionSettingListResponse> response = cmsSuspensionSettingDataDubboService.findAll(request);
        return rest(response);
    }

    @ApiOperation(value = "通过ID获取Suspension", notes = "通过ID获取Suspension")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getById(
            @ApiParam(value = "SUSPENSION ID", required = true) @PathVariable("id") String suspensionId
    ) {
        com.sdxd.data.dubbo.api.request.CmsSuspensionSettingRequest request = new com.sdxd.data.dubbo.api.request.CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(suspensionId);
        DubboResponse<com.sdxd.data.dubbo.api.response.CmsSuspensionSettingResponse> response = cmsSuspensionSettingDataDubboService.findById(request);
        return rest(response);
    }

    @ApiOperation(value = "创建SUSPENSION", notes = "创建Suspension")
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
    @ResourcePush(pushKey = "SUSPENSION_PUSH_URL", accessKey = "SUSPENSION_ACCESS_URL")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<CmsSuspensionSettingResponse> create(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody CmsSuspensionSettingVo vo) {
        CmsSuspensionSettingRequest request = new CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCmsSuspensionSettingVo(vo);
        DubboResponse<CmsSuspensionSettingResponse> response = cmsSuspensionSettingDubboService.saveCmsSuspensionSetting(request);
        return rest(response);
    }

    @ApiOperation(value = "修改Suspension", notes = "修改Suspension")
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
    @ResourcePush(pushKey = "SUSPENSION_PUSH_URL", accessKey = "SUSPENSION_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<CmsSuspensionSettingResponse> update(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "SUSPENSION ID", required = true) @PathVariable("id") String suspensionId,
            @Valid @RequestBody CmsSuspensionSettingVo vo) {
        CmsSuspensionSettingRequest request = new CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(suspensionId);
        request.setCmsSuspensionSettingVo(vo);
        DubboResponse<CmsSuspensionSettingResponse> response = cmsSuspensionSettingDubboService.updateCmsSuspensionSetting(request);
        return rest(response);
    }

    @ApiOperation(value = "删除Suspension", notes = "删除Suspension")
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
    @ResourcePush(pushKey = "SUSPENSION_PUSH_URL", accessKey = "SUSPENSION_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<CmsSuspensionSettingResponse> delete(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Suspension ID", required = true) @PathVariable("id") String suspensionId) {
        CmsSuspensionSettingRequest request = new CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(suspensionId);
        DubboResponse<CmsSuspensionSettingResponse> response = cmsSuspensionSettingDubboService.deleteCmsSuspensionSetting(request);
        return rest(response);
    }
}
