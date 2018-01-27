package com.sdxd.api.vo.certification;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
public class Addresses {

    @ApiModelProperty("电商类型, JD: 京东, TAO_BAO: 淘宝")
    private EBusinessType type;

    @ApiModelProperty("地址列表")
    private List<ShippingAddress> addresses;

    public Addresses(EBusinessType type, List<ShippingAddress> addresses) {
        this.type = type;
        this.addresses = addresses;
    }

    public EBusinessType getType() {
        return type;
    }

    public List<ShippingAddress> getAddresses() {
        return addresses;
    }
}
