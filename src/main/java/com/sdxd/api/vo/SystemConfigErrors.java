package com.sdxd.api.vo;

import com.sdxd.common.web.vo.ErrorCode;

public interface SystemConfigErrors {

    ErrorCode FETCH_CONFIG_BYTE_ARRAY_ERROR = new ErrorCode("1300001", "配置获取字节数组失败");
    ErrorCode LOAD_CONFIG_REPEAT = new ErrorCode("1300002", "导入配置重复");
    ErrorCode SYNC_CONFIG_FAIL = new ErrorCode("1300003", "配置同步失败");
    ErrorCode DELETE_CONFIG_FAIL = new ErrorCode("1300004", "配置删除失败");
    ErrorCode UPDATE_CONFIG_FAIL = new ErrorCode("1300005", "配置更新失败");
    ErrorCode CREATE_CONFIG_FAIL = new ErrorCode("1300006", "配置创建失败");

}
