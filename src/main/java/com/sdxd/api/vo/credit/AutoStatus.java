package com.sdxd.api.vo.credit;

/**
 * Created by lenovo on 2017/7/25.
 */
public class AutoStatus {
    private String autoPassCodes;
    private String autoPassLimit;
    private String shutdownStatus;
    private String approverPhoneotify;

    public String getAutoPassCodes() {
        return autoPassCodes;
    }

    public void setAutoPassCodes(String autoPassCodes) {
        this.autoPassCodes = autoPassCodes;
    }

    public String getAutoPassLimit() {
        return autoPassLimit;
    }

    public void setAutoPassLimit(String autoPassLimit) {
        this.autoPassLimit = autoPassLimit;
    }

    public String getShutdownStatus() {
        return shutdownStatus;
    }

    public void setShutdownStatus(String shutdownStatus) {
        this.shutdownStatus = shutdownStatus;
    }

    public String getApproverPhoneotify() {
        return approverPhoneotify;
    }

    public void setApproverPhoneotify(String approverPhoneotify) {
        this.approverPhoneotify = approverPhoneotify;
    }
}
