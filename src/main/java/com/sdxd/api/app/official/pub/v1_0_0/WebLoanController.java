package com.sdxd.api.app.official.pub.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.WebsitApplyDubboService;
import com.sdxd.admin.dubbo.api.request.CMDApplyRequest;
import com.sdxd.admin.dubbo.api.request.HMDApplyRequest;
import com.sdxd.admin.dubbo.api.request.YMDApplyRequest;
import com.sdxd.admin.dubbo.api.response.WebsitApplyResponse;
import com.sdxd.api.vo.loan.MortgageCarLoanApplication;
import com.sdxd.api.vo.loan.MortgageHouseLoanApplication;
import com.sdxd.api.vo.loan.MortgageYiLoanApplication;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.official.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/3     melvin                 Created
 */
@Api(value = "Web-Loan-API", produces = "application/json")
@Controller
@RequestMapping(value = "/web-loan", produces = "application/json")
public class WebLoanController {

    @Reference(version = "1.0.0")
    private WebsitApplyDubboService websitApplyDubboService;

    @ApiOperation(value = "一米贷", notes = "房抵贷")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/mortgage-yi-loans", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<WebsitApplyResponse> createMortgageCarLoan(
            @Valid @RequestBody MortgageYiLoanApplication application
    ) {
        YMDApplyRequest request = new YMDApplyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCompanyName(application.getCompanyName());
        request.setName(application.getName());
        request.setPhoneNum(application.getCellphone());
        request.setPersonalPropertyAddress(application.getPersonalPropertyAddress());
        request.setPersonalProperty(application.getPersonalProperty());
        request.setCompanyPosition(application.getCompanyPosition());
        request.setCompanyType(application.getCompanyType());
        request.setEducation(application.getEducation());
        request.setEntryTime(application.getEntryTime());
        request.setMaritalStatus(application.getMaritalStatus());
        request.setMonthSalary(application.getMonthlySalary());
        request.setRefereeName(application.getRefereeName());
        request.setResidenceAddress(application.getResidenceAddress());
        request.setTakeWorkTime(application.getTakeWorkTime());
        DubboResponse<WebsitApplyResponse> response = websitApplyDubboService.ymdApply(request);
        return rest(response);
    }

    @ApiOperation(value = "房抵贷", notes = "房抵贷")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/mortgage-house-loans", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<WebsitApplyResponse> createMortgageHouseLoan(
            @Valid @RequestBody MortgageHouseLoanApplication application
    ) {
        HMDApplyRequest request = new HMDApplyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCompanyAddress(application.getCompanyAddress());
        request.setCompanyName(application.getCompanyName());
        request.setHomeAddress(application.getHomeAddress());
        request.setIdcard(application.getIdNo());
        request.setName(application.getName());
        request.setPhoneNum(application.getPhone());
        request.setApplyAmount(application.getApplyAmount());
        request.setPersonalPropertyAddress(application.getMortgageHouseAddress());
        request.setPurpose(application.getPurpose());
        DubboResponse<WebsitApplyResponse> response = websitApplyDubboService.hmdApply(request);
        return rest(response);
    }

    @ApiOperation(value = "车抵贷", notes = "车抵贷")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/mortgage-car-loans", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<WebsitApplyResponse> createMortgageCarLoan(
            @Valid @RequestBody MortgageCarLoanApplication application
    ) {
        CMDApplyRequest request = new CMDApplyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCompanyAddress(application.getCompanyAddress());
        request.setCompanyName(application.getCompanyName());
        request.setHomeAddress(application.getHomeAddress());
        request.setIdcard(application.getIdNo());
        request.setName(application.getName());
        request.setPhoneNum(application.getPhone());
        request.setPlateNumber(application.getPlateNumber());
        request.setVehicleBrand(application.getVehicleBrand());
        request.setVehicleModel(application.getVehicleModel());
        DubboResponse<WebsitApplyResponse> response = websitApplyDubboService.cmApply(request);
        return rest(response);
    }
}
