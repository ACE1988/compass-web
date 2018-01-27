package com.sdxd.api.util;

import java.lang.annotation.*;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.util
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/7/21     melvin                 Created
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourcePush {

    String pushKey();

    String accessKey();

    String[] attached() default {};
}
