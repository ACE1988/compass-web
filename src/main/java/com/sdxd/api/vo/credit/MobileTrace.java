package com.sdxd.api.vo.credit;

import com.sdxd.approve.dubbo.api.response.CellInfoResponse;

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
 * 16/11/28     melvin                 Created
 */
public class MobileTrace {

    private Integer countOfSameDevice;
    private Integer addressCount;
    private CellInfoResponse mobileInfo;
    private String mobileOnlineTime;

    public String getPhone() {
        return mobileInfo == null ? null : mobileInfo.getPhoneNum();
    }

    public Integer getCountOfSameDevice() {
        return countOfSameDevice;
    }

    public void setCountOfSameDevice(Integer countOfSameDevice) {
        this.countOfSameDevice = countOfSameDevice;
    }

    public Integer getAddressCount() {
        return addressCount;
    }

    public void setAddressCount(Integer addressCount) {
        this.addressCount = addressCount;
    }

    public CellInfoResponse getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(CellInfoResponse mobileInfo) {
        this.mobileInfo = mobileInfo;
    }

    public String getMobileOnlineTime() {
        return mobileOnlineTime;
    }

    public void setMobileOnlineTime(String mobileOnlineTime) {
        this.mobileOnlineTime = mobileOnlineTime;
    }
}
