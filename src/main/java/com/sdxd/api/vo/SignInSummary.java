package com.sdxd.api.vo;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

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
 * 16/12/9     melvin                 Created
 */
public class SignInSummary {

    private Map<String, Integer> signInPipeData;
    private String lastSignInLocation;
    private Date lastSignInTime;
    private Date lastSignInTimeExistLocation;

    public SignInSummary() {
        signInPipeData = Maps.newHashMap();
    }

    public void count(String location, Date signInTime) {
        if (lastSignInTime == null || signInTime.after(lastSignInTime)) {
            lastSignInTime = signInTime;
        }
        if (lastSignInTimeExistLocation == null || signInTime.after(lastSignInTimeExistLocation)) {
            lastSignInTimeExistLocation = signInTime;
            if (StringUtils.isNotBlank(location)) {
                lastSignInLocation = location;
            }
        }
        if (StringUtils.isBlank(location)) {
            return;
        }
        Integer count = signInPipeData.get(location);
        if (count == null) {
            count = 0;
        }
        count ++;
        signInPipeData.put(location, count);
    }

    public Map<String, Integer> getSignInPipeData() {
        return signInPipeData;
    }

    public String getLastSignInLocation() {
        return lastSignInLocation;
    }

    public Date getLastSignInTime() {
        return lastSignInTime;
    }
}
