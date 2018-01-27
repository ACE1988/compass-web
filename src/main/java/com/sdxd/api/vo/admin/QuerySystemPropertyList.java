package com.sdxd.api.vo.admin;

import lombok.Data;

@Data
public class QuerySystemPropertyList {

    private String appName;

    private String propName;

    private Integer pageSize;

    private Integer currentIndex;

}
