package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.lending.AbatementBillQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.RepaymentUrgeQueryDubboService;
import com.sdxd.repayment.dubbo.request.RepaymentDiscountPageRequest;
import com.sdxd.repayment.dubbo.request.RepaymentUrgeDiscountApproveRequest;
import com.sdxd.repayment.dubbo.request.RepaymentUrgeDiscountRequest;
import com.sdxd.repayment.dubbo.response.RepaymentDiscountInfo;
import com.sdxd.repayment.dubbo.response.RepaymentUrgeDiscountApproveInfo;
import com.sdxd.repayment.dubbo.response.RepaymentUrgeDiscountInfo;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.lending.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/17      matteo                 Created
 */
@Api(value = "Abatement-Bill-API", produces = "application/json")
@Controller
@RequestMapping(value = "/abatement-Bill", produces = "application/json")
public class AbatementBillController {

    @Reference(version = "1.0.0")
    private RepaymentUrgeQueryDubboService repaymentUrgeQueryDubboService;


    @ApiOperation(value = "减免单列表查询", notes = "减免单列表查询")
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
    public RestResponse<PaginationSupport<RepaymentDiscountInfo>> query(
            @Valid @BeanParam AbatementBillQuery query
    ) {
        RepaymentDiscountPageRequest request = new RepaymentDiscountPageRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStatuses(query.getStatuses());
        request.setCurrentPage(query.getPageNo() == null ? 1 : query.getPageNo());
        request.setPageSize(query.getPageSize() == null ? 15 : query.getPageSize());
        request.setApplyId(query.getApplyId());
        request.setStartCreateTime(query.getStartCreateTime());
        request.setEndCreateTime(query.getEndCreateTime());
        request.setUserName(query.getUserName());
        request.setReporterName(query.getReporterName());
        DubboResponse<PaginationSupport<RepaymentDiscountInfo>> response = repaymentUrgeQueryDubboService.queryPageDiscounts(request);
        List<RepaymentDiscountInfo> list = response.getData().getList();
        if (list.size() != 0) {
            List<RepaymentDiscountInfo> newList = list.stream().sorted(
                    Comparator.comparing(RepaymentDiscountInfo::getCreateTime).reversed()).collect(Collectors.toList());
            response.getData().setList(newList);
        }
        return rest(response);
    }

    @ApiOperation(value = "减免单详细查询", notes = "减免单详细查询")
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
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<RepaymentUrgeDiscountApproveInfo> details(
            @RequestParam(value = "id") String id
    ) {
        RepaymentUrgeDiscountApproveRequest request = new RepaymentUrgeDiscountApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatch(false);
        List<String> list = new ArrayList<String>();
        list.add(id);
        request.setDiscoutId(list);
        DubboResponse<RepaymentUrgeDiscountApproveInfo> response = repaymentUrgeQueryDubboService.queryDiscountApproveInfo(request);
        return rest(response);
    }
}
