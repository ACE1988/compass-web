package com.sdxd.api.vo.lending;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/4/6     melvin                 Created
 */
public enum RepaymentStatus {

    PENDING_REPAYMENT((byte) 0, "未还清"),
    ALREADY_REPAYMENT((byte) 1, "已还清");

    private byte value;
    private String name;

    RepaymentStatus(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
