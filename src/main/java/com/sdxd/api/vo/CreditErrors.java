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
public interface CreditErrors {

    ErrorCode NO_ACCESS = new ErrorCode("900000", "没有销户的权限");
    ErrorCode LENDER_NOT_FOUND = new ErrorCode("900001", "借贷人不存在");
    ErrorCode LENDER_PHONE_NOT_FOUND = new ErrorCode("900002", "借贷人手机号不存在");
    ErrorCode LENDER_CERT_NOT_COMPLETE = new ErrorCode("900003", "借贷人信息不完整");
    ErrorCode NO_PARAMETERS = new ErrorCode("900004", "请至少填写一个参数");
    ErrorCode ERROR_IDENTITY_CARD = new ErrorCode("900005", "身份证后六位不匹配");
    ErrorCode LOAN_CLEARING_EXIST = new ErrorCode("900006", "存在借款未还清");
    ErrorCode LENDER_USER_NOT_IDENTITY = new ErrorCode("900007", "借贷人并未进行实名认证");
}
