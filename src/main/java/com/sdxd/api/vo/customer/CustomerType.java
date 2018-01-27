package com.sdxd.api.vo.customer;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.customer
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/11     melvin                 Created
 */
public enum CustomerType {

    //用户类型 ：0-注册，1-非注册
    SIGN_UP(0), NON_SIGN_UP(1);

    private int code;

    CustomerType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
