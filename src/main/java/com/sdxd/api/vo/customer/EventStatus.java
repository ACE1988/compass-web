package com.sdxd.api.vo.customer;

import rx.Observable;

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
public enum EventStatus {

    //状态 1-结案，0-提交
    COMPLETE(1, "结案"), SUBMIT(0, "提交");

    private int code;
    private String value;

    EventStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public static String forStatusValue(int code) {
        return
            Observable.from(EventStatus.values()).
                    filter(eventStatus -> eventStatus.getCode() == code).
                    map(eventStatus -> eventStatus.value).
                    toBlocking().firstOrDefault(null);
    }
}
