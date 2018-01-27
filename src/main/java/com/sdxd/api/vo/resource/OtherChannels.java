package com.sdxd.api.vo.resource;

import com.sdxd.cms.dubbo.api.pojo.OtherChannelDetailVo;

import java.util.List;

/**
 * @author liujie
 * @Date 2017/5/27
 * 盛大小贷
 */
public class OtherChannels {

    //描述
    private String describe ;
    //0表示列表 1表示表格
    private String channelShowType;
    private List<OtherChannelDetailVo> list ;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getChannelShowType() {
        return channelShowType;
    }

    public void setChannelShowType(String channelShowType) {
        this.channelShowType = channelShowType;
    }

    public List<OtherChannelDetailVo> getList() {
        return list;
    }

    public void setList(List<OtherChannelDetailVo> list) {
        this.list = list;
    }
}


