package com.sdxd.api.vo;

import com.sdxd.common.web.vo.ErrorCode;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/12/6     melvin                 Created
 */
public interface AdminErrors {

    ErrorCode ADMIN_NOT_FOUND = new ErrorCode("800001", "操作员不存在");
}
