package com.sdxd.api.vo.transport;

import com.sdxd.common.web.vo.RestRequest;
import com.sdxd.file.dubbo.constants.enums.Privacy;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

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
public class Transmission extends RestRequest {

    @ApiParam(value = "文件名", required = true)
    @NotEmpty(message = "文件名未填写")
    @Size(max = 50)
    @HeaderParam(value = "transmission_file_name")
    private String name;

    @ApiParam(value = "文件大小(字节)", required = true)
    @NotEmpty(message = "文件大小未填写")
    @HeaderParam(value = "transmission_file_size")
    private String size;

    @ApiParam(value = "文件原始类型")
    @HeaderParam(value = "transmission_file_origin_type")
    private String originType;

    @ApiParam(value = "文件权限, PRIVATE: 私有, PUBLIC: 公共", allowableValues = "PRIVATE,PUBLIC", required = true)
    @NotNull(message = "文件权限未填写")
    @HeaderParam(value = "transmission_file_privacy")
    private Privacy privacy;

    @ApiParam(value = "文件Base64编码内容", required = true)
    @NotEmpty(message = "文件Base64编码内容未填写")
    @FormParam(value = "content")
    private String base64Content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }
}
