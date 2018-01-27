package com.sdxd.api.app.flow.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.flow.FlowSdxdQuery;
import com.sdxd.api.vo.flow.FlowSourceListQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.external.api.FlowChannelDubboService;
import com.sdxd.external.api.FlowDailyDubboService;
import com.sdxd.external.api.request.FlowChannelRequest;
import com.sdxd.external.api.request.FlowDailyRequest;
import com.sdxd.external.api.request.FlowSourceListRequest;
import com.sdxd.external.api.response.FlowChannelResponse;
import com.sdxd.external.api.response.FlowDailyResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * Created by matteo on 2017/7/19.
 * 流超项目的接口
 */
@Api(value = "Flow-API", produces = "application/json")
@Controller
@RequestMapping(value = "/flow", produces = "application/json")
public class FlowController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FlowController.class);

    @Reference(version = "1.0.0")
    private FlowChannelDubboService flowChannelDubboService;

    @Reference(version = "1.0.0")
    private FlowDailyDubboService flowDailyDubboService;


    @ApiOperation(value = "获得渠道信息", notes = "获得渠道信息")
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
    @RequestMapping(value = "/flow-info", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<FlowChannelResponse> getChannelInfo(
            @ContextParam(value = "principal") String operatorId) {
        FlowChannelRequest request = new FlowChannelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(operatorId);
        LOGGER.info("request:{}", request);
        DubboResponse<FlowChannelResponse> response = flowChannelDubboService.selectByPrimaryId(request);
        return rest(response);
    }

    @ApiOperation(value = "获得渠道组", notes = "获得渠道组")
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
    @RequestMapping(value = "/flow-source-list", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<String>> getChannelInfo(
            @BeanParam FlowSourceListQuery query) {
        LOGGER.info("request:{}", query);
        FlowSourceListRequest request = new FlowSourceListRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(query.getId());
        DubboResponse<List<String>> response = flowDailyDubboService.getSourceList(request);
        return rest(response);
    }


    @ApiOperation(value = "获得流超数据", notes = "获得流超数据")
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
    @RequestMapping(value = "/flow-data", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<FlowDailyResponse>> getChannelInfo(
            @BeanParam FlowSdxdQuery query) {
        Date startDate = query.getStartDate();
        Date endDate = query.getEndDate();
        if (startDate == null && endDate == null) {
            startDate = java.sql.Date.valueOf(LocalDate.now().minusDays(15).toString());
            endDate = java.sql.Date.valueOf(LocalDate.now().minusDays(1).toString());
        } else if (startDate != null && endDate == null) {
            endDate = java.sql.Date.valueOf(LocalDate.now().minusDays(1).toString());
        } else if (startDate == null) {
            startDate = java.sql.Date.valueOf("2016-06-22");
        }
        FlowDailyRequest flowDailyRequest = new FlowDailyRequest();
        flowDailyRequest.setRequestId(BillNoUtils.GenerateBillNo());
        flowDailyRequest.setId(query.getId());
        flowDailyRequest.setStartDate(startDate);
        flowDailyRequest.setEndDate(endDate);
        Integer count = flowDailyDubboService.count(flowDailyRequest).getData();
        LOGGER.info("pageCount:{}", count);
        flowDailyRequest.setPageSize(query.getPageSize());
        flowDailyRequest.setCurrentPage(query.getPageNo());
        flowDailyRequest.setWay(query.getWay());
        flowDailyRequest.setSource(query.getSource());
        DubboResponse<PaginationSupport<FlowDailyResponse>> response = flowDailyDubboService.selectByGroup(flowDailyRequest);
        response.getData().setTotalCount(count);
        response.getData().setPageCount(count / query.getPageSize() + 1);
        LOGGER.info("response:{}", response.getData().getList());
        return rest(response);
    }
}
