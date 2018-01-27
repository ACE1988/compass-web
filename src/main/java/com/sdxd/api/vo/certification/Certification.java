package com.sdxd.api.vo.certification;

import com.sdxd.common.web.util.FieldIgnore;
import com.sdxd.common.web.vo.RestRequest;
import com.sdxd.user.api.constant.UserIdentityStatus;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certificate
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/9     melvin                 Created
 */
public class Certification extends RestRequest {

    @FieldIgnore
    private UserIdentityStatus status;

    public UserIdentityStatus getStatus() {
        return status;
    }

    public void setStatus(UserIdentityStatus status) {
        this.status = status;
    }
}
