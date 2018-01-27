package com.sdxd.api.configure;

import com.sdxd.common.redis.template.RedisClientTemplate;
import com.sdxd.common.web.access.ThrottlingAdjuster;
import com.sdxd.common.web.access.ThrottlingConfigure;
import com.sdxd.common.web.configure.CommonConfig;
import com.sdxd.common.web.inform.ReportAdjuster;
import com.sdxd.common.web.inform.ReportConfigure;
import com.sdxd.common.web.util.configure.ConfigureSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.configure
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/29     melvin                 Created
 */
@Configuration
public class ApiConfig extends CommonConfig {

    @Bean
    @Autowired
    public ThrottlingAdjuster throttlingAdjuster(ThrottlingConfigure throttlingConfigure) {
        return null;
    }

    @Bean
    @Autowired
    public ReportConfigure reportConfigure(RedisClientTemplate redisClientTemplate, ConfigureSubscriber configureSubscriber) {
        return null;
    }

    @Bean
    @Autowired
    public ReportAdjuster reportAdjuster(ReportConfigure reportConfigure) {
        return null;
    }
}
