package com.sdxd.api.vo.resource;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.product
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/21     melvin                 Created
 */
@ApiModel("产品信息")
public class Product {

    private List<ProductDetails> productInfos = Lists.newArrayList();

    private Integer amount;

    public Product(Integer amount, List<ProductDetails> productInfos) {
        this.amount = amount;
        this.productInfos = productInfos;
    }

    @ApiModelProperty("金额")
    public Integer getAmount() {
        return amount;
    }

    @ApiModelProperty("产品详情")
    public List<ProductDetails> getProductInfos() {
        return productInfos;
    }
}
