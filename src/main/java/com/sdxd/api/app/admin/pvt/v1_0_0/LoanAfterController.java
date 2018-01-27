package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.LoanAfterService;
import com.sdxd.api.vo.LoanAfterTemplate;
import com.sdxd.api.vo.admin.CreditCheck;
import com.sdxd.api.vo.admin.QueryUserList;
import com.sdxd.common.web.rest.MultiViewResponseBody;
import com.sdxd.common.web.rest.View;
import com.sdxd.common.web.util.CommonFileUtil;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.QueryParam;
import java.util.*;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pvt.v1_0_0
 * 系统名           ：贷后管理接口
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/8    wenzhou.xu              Created
 */
@Api(value = "LoanAfter-API", produces = "application/json")
@Controller
@RequestMapping(value = "/loan-after", produces = "application/json")
public class LoanAfterController {

    @Autowired
    private LoanAfterService loanAfterService;

    @ApiOperation(value = "通过文件导入批量查询手机号", notes = "通过文件导入批量查询手机号")
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
    @RequestMapping(value = "/query-phone-list", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Object> queryPhoneList(HttpServletRequest request) {
        try {
            List<LoanAfterTemplate> phoneList = CommonFileUtil.readCsv(request, LoanAfterTemplate.class);
            Set<LoanAfterTemplate> phoneSet = new HashSet<>();
            phoneSet.addAll(phoneList);
            phoneList = new ArrayList<>();
            phoneList.addAll(phoneSet);
            Map<String, String> map = new HashMap<>();
            map.put("batchId", loanAfterService.queryPhoneList(phoneList));
            return ok(map);
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询手机号", notes = "查询手机号")
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
    @RequestMapping(value = "/query-phone", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> queryPhone(@RequestParam(value = "phone") String phone) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("batchId", loanAfterService.queryPhone(phone));
            return ok(map);
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "根据批次号分页查询本批次用户数据", notes = "根据批次号分页查询本批次用户数据")
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
    @RequestMapping(value = "/query-user", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> queryUser(@Valid @BeanParam QueryUserList query) {
        try{
            return ok(loanAfterService.queryUserList(query));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "发起一个批次的征信查询", notes = "发起一个批次的征信查询")
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
    @RequestMapping(value = "/credit-check", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Object> creditCheck(@Valid @RequestBody CreditCheck query) {
        try{
            return ok(loanAfterService.saveCreditBatch(query));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询所有批次列表", notes = "查询所有批次列表")
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
    @RequestMapping(value = "/query-batch-list", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> queryBatchList(@Valid @BeanParam PageParameter query) {
        try{
            return ok(loanAfterService.queryBatchList(query));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "查看指定批次的征信结果", notes = "查看指定批次的征信结果")
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
    @RequestMapping(value = "/query-batch", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> queryBatch(@ApiParam(value = "批次号")
                                               @QueryParam(value = "batch_id")
                                               @RequestParam String batchId) {
        try {
            return ok(loanAfterService.queryCreditBatchDetail(batchId));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "导出失败结果文件", notes = "导出失败结果文件")
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
    @RequestMapping(value = "/export-file", method = RequestMethod.GET, produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    @MultiViewResponseBody(view = @View(name = "credit-check-fail-view", model = "model"))
    public RestResponse<Object> exportFile(@ApiParam(value = "批次号")
                                           @QueryParam(value = "batch_id")
                                           @RequestParam String batchId) {
        try{
            return ok(loanAfterService.queryCreditDetail(batchId));
        }
        catch (ProcessBizException e){
            return ok(new ArrayList());
        }
    }
}
