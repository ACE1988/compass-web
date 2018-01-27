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
public interface LendingErrors {

    ErrorCode EMPTY_BATCH_ID = new ErrorCode("1200001", "未选择批次号");
    ErrorCode EMPTY_BATCH_DETAILS_ID = new ErrorCode("1200002", "未选择批次明细号");
}
