package com.sdxd.api.app.admin.pub.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.activities.api.InviteUserActivityService;
import com.sdxd.activities.api.dto.inviteuser.RewardTypeEnum;
import com.sdxd.activities.api.dto.inviteuser.RewardUserRequest;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.PK;
import com.sdxd.api.vo.support.*;
import com.sdxd.approve.dubbo.api.ApproveTaskDubboService;
import com.sdxd.approve.dubbo.api.request.ApproveTaskRequest;
import com.sdxd.auth.dubbo.api.OperatorPushResultDubboService;
import com.sdxd.auth.dubbo.api.RouterChannelDubboService;
import com.sdxd.auth.dubbo.api.request.OperatorPullMqRequest;
import com.sdxd.auth.dubbo.api.request.OperatorPullRequest;
import com.sdxd.auth.dubbo.api.request.OperatorPushRequest;
import com.sdxd.auth.dubbo.api.request.RouterChannelRequest;
import com.sdxd.auth.dubbo.api.response.OperatorPushResponse;
import com.sdxd.auth.etl.dubbo.api.AnalyMidDubboService;
import com.sdxd.auth.etl.dubbo.api.ZhimaOperatorDubboService;
import com.sdxd.auth.etl.dubbo.api.request.HuluRequest;
import com.sdxd.auth.etl.dubbo.api.request.ZhimaMidRequest;
import com.sdxd.auth.etl.dubbo.api.response.HuluResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.SignatureIgnore;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.decision.api.DecisionAdminService;
import com.sdxd.decision.api.DecisionJobService;
import com.sdxd.decision.api.DecisionService;
import com.sdxd.decision.api.constants.EventType;
import com.sdxd.decision.api.request.PolicyRequest;
import com.sdxd.decision.api.request.PolicyRuleRequest;
import com.sdxd.decision.api.request.RuleRequest;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.job.api.JobManagerDubboService;
import com.sdxd.job.api.constants.InterfaceTypeEnum;
import com.sdxd.job.api.dto.JobHandlerDto;
import com.sdxd.job.api.dubbo.JobHandlerDubboService;
import com.sdxd.job.api.request.PageRequest;
import com.sdxd.job.api.response.JobResponse;
import com.sdxd.job.api.response.JobTaskResponse;
import com.sdxd.job.api.response.PageBean;
import com.sdxd.loan.api.LoanDubboService;
import com.sdxd.loan.request.ChangeBankCardRequest;
import com.sdxd.monitor.mq.utils.SendCommonMQService;
import com.sdxd.monitor.mq.utils.request.CommonMQRequest;
import com.sdxd.pay.api.P2PUserDubboService;
import com.sdxd.pay.api.PaymentDubboService;
import com.sdxd.pay.enums.PayChannel;
import com.sdxd.pay.request.DkQueryRequest;
import com.sdxd.signature.api.SignatureDubboService;
import com.sdxd.signature.api.request.IssueCertRequest;
import com.sdxd.urge.dubbo.api.PullOverDataDubboService;
import com.sdxd.urge.dubbo.api.request.PullOverDataRequest;
import com.sdxd.urge.dubbo.api.request.PullPaymentDateRequest;
import com.sdxd.urge.dubbo.api.request.PullPhoneRequest;
import com.sdxd.user.api.UserUtilService;
import com.sdxd.user.api.response.UserInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.sdxd.api.vo.CreditErrors.LENDER_USER_NOT_IDENTITY;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.loan.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/2/8     melvin                 Created
 */
@Api(value = "Support-API", produces = "application/json")
@Controller
@RequestMapping(value = "/", produces = "application/json")
@SignatureIgnore
public class SupportController {

    @Reference(version = "1.0.0")
    private P2PUserDubboService p2PUserDubboService;

    @Reference(version = "1.0.0")
    private PaymentDubboService paymentDubboService;

    @Reference(version = "1.0.0")
    private DecisionService decisionService;

    @Reference(version = "1.0.0")
    private DecisionJobService decisionJobService;

    @Reference(version = "1.0.0")
    private OperatorPushResultDubboService operatorPushResultDubboService;

    @Reference(version = "1.0.0")
    private PullOverDataDubboService pullOverDataDubboService;

    @Reference(version = "1.0.0")
    private DecisionAdminService decisionAdminService;

    @Reference(version = "1.0.0")
    private JobHandlerDubboService jobHandlerDubboService;

    @Reference(version = "1.0.0")
    private JobManagerDubboService jobManagerDubboService;

    @Reference(version = "1.0.0")
    private SendCommonMQService sendCommonMQService;

    @Reference(version = "1.0.0")
    private AnalyMidDubboService analyMidDubboService;

    @Reference(version = "1.0.0")
    private RouterChannelDubboService routerChannelDubboService;

    @Reference(version = "1.0.0")
    private LoanDubboService loanDubboService;

    @Reference(version = "1.0.0")
    private ZhimaOperatorDubboService zhimaOperatorDubboService;

    @Reference(version = "1.0.0")
    private InviteUserActivityService inviteUserActivityService;

    @Reference(version = "1.0.0")
    private ApproveTaskDubboService approveTaskDubboService;

    @Reference(version = "1.0.0")
    private UserUtilService userUtilService;

    @Reference(version = "1.0.0")
    private SignatureDubboService signatureDubboService;

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "解绑指定卡号银行卡", notes = "解绑指定卡号银行卡")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/bank-cards/{no}/binding-release", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse releaseBinding(
            @ApiParam(value = "银行卡号", required = true) @PathVariable("no") String bankCardNo,
            @ApiParam(value = "用户ID", required = true) @RequestParam("user_id") Long userId) {
        DubboResponse<String> response = p2PUserDubboService.unsign(userId, bankCardNo);
        return rest(response);
    }

    @ApiOperation(value = "解绑指定卡号银行卡", notes = "解绑指定卡号银行卡")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/bank-cards/all-binding-release", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse releaseAllBinding(
            @ApiParam(value = "用户ID", required = true) @RequestParam("user_id") Long userId) {
        DubboResponse<String> response = p2PUserDubboService.unsignAll(userId);
        return rest(response);
    }

    @ApiOperation(value = "手工把p2p代扣中的数据置为代扣失败", notes = "手工把p2p代扣中的数据置为代扣失败")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/repayment/{id}/deduction-failure", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updateDeduction(
            @ApiParam(value = "还款单ID", required = true) @PathVariable("id") String id,
            @ApiParam(
                    value = "通道Code, SHENGPAY: 盛付通代付代扣通道, BAOFOO: 宝付代付代扣, P2P_SHENGPAY: 盛付通托管账号",
                    allowableValues = "P2P_SHENGPAY,SHENGPAY,BAOFOO",
                    required = true) @RequestParam("channel_code") PayChannel channelCode) {
        DkQueryRequest request = new DkQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBizCode(id);
        request.setChannelCode(channelCode);
        DubboResponse<String> response = paymentDubboService.manualP2PDkFail(request);
        return rest(response);
    }

    @ApiOperation(value = "触发MQ重发", notes = "触发MQ重发")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/message-queue/recover", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse restQueue() {
        DubboResponse<Boolean> response = decisionService.approveMqResend();
        return rest(response);
    }

    @ApiOperation(value = "检测运营商推送结果", notes = "检测运营商推送结果")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/operators/push-result-check", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<OperatorPushResponse> checkPushResult(
            @ApiParam(value = "手机号码", required = true) @RequestParam("phone") String phone) {
        OperatorPushRequest request = new OperatorPushRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        DubboResponse<OperatorPushResponse> response = operatorPushResultDubboService.checkpPushResult(request);
        return rest(response);
    }

    @ApiOperation(value = "发送MQ", notes = "发送MQ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/operators/message-queue-send", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> sendMq(
            @ApiParam(value = "手机号码", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "姓名", required = true) @RequestParam("name") String name) {
        return operatorMq(phone, name, null, request -> operatorPushResultDubboService.sendMq(request));
    }

    @ApiOperation(value = "发送失败消息到MQ", notes = "发送失败消息到MQ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/operators/failure-message-queue-send", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> sendFailureMq(
            @ApiParam(value = "手机号码", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "姓名") @RequestParam("name") String name,
            @ApiParam(value = "Token") @RequestParam("token") String token) {
        return operatorMq(phone, name, token, request -> operatorPushResultDubboService.sendFailMq(request));
    }

    private RestResponse<Boolean> operatorMq(
            String phone, String name, String token,
            Function<OperatorPullMqRequest, DubboResponse<Boolean>> function) {
        OperatorPullMqRequest request = new OperatorPullMqRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        request.setName(name);
        request.setToken(token);
        DubboResponse<Boolean> response = function.apply(request);
        return rest(response);
    }

    @ApiOperation(value = "拉取Hulu结果", notes = "拉取Hulu结果")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/operators/hulu-result-pull", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<OperatorPushResponse> pullHulu(
            @ApiParam(value = "手机号码", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "Token", required = true) @RequestParam("token") String token,
            @ApiParam(value = "Name", required = true) @RequestParam("name") String name,
            @ApiParam(value = "ID Card", required = true) @RequestParam("id_card") String idCard) {
        OperatorPullRequest request = new OperatorPullRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        request.setToken(token);
        request.setName(name);
        request.setIdCard(idCard);
        DubboResponse<OperatorPushResponse> response = operatorPushResultDubboService.pullHuluRt(request);
        return rest(response);
    }

    @ApiOperation(value = "重跑准入", notes = "重跑准入")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/admittance/execution", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse reInvokeAdmittance() {
        decisionJobService.rerunAdmittance();
        return ok();
    }

    @ApiOperation(value = "重跑支用", notes = "重跑支用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/payment/execution", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse reInvokeMoney() {
        decisionJobService.rerunMoney();
        return ok();
    }

    @ApiOperation(value = "催收回款数据推送", notes = "催收回款数据推送")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/urge/push-repay", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pull(@RequestBody PK<String> pk) {
        PullPaymentDateRequest request = new PullPaymentDateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPlanIds(Optional.ofNullable(pk).map(PK::getIds).orElse(null));
        DubboResponse<Boolean> response = pullOverDataDubboService.pull(request);
        return rest(response);
    }

    @ApiOperation(value = "催收逾期案件数据推送", notes = "催收逾期案件数据推送")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/urge/push-overdue", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pullAll(@RequestBody PK<String> pk) {
        PullOverDataRequest request = new PullOverDataRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPlanIds(Optional.ofNullable(pk).map(PK::getIds).orElse(null));
        DubboResponse<Boolean> response = pullOverDataDubboService.pullData(request);
        return rest(response);
    }

    @ApiOperation(value = "推送用户通讯录", notes = "推送用户通讯录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/urge/push-contacts", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pullByUser(@RequestBody PK<Long> pk) {
        PullPhoneRequest request = new PullPhoneRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserIds(Optional.ofNullable(pk).map(PK::getIds).orElse(null));
        DubboResponse<List<Long>> response = pullOverDataDubboService.pullPhones(request);
        return rest(response);
    }

    @ApiOperation(value = "添加策略, 同时关联事件", notes = "添加策略, 同时关联事件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/decision/policy", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse addPolicy(
            @ApiParam(value = "Name", required = true) @RequestParam("name") String name,
            @ApiParam(value = "Code", required = true) @RequestParam("code") String code,
            @ApiParam(value = "Remark", required = true) @RequestParam("remark") String remark,
            @ApiParam(value = "Version", required = true) @RequestParam("version") String version,
            @ApiParam(value = "Event Type", allowableValues = "PRE_APPLICATION,APPLICATION,MONEY", required = true)
            @RequestParam("event_type") EventType eventType
    ) {
        PolicyRequest request = new PolicyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setName(name);
        request.setCode(code);
        request.setRemark(remark);
        request.setVersion(version);
        request.setEventType(eventType);
        DubboResponse<Boolean> response = decisionAdminService.addPolicy(request);
        return rest(response);
    }

    @ApiOperation(value = "添加规则, 同时关联策略", notes = "添加规则, 同时关联策略")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/decision/rule", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse addRule(
            @RequestBody DecisionRule rule
    ) {
        PolicyRuleRequest request = new PolicyRuleRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setName(rule.getName());
        request.setCode(rule.getCode());
        request.setRemark(rule.getRemark());
        request.setInputParams(rule.getInputParams());
        request.setScriptContext(rule.getScriptContext());
        request.setVersion(rule.getVersion());
        request.setPolicyCode(rule.getPolicyCode());
        request.setPolicyVersion(rule.getPolicyVersion());
        DubboResponse<Boolean> response = decisionAdminService.addRule(request);
        return rest(response);
    }

    @ApiOperation(value = "修改规则", notes = "修改规则")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/decision/rule", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse modifyRule(
            @RequestBody DecisionRuleUpdate update
    ) {
        RuleRequest request = new RuleRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCode(update.getCode());
        request.setInputParams(update.getInputParams());
        request.setScriptContext(update.getScriptContext());
        DubboResponse<Boolean> response = decisionAdminService.modifyRule(request);
        return rest(response);
    }

    @ApiOperation(value = "启动定时任务", notes = "定时任务启动")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/start-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse startJob() {
        DubboResponse<Boolean> response = jobHandlerDubboService.startScheduler();
        return rest(response);
    }

    @ApiOperation(value = "暂停一个定时任务", notes = "暂停一个定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/pause-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pasuseJob(
            @ApiParam(value = "任务名", required = true) @RequestParam("jobName") String jobName,
            @ApiParam(value = "任务组名", required = true) @RequestParam("groupName") String groupName
    ) {
        DubboResponse<Boolean> response = jobHandlerDubboService.pauseJob(jobName, groupName);
        return rest(response);
    }

    @ApiOperation(value = "暂停所有定时任务", notes = "暂停所有定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/pause-all", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pasuseAll() {
        DubboResponse<Boolean> response = jobManagerDubboService.pauseAll();
        return rest(response);
    }

    @ApiOperation(value = "恢复所有定时任务", notes = "恢复所有定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/resume-all", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse resumeAll() {
        DubboResponse<Boolean> response = jobManagerDubboService.resumeAll();
        return rest(response);
    }

    @ApiOperation(value = "恢复一个定时任务", notes = "恢复一个定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/resume-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse resumeJob(
            @ApiParam(value = "任务名", required = true) @RequestParam("jobName") String jobName,
            @ApiParam(value = "任务组名", required = true) @RequestParam("groupName") String groupName
    ) {
        DubboResponse<Boolean> response = jobHandlerDubboService.resumeJob(jobName, groupName);
        return rest(response);
    }

    @ApiOperation(value = "删除一个定时任务", notes = "删除一个定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/delete-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse deleteJob(
            @ApiParam(value = "任务名", required = true) @RequestParam("jobName") String jobName,
            @ApiParam(value = "任务组名", required = true) @RequestParam("groupName") String groupName
    ) {
        DubboResponse<Boolean> response = jobHandlerDubboService.deleteJob(jobName, groupName);
        return rest(response);
    }

//    @ApiOperation(value = "启动一组定时任务", notes = "启动一组定时任务")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")
//    })
//    @RequestMapping(value = "/job/start-group", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResponse startGroup(
//            @ApiParam(value = "组件名", required = true) @RequestParam("groupName") String groupName
//    ) {
//
//        JobGroupRequest request = new JobGroupRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setGroupName(groupName);
//        DubboResponse<Boolean> response = jobManagerDubboService.(request);
//        return rest(response);
//    }
//
//    @ApiOperation(value = "暂停一组定时任务", notes = "暂停一组定时任务")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")
//    })
//    @RequestMapping(value = "/job/pause-group", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResponse pauseGroup(
//            @ApiParam(value = "组件名", required = true) @RequestParam("groupName") String groupName
//    ) {
//
//        JobGroupRequest request = new JobGroupRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setGroupName(groupName);
//        DubboResponse<Boolean> response = jobManagerDubboService.pauseGroup(request);
//        return rest(response);
//    }
//
//    @ApiOperation(value = "恢复一组定时任务", notes = "恢复一组定时任务")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")
//    })
//    @RequestMapping(value = "/job/resume-group", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResponse resumeGroup(
//            @ApiParam(value = "组件名", required = true) @RequestParam("groupName") String groupName
//    ) {
//
//        JobGroupRequest request = new JobGroupRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setGroupName(groupName);
//        DubboResponse<Boolean> response = jobManagerDubboService.resumeGroup(request);
//        return rest(response);
//    }

    @ApiOperation(value = "立即执行任务", notes = "立即执行任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/start-now", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse startNow(
            @ApiParam(value = "任务名", required = true) @RequestParam("jobName") String jobName,
            @ApiParam(value = "任务组名", required = true) @RequestParam("groupName") String groupName
    ) {
        DubboResponse<Boolean> response = jobHandlerDubboService.startNow(jobName, groupName);
        return rest(response);
    }

    @ApiOperation(value = "增加定时任务", notes = "增加定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/add-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse addJob(@RequestBody JobAdd jobAdd) {
        JobHandlerDto request = new JobHandlerDto();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setJobName(jobAdd.getJobName());
        request.setJobGroup(jobAdd.getGroupName());
        request.setDescription(jobAdd.getDescription());
        request.setCronExpression(jobAdd.getExpression());
        request.setInterfaceName(jobAdd.getInterfaceName());
        request.setInterfaceType(InterfaceTypeEnum.DUBBO);
        request.setMethodName(jobAdd.getMethodName());
        request.setVersion(jobAdd.getVersion());
        request.setMethodParamType(jobAdd.getDubboParamClass());
        request.setMethodParams(jobAdd.getParam());
        DubboResponse<Boolean> response = jobHandlerDubboService.addjob(request);
        return rest(response);
    }

    @ApiOperation(value = "修改任务", notes = "修改任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/modify-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse modifyJob(
            @RequestBody JobModify jobModify
    ) {
        DubboResponse<Boolean> response = jobHandlerDubboService.modifyJob(
                jobModify.getJobName(),
                jobModify.getGroupName(),
                jobModify.getExpression()
        );
        return rest(response);
    }

    @ApiOperation(value = "查询任务", notes = "查询任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/get-job", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse getJob(
            @ApiParam(value = "任务名", required = false) @RequestParam(value = "jobName", required = false) String jobName,
            @ApiParam(value = "当前页", required = true) @RequestParam("current") int current
    ) {
        PageRequest request = new PageRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        if (jobName != null && !"".equals(jobName)) {
            request.setJobName(jobName);
        }
        request.setCurrent(current);
        DubboResponse<PageBean<JobResponse>> response = jobManagerDubboService.getJob(request);
        return rest(response);
    }

//    @ApiOperation(value = "查询一组", notes = "查询任务")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")
//    })
//    @RequestMapping(value = "/job/get-group", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResponse getJobsByGroup(
//            @ApiParam(value = "组件名", required = false) @RequestParam(value = "groupName",required = false) String jobGroup,
//            @ApiParam(value = "当前页", required = true) @RequestParam("current") int current
//    ) {
//        PageRequest request = new PageRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        if(jobGroup != null && !"".equals(jobGroup)) {
//            request.setGroupName(jobGroup);
//        }
//        request.setCurrent(current);
//        DubboResponse<PageBean<JobResponse>> response = jobManagerDubboService.getGroup(request);
//        return rest(response);
//    }

    @ApiOperation(value = "查询任务详情", notes = "查询任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/job/get-task", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse getTask(
            @ApiParam(value = "任务名", required = false) @RequestParam(value = "jobName", required = false) String jobName,
            @ApiParam(value = "当前页", required = true) @RequestParam("current") int current
    ) {
        PageRequest request = new PageRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        if (jobName != null && !"".equals(jobName)) {
            request.setJobName(jobName);
        }
        request.setCurrent(current);
        DubboResponse<PageBean<JobTaskResponse>> response = jobManagerDubboService.getTask(request);
        return rest(response);
    }

    @ApiOperation(value = "通用消息发送接口", notes = "通用消息发送接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/common/mq-delivery", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse modifyRule(
            @RequestBody CommonMQ commonMQ
    ) {
        CommonMQRequest request = new CommonMQRequest();
        request.setTag(commonMQ.getTag());
        request.setTopic(commonMQ.getTopic());
        request.setProducer(commonMQ.getProducer());
        request.setMessages(commonMQ.getMessages());
        DubboResponse<String> response = sendCommonMQService.send(request);
        return rest(response);
    }

    @ApiOperation(value = "葫芦分析接口", notes = "葫芦分析接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/hulu/analysis", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse modifyRule(
            @ApiParam(value = "Phone", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "Token", required = true) @RequestParam("token") String token,
            @ApiParam(value = "Type", allowableValues = "rawData,report", required = true) @RequestParam("type") String type
    ) {
        HuluRequest request = new HuluRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPhone(phone);
        request.setToken(token);
        request.setType(type);
        DubboResponse<HuluResponse> response = analyMidDubboService.analyData(request);
        return rest(response);
    }

    @ApiOperation(value = "认证路由权重重置接口", notes = "认证路由权重重置接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/operators/router-weight-reset", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse routerWeightReset(
            @ApiParam(value = "ProvinceCode", required = true) @RequestParam("province_code") String provinceCode,
            @ApiParam(value = "Province", required = true) @RequestParam("province") String province,
            @ApiParam(value = "ChannelCode", required = true) @RequestParam("channel_code") String channelCode,
            @ApiParam(value = "Carrier", required = true) @RequestParam("carrier") String carrier,
            @ApiParam(value = "Weight", required = true) @RequestParam("weight") Float weight
    ) {
        RouterChannelRequest request = new RouterChannelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setProvinceCode(provinceCode);
        request.setProvince(province);
        request.setChannelCode(channelCode);
        request.setCarrier(carrier);
        request.setWeight(weight);
        DubboResponse<Boolean> response = routerChannelDubboService.resetWeight(request);
        return rest(response);
    }

    @ApiOperation(value = "更改银行卡", notes = "更改银行卡")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/loan/bank-card-replace", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse changeBankCard(
            @ApiParam(value = "Card No", required = true) @RequestParam("card_no") String cardNo,
            @ApiParam(value = "Loan ID", required = true) @RequestParam("loan_id") String loanId,
            @ApiParam(value = "Operator", required = true) @RequestParam("operator") String operator
    ) {
        ChangeBankCardRequest request = new ChangeBankCardRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCardNo(cardNo);
        request.setLoanId(loanId);
        request.setOperator(operator);
        DubboResponse<String> response = loanDubboService.changeBankCard(request);
        return rest(response);
    }

    @ApiOperation(value = "芝麻数据修复", notes = "芝麻数据修复")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/zhi-ma/data-recover", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse zhiMaDataRecover(
            @ApiParam(value = "ID Card No", required = true) @RequestParam("card_no") String cardNo,
            @ApiParam(value = "Name", required = true) @RequestParam("name") String name,
            @ApiParam(value = "Open ID", required = true) @RequestParam("open_id") String openId
    ) {
        ZhimaMidRequest request = new ZhimaMidRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCardNo(cardNo);
        request.setName(name);
        request.setOpenId(openId);
        DubboResponse<Boolean> response = zhimaOperatorDubboService.zhimaMiddealDeleteBefore(request);
        return rest(response);
    }

    @ApiOperation(value = "催收系统导入通话记录", notes = "催收系统导入通话记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/urge/pull-calls", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse pullCalls(
            @RequestBody PK<Long> pk
    ) {
        PullPhoneRequest request = new PullPhoneRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserIds(pk.getIds());
        DubboResponse<List<Long>> response = pullOverDataDubboService.pullCalls(request);
        return rest(response);
    }

    @ApiOperation(value = "邀请有礼白名单加载缓存", notes = "邀请有礼白名单加载缓存")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/activity-invite/cache-white-user", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse cacheActivityWhite() {
        DubboResponse<Boolean> res = inviteUserActivityService.loadWhiteList();
        return rest(res);
    }

    @ApiOperation(value = "邀请有礼奖励用户", notes = "邀请有礼奖励用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/activity-invite/reward-user", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse rewardActivityInvite(
            @ApiParam(value = "invitee user id", required = true) @RequestParam("inviteeUserId") Long inviteeUserId,
            @ApiParam(value = "INVITER_ADMITE:申请额度奖励邀请人, INVITEE_ADMITE:申请额度奖励被邀请人, INVITER_LOAN:放款奖励被邀请人, INVITER_LOAN_EXTRA:放款奖励被邀请人5元(邀请有礼第二弹)",
                    allowableValues = "INVITER_ADMITE,INVITEE_ADMITE,INVITER_LOAN,INVITER_LOAN_EXTRA", required = true)
            @RequestParam("rewardTypeEnum") RewardTypeEnum rewardTypeEnum,
            @RequestParam("applyId") String applyId
    ) {
        RewardUserRequest req = new RewardUserRequest();
        req.setInviteeUserId(inviteeUserId);
        req.setRewardTypeEnum(rewardTypeEnum);
        req.setApplyId(applyId);
        req.setRequestId(BillNoUtils.GenerateBillNo());

        DubboResponse<Boolean> res = inviteUserActivityService.rewardUser(req);
        return rest(res);
    }

    @ApiOperation(value = "执行Approve定时任务", notes = "执行Approve定时任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/approve-task/run", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse runApproveTask(
            @ApiParam(value = "执行任务名", allowableValues = "CreditOverdueTask,PushCreditOverdueMessageTask", required = true)
            @RequestParam("class_name") String className,
            @ApiParam(value = "执行日期(格式:2017-01-01)", required = true)
            @RequestParam("date") String date
    ) {
        ApproveTaskRequest request = new ApproveTaskRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setClassName(className);
        request.setDate(date);
        return rest(approveTaskDubboService.run(request));
    }

    @ApiOperation(value = "用户手机号加密", notes = "用户手机号加密")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/user-phone-encryption", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse encryptUserPhone() {
        userUtilService.phoneCipher();
        return ok();
    }

    @ApiOperation(value = "用户颁发证书", notes = "用户颁发证书")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/user-issue-cert", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse issueCert(@RequestParam("user_id") Long userId) {
        try {
            UserInfo user = profileService.getUserInfo(userId);
            if (user == null || user.getUserProfileIdentityInfo() == null) {
                return fail(LENDER_USER_NOT_IDENTITY);
            }
            IssueCertRequest request = new IssueCertRequest();
            request.setRequestId(BillNoUtils.GenerateBillNo());
            request.setUserId(String.valueOf(userId));
            request.setUserName(user.getUserProfileIdentityInfo().getName());
            request.setIdCardNo(user.getUserProfileIdentityInfo().getIdcard());
            request.setMobile(user.getPhone());
            DubboResponse<Boolean> response = signatureDubboService.issueCert(request);
            return rest(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

}
