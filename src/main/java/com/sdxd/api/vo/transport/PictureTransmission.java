package com.sdxd.api.vo.transport;

import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.io.IOException;
import java.util.Base64;

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
 * 16/11/8     melvin                 Created
 */
public class PictureTransmission extends Transmission {

    @ApiParam(value = "文件内容", required = true)
    @NotNull(message = "文件内容未填写")
    @FormParam(value = "content")
    private MultipartFile multipartContent;

    public MultipartFile getMultipartContent() {
        return multipartContent;
    }

    public void setMultipartContent(MultipartFile multipartContent) {
        this.multipartContent = multipartContent;
        if (multipartContent == null) {
            return;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        try {
            this.setBase64Content(encoder.encodeToString(multipartContent.getBytes()));
        } catch (IOException e) {
        }
    }
}
