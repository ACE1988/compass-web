package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.MobileTraceService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.service.ShippingAddressService;
import com.sdxd.api.vo.SignInSummary;
import com.sdxd.api.vo.certification.Addresses;
import com.sdxd.api.vo.credit.InternetTrace;
import com.sdxd.api.vo.credit.MobileTrace;
import com.sdxd.api.vo.credit.Scores;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.risk.carrier.dto.Call;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.UserQueryService;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.UserInfo;
import com.sdxd.user.api.response.UserLoginInfo;
import com.sdxd.user.api.response.UserProfileBaseInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rx.Observable;

import javax.ws.rs.BeanParam;
import java.util.List;

import static com.sdxd.api.vo.CreditErrors.LENDER_NOT_FOUND;
import static com.sdxd.api.vo.CreditErrors.LENDER_PHONE_NOT_FOUND;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.user.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/8     melvin                 Created
 */
@Api(value = "Privacy-API", produces = "application/json")
@Controller
@RequestMapping(value = "/privacy", produces = "application/json")
public class UserPrivacyController {

    private static final Logger log = LoggerFactory.getLogger(UserPrivacyController.class);

    @Reference(version = "1.0.0")
    private UserQueryService userQueryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MobileTraceService mobileTraceService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @ApiOperation(value = "获取用户手机信息", notes = "获取用户手机信息")
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
    @RequestMapping(value = "/mobile-trace-info", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<MobileTrace> getMobileTrace(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        try {
            MobileTrace traceInfo =
                    forValue(new MobileTrace(), false).
                            parallel(() -> mobileTraceService.getCountOfSameDevice(userId), MobileTrace::setCountOfSameDevice).
                            parallel(() -> mobileTraceService.getUserAddressCount(userId), MobileTrace::setAddressCount).
                            parallel(() -> mobileTraceService.getUserMobileInfo(userId), MobileTrace::setMobileInfo).
                            dep(MobileTrace::getPhone, phone -> mobileTraceService.getMobileOnlineTime(phone), MobileTrace::setMobileOnlineTime).
                            start();
            return ok(traceInfo);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取用户通话记录", notes = "获取用户通话记录")
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
    @RequestMapping(value = "/call-records", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<Call>> getCallRecords(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId,
            @BeanParam PageParameter page
            ) {
        try {
            UserInfo userInfo = profileService.getUserInfo(userId);
            if (userInfo == null) {
                return fail(LENDER_NOT_FOUND);
            }
            if (StringUtils.isBlank(userInfo.getPhone())) {
                return fail(LENDER_PHONE_NOT_FOUND);
            }
            PaginationSupport<Call> response = mobileTraceService.getUserMobileData(userInfo.getPhone(), page);
            return ok(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取用户联系人通话记录", notes = "获取用户联系人通话记录")
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
    @RequestMapping(value = "/call-records/relatives", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<Call>> getCallRecords(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId,
            @ApiParam(name = "relatives_cellphone", value = "联系人手机", required = true) @RequestParam(value = "relatives_cellphone") String relativesCellphone,
            @BeanParam PageParameter page
    ) {
        try {
            UserInfo userInfo = profileService.getUserInfo(userId);
            if (userInfo == null) {
                return fail(LENDER_NOT_FOUND);
            }
            if (StringUtils.isBlank(userInfo.getPhone())) {
                return fail(LENDER_PHONE_NOT_FOUND);
            }
            PaginationSupport<Call> response = mobileTraceService.getContactMobileData(userInfo.getPhone(), relativesCellphone, page);
            return ok(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取用户互联网信息", notes = "获取用户互联网信息")
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
    @RequestMapping(value = "/internet-trace-info", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<InternetTrace> getInternetInfo(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<List<UserLoginInfo>> response = userQueryService.queryUserLoginInfo(request);
        try {
            UserProfileBaseInfo profile = profileService.getProfile(userId);
            return rest(response, loginDistribution -> {
                SignInSummary signInSummary =
                        Observable.from(loginDistribution).reduce(new SignInSummary(), (summary, userLoginInfo) -> {
                            log.debug("Count sign in location: {}", userLoginInfo.getCity());
                            summary.count(userLoginInfo.getCity(), userLoginInfo.getLoginTime());
                            return summary;
                        }).toBlocking().first();
                return new InternetTrace(signInSummary, profile == null ? null : profile.getEmail());
            });
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询分数", notes = "查询分数")
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
    @RequestMapping(value = "/scores", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Scores> getScores(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId) {
        try {
            Scores scores =
                    forValue(new Scores(), false).
                            parallel(() -> profileService.getZhiMaScore(userId), Scores::setZhiMaScore).
                            parallel(() -> profileService.getBaiRongScore(userId), Scores::setBaiRongScore).
                            start();
            return ok(scores);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
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
    @RequestMapping(value = "/shipping-addresses", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Addresses>> getShippingAddresses(
            @ApiParam(name = "user_id", value = "用户ID", required = true)
            @RequestParam(value = "user_id") Long userId) {
        try {
            List<Addresses> result = shippingAddressService.getAll(userId);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
