package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.MobileTraceService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.certification.*;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.RestContext;
import com.sdxd.common.web.util.OperatorsUtil;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.risk.carrier.dto.PhoneCellInfoResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.UserAuthService;
import com.sdxd.user.api.UserQueryService;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.UserInfo;
import com.sdxd.user.api.response.UserProfileContactResponse;
import com.sdxd.user.api.response.UserProfileHomeInfo;
import com.sdxd.user.api.response.UserProfileJobInfo;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sdxd.api.vo.CreditErrors.LENDER_NOT_FOUND;
import static com.sdxd.api.vo.CreditErrors.LENDER_PHONE_NOT_FOUND;
import static com.sdxd.common.web.util.ResponseUtil.isSuccessfulResponse;
import static com.sdxd.common.web.util.Throwables.propagate;
import static com.sdxd.common.web.util.Throwables.toResponse;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.util.dubbo.DubboLoading.invoke;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;
import static com.sdxd.framework.constant.Constants.System.SYSTEM_ERROR_CODE;
import static com.sdxd.framework.constant.Constants.System.SYSTEM_ERROR_MSG;

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
@Api(value = "Certification-API", produces = "application/json")
@Controller
@RequestMapping(value = "/certification", produces = "application/json")
public class CertificationController {

    private static final Logger log = LoggerFactory.getLogger(CertificationController.class);

    @Reference(version = "1.0.0")
    private UserAuthService userAuthService;

    @Reference(version = "1.0.0")
    private UserQueryService userQueryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MobileTraceService mobileTraceService;

    @ApiOperation(value = "获取身份信息", notes = "获取身份信息")
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
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ProfileCertification> getProfile(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId,
            @ApiParam(name = "admittanceId", value = "准入单号", required = true) @RequestParam(value = "admittanceId") String admittanceId
    ) {
        try {
            ProfileCertification certification =
                    forValue(new ProfileCertification(), false).
                            parallel(() -> profileService.getUserInfo(userId), (cert, userInfo) -> {
                                cert.setCellphone(userInfo.getPhone());
                                if (userInfo.getUserProfileIdentityInfo() != null) {
                                    cert.setSex(userInfo.getUserProfileIdentityInfo().getSex());
                                    cert.setBirthday(userInfo.getUserProfileIdentityInfo().getBirthday());
                                    cert.setAddress(userInfo.getUserProfileIdentityInfo().getAddress());
                                    cert.setIssuedBy(userInfo.getUserProfileIdentityInfo().getIssuedBy());
                                    cert.setValidDateStart(userInfo.getUserProfileIdentityInfo().getValidDateStart());
                                    cert.setValidDateEnd(userInfo.getUserProfileIdentityInfo().getValidDateEnd());
                                    cert.setFrontSide(userInfo.getUserProfileIdentityInfo().getFrontSide());
                                    cert.setFrontSideCode(userInfo.getUserProfileIdentityInfo().getFrontSideCode());
                                    cert.setBackSide(userInfo.getUserProfileIdentityInfo().getBackSide());
                                    cert.setBackSideCode(userInfo.getUserProfileIdentityInfo().getBackSideCode());
                                }
                            }).
                            parallel(() -> profileService.getIdentityInfo(userId), (cert, identity) -> {
                                cert.setName(identity.getName());
                                cert.setIdNo(identity.getIdcard());
                                cert.setStatus(identity.getStatusEnum());
                            }).
                            parallel(() -> profileService.getProfile(userId), (cert, profile) -> {
                                cert.setMaritalStatus(profile.getMarriage());
                                cert.setEducation(profile.getEducation());
                                cert.setMail(profile.getEmail());
                            }).
                            parallel(() -> profileService.getFaceRecognitionInfo(userId), (cert, faceInfo) -> {
                                cert.setFaceStatus(faceInfo.getStatusEnum());
                                cert.setFacePicture(faceInfo.getFacePicture());
                                cert.setFacePictureCode(faceInfo.getFacePictureCode());
                            }).
                            parallel(() -> profileService.getCode(admittanceId), ProfileCertification::setCode).
                            start();
            return ok(certification);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取居住信息", notes = "获取居住信息")
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
    @RequestMapping(value = "/residence", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ResidenceCertification> getResidence(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileHomeInfo> response = userAuthService.getProfileHomeInfo(request);
        if (response == null) {
            return fail(SYSTEM_ERROR_CODE, SYSTEM_ERROR_MSG);
        }
        if (!isSuccessfulResponse(response)) {
            return fail(response.getError(), response.getMsg());
        }

        UserProfileHomeInfo info = response.getData();
        ResidenceCertification certification = new ResidenceCertification();
        if (info != null) {
            certification.setResidenceStatus(info.getResidentialStatus());
            certification.setResidenceAddress(info.getAddress());
            certification.setRegisterInResidence(info.getIndigeneEnum());
            certification.setTelephone(info.getPhoneNumber());
            certification.setYearsOfResidence(info.getIndigeneYears());
            certification.setAreaCode(info.getPhoneAreaCode());
            certification.setResidenceArea(info.getArea());
            certification.setResidenceCity(info.getCity());
            certification.setResidenceProvince(info.getProvince());
            certification.setStatus(info.getStatusEnum());
        }
        return ok(certification);
    }

    @ApiOperation(value = "获取工作信息", notes = "获取工作信息")
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
    @RequestMapping(value = "/job", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<JobCertification> getJob(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileJobInfo> response = userAuthService.getProfileJobInfo(request);
        if (response == null) {
            return fail(SYSTEM_ERROR_CODE, SYSTEM_ERROR_MSG);
        }
        if (!isSuccessfulResponse(response)) {
            return fail(response.getError(), response.getMsg());
        }

        UserProfileJobInfo info = response.getData();
        JobCertification certification = new JobCertification();
        if (info != null) {
            certification.setStatus(info.getStatusEnum());
            certification.setCareerType(info.getType());
            certification.setCompanyProvince(info.getCompanyProvince());
            certification.setCompanyCity(info.getCompanyCity());
            certification.setCompanyArea(info.getCompanyArea());
            certification.setCompanyAddress(info.getCompanyAddress());
            certification.setCompanyName(info.getCompanyName());
            certification.setMonthlyIncome(info.getMonthSalary());
            certification.setDurationOfLastJob(info.getLatestJobMonths());
            certification.setEmploymentStatus(info.getJobStatusEnum());
            certification.setJobLevel(info.getPositionRank());
            certification.setProofOfEmployment(info.getJobCertificateCode());
            certification.setProofOfEmploymentPath(info.getJobCertificate());
            certification.setRepaymentSource(info.getRepaymentSource());
            certification.setAreaCode(info.getCompanyPhoneAreaCode());
            certification.setTelephone(info.getCompanyPhoneNumber());
        }
        return ok(certification);
    }

    @ApiOperation(value = "获取联系人信息", notes = "获取联系人信息")
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
    @RequestMapping(value = "/relatives", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<RelativesCertification> getRelatives(
            @ApiParam(name = "user_id", value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileContactResponse> response = userAuthService.getProfileContact(request);
        if (response == null) {
            return fail(SYSTEM_ERROR_CODE, SYSTEM_ERROR_MSG);
        }
        if (!isSuccessfulResponse(response)) {
            return fail(response.getError(), response.getMsg());
        }
        UserProfileContactResponse info = response.getData();
        RelativesCertification certification = new RelativesCertification();
        if (info != null) {
            try {
                UserInfo userInfo = profileService.getUserInfo(userId);
                if (userInfo == null) {
                    return fail(LENDER_NOT_FOUND);
                }
                if (StringUtils.isBlank(userInfo.getPhone())) {
                    return fail(LENDER_PHONE_NOT_FOUND);
                }
                String contextId = RestContext.getContextId();
                Date entryTime = RestContext.getRequestEntry();
                List<Relatives> relatives =
                        invoke(() -> info.getUserContacts().parallelStream().map(contact -> {
                            Relatives relative = new Relatives();
                            relative.setName(contact.getName());
                            relative.setRelation(contact.getRelation());
                            relative.setTelephone(contact.getMobile());
                            relative.setOperator(OperatorsUtil.getOperatorsName(contact.getMobile()));
                            return relative;
                        }).map(relative -> {
                            RestContext.setContextId(contextId, entryTime);
                            PhoneCellInfoResponse callInfo = propagate(() -> mobileTraceService.getContactMobileMatchData(userInfo.getPhone(), relative.getTelephone()));
                            if (callInfo != null) {
                                relative.setIncomingCount(callInfo.getReceivedTimes());
                                relative.setOutgoingCount(callInfo.getCallTimes());
                            }
                            return relative;
                        }).collect(Collectors.toList()));
                certification.setRelatives(relatives);
            } catch (ProcessBizException e) {
                return e.toResult();
            } catch (RuntimeException e) {
                return toResponse(e);
            }
        }
        return ok(certification);
    }
}
