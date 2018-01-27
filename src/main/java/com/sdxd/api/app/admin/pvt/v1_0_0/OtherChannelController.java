package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.SystemVariableDubboService;
import com.sdxd.admin.dubbo.api.request.VariableCodeRequest;
import com.sdxd.admin.dubbo.api.response.SystemVariableResponse;
import com.sdxd.api.app.credit.pvt.v1_0_0.AdmittanceController;
import com.sdxd.api.service.OtherChannelService;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.api.vo.admin.*;
import com.sdxd.cms.dubbo.api.pojo.OtherChannelDetailVo;
import com.sdxd.cms.dubbo.api.response.OtherChannelDetailResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admittance.pvt
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/05/26     刘节                 Created
 */
@Api(value = "OtherChannel-API", produces = "application/json")
@Controller
@RequestMapping(value = "/other-channels", produces = "application/json")
public class OtherChannelController {

    private static final Logger log = LoggerFactory.getLogger(AdmittanceController.class);

    @Autowired
    private OtherChannelService otherChannelService;

    @Reference(version = "1.0.0")
    private SystemVariableDubboService systemVariableDubboService;

    @ApiOperation(value = "保存文案信息", notes = "保存文案信息")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/channel-describe", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> saveCopyWriting(
            @Valid @RequestBody ChannelDescribe describe) {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableValue("CHANNEL_DESCRIBE");
        request.setVariableCode(describe.getChannelDescribe());
        DubboResponse<Boolean> response = systemVariableDubboService.updateValueByCodeStr(request);
        return rest(response);
    }


    @ApiOperation(value = "文案信息查询接口", notes = "文案信息查询接口")
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
    @RequestMapping(value = "/channel-describe", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<SystemVariableResponse> getCopyWriting() {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableCode("CHANNEL_DESCRIBE");
        DubboResponse<SystemVariableResponse> response = systemVariableDubboService.getValueByCodeStr(request);
        return rest(response);
    }

    @ApiOperation(value = "展示方式保存接口", notes = "展示方式保存接口")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/show-type", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> saveShowWay(
            @Valid @RequestBody ChannelShowType showType) {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableValue("CHANNEL_SHOW_TYPE");
        request.setVariableCode(showType.getShowType());
        DubboResponse<Boolean> response = systemVariableDubboService.updateValueByCodeStr(request);
        return rest(response);
    }


    @ApiOperation(value = "展示方式查询接口", notes = "展示方式查询接口")
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
    @RequestMapping(value = "/show-type", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<SystemVariableResponse> getShowWay() {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableCode("CHANNEL_SHOW_TYPE");
        DubboResponse<SystemVariableResponse> response = systemVariableDubboService.getValueByCodeStr(request);
        return rest(response);
    }

    @ApiOperation(value = "添加新平台", notes = "添加新平台")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<OtherChannelDetailResponse> addChannel(
            @Valid @RequestBody AddOtherChannel add) {
        DubboResponse<OtherChannelDetailResponse> response = otherChannelService.add(add);
        return rest(response);
    }

    @ApiOperation(value = "平台列表查询", notes = "平台列表查询")
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
    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<OtherChannelDetailVo>> channels(
            @BeanParam QueryOtherChannels query
    ) {
        try {
            PaginationSupport<OtherChannelDetailVo> page =
                    forValue(new PaginationSupport<OtherChannelDetailVo>(null, 0)).
                            parallel(() -> otherChannelService.queryChannels(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> otherChannelService.count(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "修改平台信息", notes = "修改平台信息")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<OtherChannelDetailResponse> updateChannel(
            @ApiParam(value = "平台id", required = true) @PathVariable(value = "id") String id,
            @Valid @RequestBody UpdateOtherChannel query) {
        DubboResponse<OtherChannelDetailResponse> response = otherChannelService.update(query, id);
        return rest(response);
    }


    @ApiOperation(value = "删除平台", notes = "删除平台")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<OtherChannelDetailResponse> deleteChannel(
            @ApiParam(value = "平台id", required = true) @PathVariable(value = "id") String id) {
        DubboResponse<OtherChannelDetailResponse> response = otherChannelService.updateChannelDeleteFlag(id);
        return rest(response);
    }


    @ApiOperation(value = "关闭开启平台", notes = "关闭开启平台")
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
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @RequestMapping(value = "/on-off", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> onOff(
            @Valid @RequestBody OnOffOtherChannel query) {
        DubboResponse<Boolean> response = otherChannelService.onOff(query);
        return rest(response);
    }

    @ApiOperation(value = "开关状态", notes = "开关状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ResourcePush(pushKey = "OTHER_CHANNELS_PUSH_URL", accessKey = "OTHER_CHANNELS_ACCESS_URL", attached = {"app", "h5"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/on-off", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Integer> offStatus() {
        DubboResponse<Integer> response = otherChannelService.offStatus();
        return rest(response);
    }
}
