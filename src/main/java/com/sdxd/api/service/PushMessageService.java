package com.sdxd.api.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.util.CSVPush;
import com.sdxd.api.util.InvitePush;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.constant.Constants;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.message.dubbo.api.UmengPushDubboService;
import com.sdxd.message.dubbo.enums.DeviceType;
import com.sdxd.message.dubbo.request.push.BatchPushNotifyRequest;
import com.sdxd.user.api.UserService;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.UmengTokenInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sdxd.common.web.trace.HttpTracer.DEBUG;
import static com.sdxd.framework.enums.DeviceType.ANDROID;

/**
 * Created by matteo on 2017/8/7.
 * 定向push的工具类
 */
@Service
public class PushMessageService {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PushMessageService.class);

    @Reference(version = "1.0.0")
    private UmengPushDubboService umengPushDubboService;

    @Reference(version = "1.0.0")
    private UserService userDubboService;

    public void pushMessage(MultipartFile file, String title, String content) throws IOException,ProcessBizException{
        pushMeassage(parse(file), title, content);
    }

    private void pushMeassage(List<String> list, String title, String content) throws ProcessBizException{
        if(list == null || list.isEmpty())
            return;
        //去重
        Set<String> userIdSet = new HashSet<>();
        userIdSet.addAll(list);
        list.clear();
        list.addAll(userIdSet);

        //发送消息
        if(!list.isEmpty())
            pushMessageBatch(list, title, content);
    }

    private void pushMessageBatch(List<String> list, String title, String content) {
        List<String> android = new ArrayList<>();
        List<String> ios = new ArrayList<>();
        for (String userId : list) {
            UmengTokenInfo token = this.getToken(Long.valueOf(userId));
            if (null == token) {
                DEBUG(logger, "用户的友盟Token未找到", userId);
                continue;
            }
            if (token.getDeviceTypeEnum() == ANDROID){
                android.add(token.getToken());
                if (android.size() == 500){
                    sendMessage(android, com.sdxd.message.dubbo.enums.DeviceType.ANDROID, title, content);
                }
            }
            else{
                ios.add(token.getToken());
                if (ios.size() == 500){
                    sendMessage(ios, com.sdxd.message.dubbo.enums.DeviceType.IOS, title, content);
                }
            }
        }
        // 发送剩余消息（不足500）
        if (!android.isEmpty())
            sendMessage(android, com.sdxd.message.dubbo.enums.DeviceType.ANDROID, title, content);
        if (!ios.isEmpty())
            sendMessage(ios, com.sdxd.message.dubbo.enums.DeviceType.IOS, title, content);
    }

    private void sendMessage(List<String> deciveTokes, DeviceType deviceType, String title, String content) {
        BatchPushNotifyRequest pushNotifyRequest = new BatchPushNotifyRequest();
        pushNotifyRequest.setRequestId(BillNoUtils.GenerateBillNo());
        pushNotifyRequest.setTitle(title);
        pushNotifyRequest.setContent(content);
        pushNotifyRequest.setDeciveType(deviceType);
        pushNotifyRequest.setDeciveTokes(deciveTokes);
        umengPushDubboService.batchPushNotify(pushNotifyRequest);
        //清空
        deciveTokes.clear();
    }

    private List<String> parse(MultipartFile file) throws IOException{
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (prefix.equalsIgnoreCase("csv"))
            return new CSVPush(inputStream).parse();
        if (prefix.equalsIgnoreCase("xls") || prefix.equalsIgnoreCase("xlsx"))
            return new InvitePush(inputStream, prefix).parse();
        return null;
    }

    /**
     * 拿到token  用来得到设备型号
     *
     * @param userId
     * @return
     */
    private UmengTokenInfo getToken(long userId) {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        try {
            DubboResponse<UmengTokenInfo> response = userDubboService.queryUmengToken(request);
            if (response != null && StringUtils.equals(response.getStatus(), Constants.System.OK)) {
                return response.getData();
            }
        } catch (Exception e) {
            logger.error("调用Dubbo接口出错, userId={}", e);
        }
        return null;
    }
}
