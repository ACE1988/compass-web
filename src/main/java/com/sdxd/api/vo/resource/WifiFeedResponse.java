package com.sdxd.api.vo.resource;

import com.sdxd.api.vo.admin.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.resource
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/21    wenzhou.xu              Created
 */
public class WifiFeedResponse {
    private Integer retCd; //0：成功，data非空、-1：成功，data空、大于零：失败
    private String retMsg; //retCd非0时，返回错误消息
    private List<WifiFeedData> data;//feed素材列表

    public WifiFeedResponse(List<Feed> feedList){
        retCd = 0;
        initData(feedList);
    }

    private void initData(List<Feed> feedList) {
        data = new ArrayList<>();
        feedList.stream().map(WifiFeedData::new).forEach(wifiFeedData -> data.add(wifiFeedData));
    }

    public WifiFeedResponse(Integer retCd, String retMsg){
        this.retCd = retCd;
        this.retMsg = retMsg;
    }

    public Integer getRetCd() {
        return retCd;
    }

    public void setRetCd(Integer retCd) {
        this.retCd = retCd;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<WifiFeedData> getData() {
        return data;
    }

    public void setData(List<WifiFeedData> data) {
        this.data = data;
    }
}
