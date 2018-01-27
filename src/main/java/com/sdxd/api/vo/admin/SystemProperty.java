package com.sdxd.api.vo.admin;

import lombok.Data;

@Data
public class SystemProperty {

    private String appName;

    private String propName;

    private String propValue;

    private String zkAddress;

}
