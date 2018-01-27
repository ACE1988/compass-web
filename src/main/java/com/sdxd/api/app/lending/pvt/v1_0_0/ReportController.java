package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.sdxd.api.service.ReportService;
import com.sdxd.api.vo.lending.DailyReportQuery;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.response.pojo.ReportLoanDailyBo;
import com.sdxd.data.dubbo.api.response.pojo.ReportRepaymentDailyBo;
import com.sdxd.framework.dto.PaginationSupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;

import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;

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
 * 17/3/16     melvin                 Created
 */
@Api(value = "Report-API", produces = "application/json")
@Controller
@RequestMapping(value = "/report", produces = "application/json")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "获取放款日报", notes = "获取放款日报")
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
    @RequestMapping(value = "/daily-lending", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ReportLoanDailyBo>> getDailyLendingReport(
            @BeanParam DailyReportQuery query
    ) {
        try {
            PaginationSupport<ReportLoanDailyBo> page =
                    forValue(new PaginationSupport<ReportLoanDailyBo>(null, 0)).
                            parallel(() -> reportService.getLendingDailyReport(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> reportService.countLendingDailyReport(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取还款日报", notes = "获取还款日报")
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
    @RequestMapping(value = "/daily-repayment", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ReportRepaymentDailyBo>> getDailyRepaymentReport(
            @BeanParam DailyReportQuery query
    ) {
        try {
            PaginationSupport<ReportRepaymentDailyBo> page =
                    forValue(new PaginationSupport<ReportRepaymentDailyBo>(null, 0)).
                            parallel(() -> reportService.getRepaymentDailyReport(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> reportService.countRepaymentDailyReport(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
