package com.sdxd.api.vo.certification;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certification
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/5     melvin                 Created
 */
public class Picture {

    private String code;
    private String path;

    public Picture(String code, String path) {
        this.code = code;
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }
}
