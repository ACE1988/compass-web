package com.sdxd.api.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
 * 16/12/27     melvin                 Created
 */
public class PK<T> {

    public boolean empty() {
        return getIds() == null ||
                getIds().isEmpty() ||
                getIds().stream().filter(
                        id -> id != null &&
                                (!(id instanceof String) ||
                                        StringUtils.isNotBlank(String.class.cast(id)))).count() == 0;
    }

    private List<T> ids;

    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }
}
