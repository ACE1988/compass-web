package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.apply.api.ApplyDubboService;
import com.sdxd.apply.request.BaseUserIdRequest;
import com.sdxd.apply.response.ApplyMoneyInfo;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.security.JwtAuthentication;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.credit.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/24     melvin                 Created
 */
@Api(value = "Loan-API", produces = "application/json")
@Controller
@RequestMapping(value = "/loans", produces = "application/json")
public class LoanController {

    @Reference(version = "1.0.0")
    private ApplyDubboService applyDubboService;

    @ApiOperation(value = "获取用户借款记录", notes = "获取用户借款记录")
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
    public RestResponse<List<ApplyMoneyInfo>> getLoans(
            @ContextParam(value = "authentication") JwtAuthentication authentication,
            @ApiParam(value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        BaseUserIdRequest request = new BaseUserIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<List<ApplyMoneyInfo>> response = applyDubboService.queryApplyList(request);
        return rest(response);
    }
}
