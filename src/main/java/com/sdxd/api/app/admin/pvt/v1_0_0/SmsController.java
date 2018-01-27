package com.sdxd.api.app.admin.pvt.v1_0_0;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.sdxd.api.vo.admin.SmsSQLQuery;
import com.sdxd.api.vo.admin.SmsSendRequest;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.trace.HideBody;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.risk.user.UserDataDubboService;
import com.sdxd.data.dubbo.api.risk.user.dto.UserPhoneQuerySqlRequest;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.message.dubbo.api.SmsDubboService;
import com.sdxd.message.dubbo.request.SmsBatchRequest;
import com.sdxd.message.dubbo.request.SmsChannelRequest;
import com.sdxd.message.dubbo.response.SmsChannelResponse;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * author: zhangxu
 */
@Api(value = "MARKETING-SMS-API", produces = "application/json")
@Controller
@RequestMapping(value = "/sms", produces = "application/json")
@HideBody
public class SmsController {

    @Reference(version = "1.0.0")
    private UserDataDubboService userDataDubboService;

    @Reference(version = "1.0.0")
    private SmsDubboService smsDubboService;

    @ApiOperation(value = "查询营销短信", notes = "查询营销短信")
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
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse querySmsList(@RequestBody SmsSQLQuery query
    ) {
        UserPhoneQuerySqlRequest request = new UserPhoneQuerySqlRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSql(query.getSqlQuery());
        DubboResponse<Set<String>> response = userDataDubboService.queryUserPhoneBySQL(request);
        List<String> phoneList = Lists.newArrayList(response.getData());
        Map<String, Object> map = new HashMap<>();
        int count = phoneList.size();
        map.put("count", count);
        map.put("phoneList", phoneList.subList(0, count >= 10 ? 10 : count));
        DubboResponse<Map<String, Object>> mapDubboResponse = new DubboResponse<>();
        mapDubboResponse.setStatus("0");
        mapDubboResponse.setMsg("请求成功");
        mapDubboResponse.setData(map);
        return ok(mapDubboResponse);
    }

    @ApiOperation(value = "发送营销短信", notes = "发送营销短信")
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
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse sendSms(@RequestBody SmsSendRequest send) {
        // 发送短信接口(签名、通道、短信内容)
        SmsBatchRequest request = new SmsBatchRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSign(send.getSign());
        request.setChannelCode(send.getChannel());
        request.setContent(send.getContent());

        // 获取发送短信的手机号列表
        UserPhoneQuerySqlRequest sqlRequest = new UserPhoneQuerySqlRequest();
        sqlRequest.setRequestId(BillNoUtils.GenerateBillNo());
        sqlRequest.setSql(send.getSqlQuery());
        DubboResponse<Set<String>> response = userDataDubboService.queryUserPhoneBySQL(sqlRequest);

        List<String> phoneList = Lists.newArrayList(response.getData());
        List<String> list = new ArrayList<>();
        for(String phone : phoneList){
            if (StringUtils.isNotBlank(phone)) {
                list.add(phone);
            }
        }
        request.setPhoneList(list);
        DubboResponse<Boolean> response1 = smsDubboService.sendBatchSms(request);
        if (!response1.getData()) {
            return fail("1", "营销短信发送失败");
        }
        return ok(response1);

    }


    @ApiOperation(value = "获取短信渠道", notes = "获取短信渠道")
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
    @RequestMapping(value = "/channel", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse channel() {
        SmsChannelRequest request = new SmsChannelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<List<SmsChannelResponse>> response = smsDubboService.channel(request);
        return ok(response);
    }

}
