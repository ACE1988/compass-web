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
 * 16/12/19     melvin                 Created
 */
public interface CustomerErrors {

    ErrorCode INVALID_INCOMING_PHONE = new ErrorCode("1100001", "来电号码与当前客户号码不一致");
}
