package com.sdxd.api.vo.credit;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/16     melvin                 Created
 */
public class Scores {

    private String zhiMaScore;
    private String baiRongScore;

    public String getZhiMaScore() {
        return zhiMaScore;
    }

    public void setZhiMaScore(String zhiMaScore) {
        this.zhiMaScore = zhiMaScore;
    }

    public String getBaiRongScore() {
        return baiRongScore;
    }

    public void setBaiRongScore(String baiRongScore) {
        this.baiRongScore = baiRongScore;
    }
}
