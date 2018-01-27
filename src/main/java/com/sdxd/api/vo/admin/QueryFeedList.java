package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.PageParameter;

import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.admin
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/7    wenzhou.xu              Created
 */
public class QueryFeedList extends PageParameter {

    private List<Integer> templateList;

    private Integer display;

    public List<Integer> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Integer> templateList) {
        this.templateList = templateList;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }
}
