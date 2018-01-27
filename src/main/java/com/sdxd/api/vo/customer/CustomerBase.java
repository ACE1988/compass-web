package com.sdxd.api.vo.customer;

import com.sdxd.user.api.constant.UserStatus;
import com.sdxd.user.api.response.UserProfileIdentityInfo;

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
 * 17/3/23     melvin                 Created
 */
public class CustomerBase {

    private String userId;
    private String phone;
    private UserStatus statusEnum;
    private UserProfileIdentityInfo userProfileIdentityInfo;

    public CustomerBase() {}

    public CustomerBase(String userId, String phone, UserStatus statusEnum, UserProfileIdentityInfo userProfileIdentityInfo) {
        this.userId = userId;
        this.phone = phone;
        this.statusEnum = statusEnum;
        this.userProfileIdentityInfo = userProfileIdentityInfo;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public UserStatus getStatusEnum() {
        return statusEnum;
    }

    public UserProfileIdentityInfo getUserProfileIdentityInfo() {
        return userProfileIdentityInfo;
    }
}
