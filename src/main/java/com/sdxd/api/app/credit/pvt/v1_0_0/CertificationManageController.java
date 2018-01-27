package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.AdminUserGroupDubboService;
import com.sdxd.admin.dubbo.api.request.AdjustmentPojo;
import com.sdxd.admin.dubbo.api.request.AdjustmentRequest;
import com.sdxd.admin.dubbo.api.request.QueryGroupRequest;
import com.sdxd.admin.dubbo.api.response.MemberGroupResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * Created by matteo on 2017/7/12.
 */

@Api(value = "Credit-Manager-API", produces = "application/json")
@Controller
@RequestMapping(value = "/certification-manager", produces = "application/json")
public class CertificationManageController {

    @Reference(version = "1.0.0")
    private AdminUserGroupDubboService adminUserGroupDubboService;

    @ApiOperation(value = "得到分配组", notes = "得到分配组")
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
    @RequestMapping(value = "/get-group", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<MemberGroupResponse> queryGroup(
            @ContextParam(value = "principal") String operatorId
    ) {
        QueryGroupRequest request = new QueryGroupRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setOperatorId(operatorId);
        request.setLeaderIds(null);
        DubboResponse<MemberGroupResponse> group = adminUserGroupDubboService.queryGroup(request);
        return rest(group);
    }

    @ApiOperation(value = "给信审员分配组", notes = "给信审员分配组")
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
    @RequestMapping(value = "/allocation", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> adjustMember(
            @ContextParam(value = "principal") String operatorId,
            @RequestBody List<AdjustmentPojo> adjustmentPojo
    ) {
        AdjustmentRequest request = new AdjustmentRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPojos(adjustmentPojo);
        request.setOperatorId(operatorId);
        DubboResponse<Boolean> member = adminUserGroupDubboService.adjustMember(request);
        return rest(member);
    }
}
