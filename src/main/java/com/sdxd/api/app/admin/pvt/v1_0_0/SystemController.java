package com.sdxd.api.app.admin.pvt.v1_0_0;


import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.SystemPropertiesDubboService;
import com.sdxd.admin.dubbo.api.request.SyncZooKeeperRequest;
import com.sdxd.admin.dubbo.api.request.SystemPropertiesFileRequest;
import com.sdxd.admin.dubbo.api.request.SystemPropertiesPageRequest;
import com.sdxd.admin.dubbo.api.request.SystemPropertiesRequest;
import com.sdxd.admin.dubbo.api.response.SystemPropertiesResponse;
import com.sdxd.api.vo.admin.QuerySystemPropertyList;
import com.sdxd.api.vo.admin.SystemProperty;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.util.CommonFileUtil;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.sdxd.api.vo.SystemConfigErrors.*;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

@Api(value = "System-API", produces = "application/json")
@Controller
@RequestMapping(value = "/system/property", produces = "application/json")
public class SystemController {

    @Reference(version = "1.0.0")
    private SystemPropertiesDubboService systemPropertiesDubboService;

    @ApiOperation(value = "添加配置", notes = "添加配置")
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
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse createProperty(
            @Valid @RequestBody SystemProperty property) {
        SystemPropertiesRequest request = new SystemPropertiesRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAppName(property.getAppName());
        request.setPropName(property.getPropName());
        request.setPropValue(property.getPropValue());
        request.setZkServerAddress(property.getZkAddress());
        DubboResponse<Boolean> dubboResponse = systemPropertiesDubboService.save(request);
        if(dubboResponse.getData()){
            return ok();
        } else {
            return fail(CREATE_CONFIG_FAIL);
        }
    }

    @ApiOperation(value = "修改配置", notes = "修改配置")
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
    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updateProperty(
            @Valid @RequestBody SystemProperty property) {
        SystemPropertiesRequest request = new SystemPropertiesRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAppName(property.getAppName());
        request.setPropName(property.getPropName());
        request.setPropValue(property.getPropValue());
        request.setZkServerAddress(property.getZkAddress());
        DubboResponse<Boolean> dubboResponse = systemPropertiesDubboService.update(request);
        if(dubboResponse.getData()){
            return ok();
        } else {
            return fail(UPDATE_CONFIG_FAIL);
        }
    }

    @ApiOperation(value = "删除配置", notes = "删除配置")
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
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse deleteProperty(
            @Valid @BeanParam SystemProperty property) {
        SystemPropertiesRequest request = new SystemPropertiesRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAppName(property.getAppName());
        request.setPropName(property.getPropName());
        request.setZkServerAddress(property.getZkAddress());
        DubboResponse<Boolean> dubboResponse = systemPropertiesDubboService.delete(request);
        if(dubboResponse.getData()){
            return ok();
        } else {
            return fail(DELETE_CONFIG_FAIL);
        }
    }

    @ApiOperation(value = "同步配置", notes = "同步配置")
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
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse syncProperty(
            @Valid @RequestParam String zkAddress) {
        SyncZooKeeperRequest request = new SyncZooKeeperRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setZkServerAddress(zkAddress);
        DubboResponse<Boolean> dubboResponse = systemPropertiesDubboService.sync(request);
        if(dubboResponse.getData()){
            return ok();
        } else {
            return fail(SYNC_CONFIG_FAIL);
        }
    }

    @ApiOperation(value = "查询配置", notes = "查询配置")
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
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<SystemPropertiesResponse>> queryProperty(
            @Valid @BeanParam QuerySystemPropertyList property
    ) {
        SystemPropertiesPageRequest request = new SystemPropertiesPageRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAppName(property.getAppName());
        request.setPropName(property.getPropName());
        request.setCurrentPage(property.getCurrentIndex());
        request.setPageSize(property.getPageSize());

        DubboResponse<PaginationSupport<SystemPropertiesResponse>> dubboResponse =
                systemPropertiesDubboService.queryProperties(request);

        return rest(dubboResponse);

    }

    @ApiOperation(value = "查询系统配置ZooKeeper地址", notes = "查询系统配置ZooKeeper地址")
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
    @RequestMapping(value = "/query/zkAddress", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<String>> queryDistinctZkAddress() {
        DubboResponse<List<String>> dubboResponse =
                systemPropertiesDubboService.queryDistinctZkAddress();
        return rest(dubboResponse);
    }

    @ApiOperation(value = "查询系统配置应用名称", notes = "查询系统配置应用名称")
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
    @RequestMapping(value = "/query/appNames", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<String>> queryDistinctAppNames() {
        DubboResponse<List<String>> dubboResponse =
                systemPropertiesDubboService.queryDistinctAppNames();
        return rest(dubboResponse);
    }

    @ApiOperation(value = "文件导入请求", notes = "文件导入请求")
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
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse loadPropertiesFile(
            @Valid @RequestParam String appName,
            @Valid @RequestParam String zkAddress,
            HttpServletRequest req) {
        MultipartFile file = CommonFileUtil.getMultipartFile(req, "file");// 获取上传文件名

        SystemPropertiesFileRequest request = new SystemPropertiesFileRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAppName(appName);
        request.setZkServerAddress(zkAddress);
        try {
            byte[] bytes = file.getBytes();
            request.setPropertiesFile(bytes);
            DubboResponse<List<String>> dubboResponse =
                    systemPropertiesDubboService.loadPropertiesFile(request);
            return rest(dubboResponse, o -> {
                StringBuilder repeat = new StringBuilder();
                for (String str : o) {
                    repeat.append(str).append(" ");
                }
                return fail(LOAD_CONFIG_REPEAT.getCode(), repeat.toString());
            });
        } catch (IOException e) {
            return fail(FETCH_CONFIG_BYTE_ARRAY_ERROR);
        }
    }

}
