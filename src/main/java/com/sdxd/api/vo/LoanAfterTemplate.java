package com.sdxd.api.vo;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.util
 * 系统名           ：Excel模板
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/8    wenzhou.xu              Created
 */
public class LoanAfterTemplate {

    private String userId;

    private String phone;

    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || obj.getClass() != getClass())
            return false;
        return phone.equals(((LoanAfterTemplate) obj).getPhone());
    }
    @Override
    public int hashCode() {
        return phone.hashCode();
    }
}
