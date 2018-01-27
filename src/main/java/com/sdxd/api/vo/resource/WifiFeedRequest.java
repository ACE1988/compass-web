package com.sdxd.api.vo.resource;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.resource
 * 系统名           ：
 * 描  述           ：协议中除了sign字段，默认都要参与签名，签名顺序按参数名称升序排序，参数名不参与签名
 * key单独给出
 * eg:A=1;B=4,C=3
 * sign = MD5(143key)
 * <p>
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/21    wenzhou.xu              Created
 */
public class WifiFeedRequest {
    @ApiParam(value = "用户开放id，必传")
    @QueryParam(value = "openId")
    private String openId;//用户开放id，必传,后续取消

    @ApiParam(value = "设备唯一标示，必传")
    @QueryParam(value = "deviceId")
    @NotBlank
    private String deviceId;//设备唯一标示，必传

    @ApiParam(value = "支持模板，eg：100_102_103，必传")
    @QueryParam(value = "template")
    @NotBlank
    private String template;//支持模板，必传

    @ApiParam(value = "客户端当前页号，必传")
    @QueryParam(value = "pageNo")
    @NotNull
    private Integer pageNo;//客户端当前页号，必传

    @ApiParam(value = "请求数量，必传")
    @QueryParam(value = "limit")
    @NotNull
    private Integer limit;//请求数量，必传

    @ApiParam(value = "本次请求会话id，必传")
    @QueryParam(value = "serialId")
    @NotBlank
    private String serialId;//本次请求会话id，必传

    @ApiParam(value = "经度，可不传")
    @QueryParam(value = "lng")
    private Double lng;//经度，可不传

    @ApiParam(value = "纬度，可不传")
    @QueryParam(value = "lat")
    private Double lat;//纬度，可不传
    /**
     * 坐标类型，可不传
     * b：百度坐标系
     * g：google坐标系
     * t：腾讯坐标
     * a：高德坐标
     * w：ios WGS84坐标
     */
    @ApiParam(value = "坐标类型，可不传")
    @QueryParam(value = "coordType")
    private String coordType;

    @ApiParam(value = "客户端操作系统，android/ios，必传")
    @QueryParam(value = "os")
    @NotBlank
    private String os;//客户端操作系统，android/ios，必传

    /**
     * 客户端是否支持https，必传
     * 0：不支持https
     * 1：支持https
     */
    @ApiParam(value = "客户端是否支持https，0：不支持，1：支持，必传")
    @QueryParam(value = "ssl")
    @NotNull
    private Integer ssl;

    /**
     * 当前频道id，必传
     * 1：推荐tab
     * 2：视频tab
     */
    @ApiParam(value = "当前频道ID，1：推荐tab，2：视频tab，必传")
    @QueryParam(value = "channel")
    @NotNull
    private Integer channel;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCoordType() {
        return coordType;
    }

    public void setCoordType(String coordType) {
        this.coordType = coordType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getSsl() {
        return ssl;
    }

    public void setSsl(Integer ssl) {
        this.ssl = ssl;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
