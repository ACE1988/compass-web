package com.sdxd.api.vo.certification;

import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certification
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/26     melvin                 Created
 */
public class ShippingAddress {

    @ApiModelProperty("收货人姓名")
    private String receiver;
    @ApiModelProperty("联系电话")
    private String contactNumber;
    @ApiModelProperty("收货地址")
    private String address;
    @ApiModelProperty("是否默认地址")
    private boolean isDefault;
    @ApiModelProperty("最近使用该地址的订单时间")
    private String orderTime;

    public ShippingAddress(String receiver, String contactNumber, String address, boolean isDefault, String orderTime) {
        this.receiver = receiver;
        this.contactNumber = contactNumber;
        this.address = address;
        this.isDefault = isDefault;
        this.orderTime = orderTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getOrderTime() {
        return orderTime;
    }
}
