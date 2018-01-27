package com.sdxd.api.vo;

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
public class ProcessResult<T> {

    private String id;
    private T result;

    public ProcessResult(String id, T result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public T getResult() {
        return result;
    }
}
