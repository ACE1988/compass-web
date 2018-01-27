package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.common.web.interceptor.PostHandler;
import com.sdxd.file.dubbo.api.FileService;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sdxd.common.web.trace.HttpTracer.DEBUG;

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
 * 2017/7/21     melvin                 Created
 */
@Component
public class PublicResourcePushHandler implements PostHandler {

    private static final Logger log = LoggerFactory.getLogger(PublicResourcePushHandler.class);

    @Autowired
    public PublicResourceService publicResourceService;

    @Reference(version = "1.0.0")
    private FileService fileService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (HandlerMethod.class.isInstance(handler)) {
            HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);

            ResourcePush resourcePush = handlerMethod.getMethodAnnotation(ResourcePush.class);
            if (resourcePush == null) {
                return;
            }
            String pushKey = resourcePush.pushKey();
            String accessKey = resourcePush.accessKey();
            if (StringUtils.isBlank(pushKey) || StringUtils.isBlank(accessKey)) {
                return;
            }
            String[] attached = resourcePush.attached();
            boolean hasAttached = attached.length > 0;
            if (!hasAttached) {
                attached = new String[1];
            }
            for (String a : attached) {
                String pushUrl = publicResourceService.getVariable(pushKey);
                String accessUrl = publicResourceService.getVariable(accessKey);
                if (StringUtils.isBlank(pushUrl)) {
                    continue;
                }
                if (hasAttached) {
                    pushUrl = String.format(pushUrl, a);
                    accessUrl = String.format(accessUrl, a);
                }
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OkHttpLogger());
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor).build();
                Request okRequest = new Request.Builder().cacheControl(new CacheControl.Builder().noCache().build()).get().url(pushUrl).build();
                Response okResponse = client.newCall(okRequest).execute();
                log.debug("Resource push result: {}", okResponse.toString());
                if (StringUtils.isNotBlank(accessUrl)) {
                    try {
                        fileService.refreshCDNUrl(accessUrl);
                    } catch (Exception e) {
                        DEBUG(log, "", e);
                    }
                }
            }
        }
    }

    private static class OkHttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            DEBUG(log, "{}", message);
        }
    }
}
