package com.sdxd.api.vo.resource;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.resource
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/7/13     melvin                 Created
 */
public class AppVersion {

    private String appVersionCode;
    private String androidForceUpgrade;
    private String iosVersionCodeXzc;
    private String iosForceUpgradeXzc;
    private String iosVersionCodeJz;
    private String iosForceUpgradeJz;
    private String androidAppInstallLink;

    private String appDebugEnabled;

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAndroidForceUpgrade() {
        return androidForceUpgrade;
    }

    public void setAndroidForceUpgrade(String androidForceUpgrade) {
        this.androidForceUpgrade = androidForceUpgrade;
    }

    public String getIosVersionCodeXzc() {
        return iosVersionCodeXzc;
    }

    public void setIosVersionCodeXzc(String iosVersionCodeXzc) {
        this.iosVersionCodeXzc = iosVersionCodeXzc;
    }

    public String getIosForceUpgradeXzc() {
        return iosForceUpgradeXzc;
    }

    public void setIosForceUpgradeXzc(String iosForceUpgradeXzc) {
        this.iosForceUpgradeXzc = iosForceUpgradeXzc;
    }

    public String getIosVersionCodeJz() {
        return iosVersionCodeJz;
    }

    public void setIosVersionCodeJz(String iosVersionCodeJz) {
        this.iosVersionCodeJz = iosVersionCodeJz;
    }

    public String getIosForceUpgradeJz() {
        return iosForceUpgradeJz;
    }

    public void setIosForceUpgradeJz(String iosForceUpgradeJz) {
        this.iosForceUpgradeJz = iosForceUpgradeJz;
    }

    public String getAndroidAppInstallLink() {
        return androidAppInstallLink;
    }

    public void setAndroidAppInstallLink(String androidAppInstallLink) {
        this.androidAppInstallLink = androidAppInstallLink;
    }

    public String getAppDebugEnabled() {
        return appDebugEnabled;
    }

    public void setAppDebugEnabled(String appDebugEnabled) {
        this.appDebugEnabled = appDebugEnabled;
    }
}
