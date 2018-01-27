package com.sdxd.api.vo.lending;

import com.sdxd.api.vo.ProcessResult;

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
 * 16/11/30     melvin                 Created
 */
public class LendingResult<T> {

    private List<ProcessResult<T>> lending;

    public LendingResult(List<ProcessResult<T>> lending) {
        this.lending = lending;
    }

    public List<ProcessResult<T>> getLending() {
        return lending;
    }
}
