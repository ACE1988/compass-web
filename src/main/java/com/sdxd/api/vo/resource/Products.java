package com.sdxd.api.vo.resource;

import org.apache.commons.lang3.StringUtils;

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
 * 2017/11/17     melvin                 Created
 */
public class Products {

    private List<Product> products;
    private String loanText; //*实际到手金额
    private String interestRateText; //年化利率约24%
    private String[] loanUse; //借款用途

    public Products(List<Product> products, String loanText, String interestRateText, String[] loanUse) {
        this.products = products;
        if (StringUtils.isNotBlank(loanText)) {
            this.loanText = loanText;
        }
        if (StringUtils.isNotBlank(interestRateText)) {
            this.interestRateText = interestRateText;
        }
        if (loanUse != null && loanUse.length > 0) {
            this.loanUse = loanUse;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getLoanText() {
        return loanText;
    }

    public String getInterestRateText() {
        return interestRateText;
    }

    public String[] getLoanUse() {
        return loanUse;
    }
}
