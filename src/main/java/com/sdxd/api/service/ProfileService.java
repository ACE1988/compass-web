package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.sdxd.api.vo.certification.CertificationCompletion;
import com.sdxd.api.vo.certification.RequiredCertification;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.util.JsonUtil;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.ControlCodeDubboService;
import com.sdxd.data.dubbo.api.request.ControlCodeRequest;
import com.sdxd.data.dubbo.api.risk.bairong.BairongDataDubboService;
import com.sdxd.data.dubbo.api.risk.bairong.dto.BairongMidRequest;
import com.sdxd.data.dubbo.api.risk.bairong.dto.BairongMidScoreBo;
import com.sdxd.data.dubbo.api.risk.policy.PolicyDataDubboService;
import com.sdxd.data.dubbo.api.risk.policy.dto.PolicyCodeRequest;
import com.sdxd.data.dubbo.api.risk.policy.dto.PolicyCodeResponse;
import com.sdxd.file.dubbo.api.FileService;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.message.dubbo.api.PhoneDubboService;
import com.sdxd.message.dubbo.api.UmengPushDubboService;
import com.sdxd.message.dubbo.enums.DeviceType;
import com.sdxd.message.dubbo.request.phoneblack.BlackPhoneRequest;
import com.sdxd.message.dubbo.request.push.BatchPushNotifyRequest;
import com.sdxd.message.dubbo.response.push.PushNotifyResponse;
import com.sdxd.user.api.UserAuthService;
import com.sdxd.user.api.UserCardService;
import com.sdxd.user.api.UserQueryService;
import com.sdxd.user.api.UserService;
import com.sdxd.user.api.request.PhoneRequest;
import com.sdxd.user.api.request.ProfilePhoneIdentityInfo;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.user.api.constant.UserIdentityStatus.PASSED;
import static com.sdxd.user.api.constant.UserIdentityStatus.SUBMITTED;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.service
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/12/7     melvin                 Created
 */
@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Reference(version = "1.0.0")
    private UserService userDubboService;

    @Reference(version = "1.0.0")
    private UserAuthService userAuthService;

    @Reference(version = "1.0.0")
    private UserQueryService userQueryService;

    @Reference(version = "1.0.0")
    private FileService fileService;

    @Reference(version = "1.0.0")
    private BairongDataDubboService bairongDataDubboService;

    @Reference(version = "1.0.0")
    private UserCardService userCardService;

    @Reference(version = "1.0.0")
    private PolicyDataDubboService policyDataDubboService;

    @Reference(version = "1.0.0")
    private ControlCodeDubboService controlCodeDubboService;

    @Reference(version = "1.0.0")
    private UmengPushDubboService umengPushDubboService;
    @Reference(version = "1.0.0")
    private PhoneDubboService phoneDubboService;

    public UserInfo getUserInfo(long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserInfo> userInfoResponse = userQueryService.queryUser(request);
        return data(userInfoResponse);
    }

    public UserRegInfoInfo getUserRegInfoInfo(Long userId) throws ProcessBizException {
        UserBaseRequest userBaseRequest = new UserBaseRequest();
        userBaseRequest.setRequestId(BillNoUtils.GenerateBillNo());
        userBaseRequest.setUserId(userId);
        DubboResponse<UserRegInfoInfo> dubboResponse
                = userQueryService.queryUserRegInfo(userBaseRequest);
        return data(dubboResponse);
    }

    public UmengTokenInfo getUmengToken(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UmengTokenInfo> response = userDubboService.queryUmengToken(request);
        return data(response);
    }

    public PushNotifyResponse pushMessageByUmeng(String title, String content, DeviceType deviceType, Set<String> deviceTokens) throws ProcessBizException {
        List<String> tokens = Lists.newArrayList(deviceTokens);
        BatchPushNotifyRequest request = new BatchPushNotifyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setTitle(title);
        request.setContent(content);
        request.setDeciveType(deviceType);
        request.setDeciveTokes(tokens);
        DubboResponse<PushNotifyResponse> response = umengPushDubboService.batchPushNotify(request);
        return data(response);
    }

    public boolean hasAccountCode(Long userId) throws ProcessBizException {
        UserInfo userInfo = this.getUserInfo(userId);
        if (null != userInfo && null != userInfo.getPhone()) {
            ControlCodeRequest controlCodeRequest
                    = new ControlCodeRequest();
            controlCodeRequest.setPhone(userInfo.getPhone());
            controlCodeRequest.setRequestId(BillNoUtils.GenerateBillNo());
            DubboResponse<Integer> dubboResponse = controlCodeDubboService.count(controlCodeRequest);
            if (null != data(dubboResponse)) {
                int totalCount = data(dubboResponse);
                return totalCount > 0;
            }
        }
        return false;
    }

    public Long getUserId(String phone) throws ProcessBizException {
        PhoneRequest request = new PhoneRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        DubboResponse<Long> userInfoResponse = userQueryService.getUserId(request);
        return data(userInfoResponse);
    }

    public UserProfileIdentityInfo getIdentityInfo(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileIdentityInfo> response = userQueryService.getProfileIdentityInfo(request);
        return data(response);
    }

    public UserProfileBaseInfo getProfile(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileBaseInfo> baseResponse = userAuthService.getProfileBaseInfo(request);
        return data(baseResponse);
    }

    public UserProfileFaceRecognitionInfo getFaceRecognitionInfo(long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<UserProfileFaceRecognitionInfo> faceResponse = userAuthService.getProfileFaceRecognitionInfo(request);
        return data(faceResponse);
    }

//    public Picture getPhotoByRealName(String idNo, String realName) throws ProcessBizException {
//        RealNamePhotoRequest request = new RealNamePhotoRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setIdNumber(idNo);
//        request.setRealName(realName);
//        DubboResponse<RealNamePhotoResponse> response = realnameDubboService.getPhotoByRealName(request);
//        RealNamePhotoResponse result = data(response);
//        if (result == null || StringUtils.isBlank(result.getPhotoCode())) return null;
//        DubboResponse<FilePathResponse> pathResponse = fileService.getFilePathByCode(result.getPhotoCode());
//        FilePathResponse path = data(pathResponse);
//        if (path == null || StringUtils.isBlank(path.getFilePath())) return null;
//        return new Picture(result.getPhotoCode(), path.getFilePath());
//    }

    public CertificationCompletion getCertificationQualification(Long userId) throws ProcessBizException {
        return forValue(new CertificationCompletion()).
                parallel(() -> getRequiredCertification(userId), CertificationCompletion::setRequired).
                parallel(() -> isOperatorsCertPass(userId), (completion, value) -> completion.getRequired().setOperators(value)).
                parallel(() -> getZhiMaScore(userId), (completion, score) -> completion.getRequired().setZhiMaScore(score)).
                parallel(() -> getBaiRongScore(userId), (completion, score) -> completion.getRequired().setBaiRongScore(score)).
                start();
    }

    private RequiredCertification getRequiredCertification(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<AuthStatus> response = userAuthService.authStatus(request);
        AuthStatus status = data(response);
        UserProfileFaceRecognitionInfo faceInfo = getFaceRecognitionInfo(userId);
        log.debug("Get certification status for {}: {}", request.getUserId(), JsonUtil.toJson(status));
        boolean realName = status != null && status.getRealName() == PASSED;
        boolean identity = status != null && status.getIdentity() == PASSED;
        boolean face = faceInfo != null && faceInfo.getStatusEnum() == PASSED;
        boolean residence = status != null && status.getHome() == PASSED;
        boolean job = status != null && status.getJob() == PASSED;
        boolean contact = status != null && status.getContact() == PASSED;
        boolean zhiMa = status != null && status.getZhima() == PASSED;
        boolean bindCard = isBindCard(userId);
        return new RequiredCertification(realName, identity, face, residence, job, contact, zhiMa, bindCard);
    }

    public String getZhiMaScore(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<ZhimaInfo> response = userAuthService.queryZhima(request);
        return data(response, resp -> resp != null ? resp.getScore() : null);
    }

    public String getBaiRongScore(Long userId) throws ProcessBizException {
        BairongMidRequest request = new BairongMidRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<BairongMidScoreBo> response = bairongDataDubboService.queryBairongScore(request);
        return data(response, value -> value == null ? null : value.getScorepettycashv1());
    }

    //专案代码
    public String getCode(String admittanceId) throws ProcessBizException {
        PolicyCodeRequest request = new PolicyCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSourceId(admittanceId);
        DubboResponse<PolicyCodeResponse> response = policyDataDubboService.queryPolicy(request);
        return data(response, resp -> resp != null ? resp.getRemake() : null);
    }

    private boolean isOperatorsCertPass(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<ProfilePhoneIdentityInfo> response = userAuthService.queryProfilePhoneIdentity(request);
        return data(response, result -> result != null && result.getStatusEnum() == SUBMITTED);
    }

    private boolean isBindCard(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<List<BindCardInfo>> response = userCardService.queryBindCards(request);
        return data(response, bindingCards -> bindingCards != null && bindingCards.size() > 0);
    }

    public DubboResponse<Boolean> unRegister(Long userId) throws ProcessBizException {
        return userDubboService.unRegister(userId);
    }

    public boolean addPhoneBlack(String phone) throws ProcessBizException {
        BlackPhoneRequest request = new BlackPhoneRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        DubboResponse<Boolean> response = phoneDubboService.addPhoneBlack(request);
        return data(response);
    }


}
