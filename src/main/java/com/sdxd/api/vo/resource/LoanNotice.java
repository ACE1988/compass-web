package com.sdxd.api.vo.resource;

import com.sdxd.data.dubbo.api.response.pojo.H5WifiLoginBo;

import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.index
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/31     melvin                 Created
 */
public class LoanNotice {

    private String version;
    private List<H5WifiLoginBo> creditLine;
    private List<H5WifiLoginBo> loan;
    private List<H5WifiLoginBo> rollover;

    public LoanNotice(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<H5WifiLoginBo> getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(List<H5WifiLoginBo> creditLine) {
        this.creditLine = creditLine;
    }

    public List<H5WifiLoginBo> getLoan() {
        return loan;
    }

    public void setLoan(List<H5WifiLoginBo> loan) {
        this.loan = loan;
    }

    public List<H5WifiLoginBo> getRollover() {
        return rollover;
    }

    public void setRollover(List<H5WifiLoginBo> rollover) {
        this.rollover = rollover;
    }
}
