package com.sdxd.api.vo.transport;

import com.sdxd.file.dubbo.response.UploadFileResponse;

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
 * 16/11/11     melvin                 Created
 */
public class IndexedTransmissionResult {

    private String error;
    private String message;

    private String index;
    private UploadFileResponse result;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public UploadFileResponse getResult() {
        return result;
    }

    public void setResult(UploadFileResponse result) {
        this.result = result;
    }
}
