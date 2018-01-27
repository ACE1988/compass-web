package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.activities.api.ActivityDubboService;
import com.sdxd.activities.api.dto.activity.*;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.cms.dubbo.api.pojo.CmsSuspensionSettingVo;
import com.sdxd.cms.dubbo.api.request.CmsSuspensionSettingRequest;
import com.sdxd.cms.dubbo.api.response.CmsSuspensionSettingResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.PagingResponse;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;

@Api(value = "Activity-API", produces = "application/json")
@Controller
@RequestMapping(value = "/activity", produces = "application/json")
public class ActivityController {

    @Reference(version = "1.0.0")
    private ActivityDubboService activityDubboService;

    @ApiOperation(value = "查询活动列表", notes = "查询活动列表")
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
    public RestResponse<List<ActivityInfo>> queryOpenActivitys(){
        DubboResponse<List<ActivityInfo>> response = activityDubboService.queryAllAcivitys();
        return rest(response);
    }

    @ApiOperation(value = "创建活动", notes = "创建活动")
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
    @ResourcePush(pushKey = "ACTIVITY_PUSH_URL", accessKey = "ACTIVITY_ACCESS_URL")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> create(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody ActivityInfo info) {
        ActivityItemReq request = new ActivityItemReq();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setActivityInfo(info);
        DubboResponse<Boolean> response = activityDubboService.addActivity(request);
        return rest(response);
    }

    @ApiOperation(value = "更新活动", notes = "更新活动")
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
    @ResourcePush(pushKey = "ACTIVITY_PUSH_URL", accessKey = "ACTIVITY_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> update(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "ACTIVITY ID", required = true) @PathVariable("id") String activityId,
            @Valid @RequestBody ActivityInfo info) {
        ActivityItemReq request = new ActivityItemReq();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        info.setId(activityId);
        request.setActivityInfo(info);
        DubboResponse<Boolean> response = activityDubboService.updateActivity(request);
        return rest(response);
    }

    @ApiOperation(value = "删除活动", notes = "删除活动")
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
    @ResourcePush(pushKey = "ACTIVITY_PUSH_URL", accessKey = "ACTIVITY_ACCESS_URL")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<Boolean> delete(
            @ContextParam(value = "principal") String userId,
            @ApiParam(value = "Suspension ID", required = true) @PathVariable("id") String activityId) {
        QueryByIdReq request = new QueryByIdReq();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(activityId);
        DubboResponse<Boolean> response = activityDubboService.deleteActivity(request);
        return rest(response);
    }

    @ApiOperation(value = "查询活动分页列表", notes = "查询活动分页列表")
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
    @RequestMapping(value = "/queryActivity", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ActivityInfo>> queryActivity(
            @Valid @BeanParam ActivityQueryReq request){
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<PaginationSupport<ActivityInfo>> response = activityDubboService.queryActivity(request);
        return rest(response);
    }

}
