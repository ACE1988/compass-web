package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.credit.ApprovalMark;
import com.sdxd.approve.dubbo.api.ApproveCommentsDubboService;
import com.sdxd.approve.dubbo.api.request.CommentsQueryRequest;
import com.sdxd.approve.dubbo.api.request.CommentsRequest;
import com.sdxd.approve.dubbo.api.response.CommentsQueryResponse;
import com.sdxd.approve.dubbo.api.response.CommentsResponse;
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
@Api(value = "Approval-Mark-API", produces = "application/json")
@Controller
@RequestMapping(value = "/marks", produces = "application/json")
public class ApprovalMarkController {

    @Reference(version = "1.0.0")
    private ApproveCommentsDubboService approveCommentsDubboService;

    @ApiOperation(value = "提交注记", notes = "提交注记")
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<CommentsResponse> createMark(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody ApprovalMark mark
    ) {
        CommentsRequest request = new CommentsRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSystemUserId(operatorId);
        request.setApproveId(mark.getAdmittanceId());
        request.setTv1(mark.getTv1());
        request.setTv2(mark.getTv2());
        request.setTv3(mark.getTv3());
        request.setRemarks(mark.getRemarks());
        DubboResponse<CommentsResponse> response = approveCommentsDubboService.saveComment(request);
        return rest(response);
    }

    @ApiOperation(value = "获取历史注记", notes = "获取历史注记")
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
    public RestResponse<CommentsQueryResponse> getMarks(
            @ContextParam(value = "principal") String operatorId,
            @ApiParam(name = "admittance_id", value = "准入申请ID", required = true)
            @RequestParam(value = "admittance_id") String admittanceId
    ) {
        CommentsQueryRequest request = new CommentsQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSystemUserId(operatorId);
        request.setApproveId(admittanceId);
        DubboResponse<CommentsQueryResponse> response = approveCommentsDubboService.queryComments(request);
        return rest(response);
    }
}
