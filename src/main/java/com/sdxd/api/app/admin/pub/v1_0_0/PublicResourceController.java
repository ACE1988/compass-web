package com.sdxd.api.app.admin.pub.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import com.sdxd.activities.api.dto.activity.ActivityInfo;
import com.sdxd.activities.api.dto.activity.ActivitySettingItem;
import com.sdxd.api.service.PublicResourceService;
import com.sdxd.api.vo.resource.*;
import com.sdxd.cms.dubbo.api.pojo.CmsBannerVo;
import com.sdxd.cms.dubbo.api.pojo.CmsNoticeVo;
import com.sdxd.common.web.rest.SignatureIgnore;
import com.sdxd.common.web.trace.HideBody;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.response.pojo.CmsSuspensionSettingVo;
import com.sdxd.file.dubbo.api.FileService;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.pay.response.BankSourceDetail;
import com.sdxd.repayment.dubbo.response.RepayModeResponse;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sdxd.admin.dubbo.api.constants.SystemVariableCode.*;
import static com.sdxd.common.web.util.HttpUtil.toCamelCase;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.Throwables.propagate;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;
import static com.sdxd.product.enums.ProductChargeType.SERVICE_CHARGE;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/7/13     melvin                 Created
 */
@SignatureIgnore
@Api(value = "Public-Resource-API", produces = "application/json")
@Controller
@RequestMapping(value = "/public-resource", produces = "application/json;charset=utf-8")
public class PublicResourceController {

    @Reference(version = "1.0.0")
    private FileService fileService;

    @Autowired
    private PublicResourceService publicResourceService;

    @ApiOperation(value = "查询App版本信息", notes = "查询App版本信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/app-version", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<AppVersion> searchAppVersion() {
        try {
            AppVersion appVersion =
                    forValue(new AppVersion()).
                            parallel(() -> publicResourceService.getVariable(VERSION_CODE_ANDROID), AppVersion::setAppVersionCode).
                            parallel(() -> publicResourceService.getVariable(ANDROID_APPINSTALL_LINK), AppVersion::setAndroidAppInstallLink).
                            parallel(() -> publicResourceService.getVariable(ANDROID_FORCE_UPGRADE), AppVersion::setAndroidForceUpgrade).
                            parallel(() -> publicResourceService.getVariable("XZC_VERSION_CODE_IOS"), AppVersion::setIosVersionCodeXzc).
                            parallel(() -> publicResourceService.getVariable("JZ_VERSION_CODE_IOS"), AppVersion::setIosVersionCodeJz).
                            parallel(() -> publicResourceService.getVariable("XZC_IOS_FORCE_UPGRADE"), AppVersion::setIosForceUpgradeXzc).
                            parallel(() -> publicResourceService.getVariable("JZ_IOS_FORCE_UPGRADE"), AppVersion::setIosForceUpgradeJz).
                            parallel(() -> publicResourceService.getVariable("SIGNATURE_DEBUG"), AppVersion::setAppDebugEnabled).
                            start();
            return ok(appVersion);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询默认产品信息", notes = "查询默认产品信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/default-product", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<DefaultProduct> searchDefaultProduct() {
        try {
            DefaultProduct defaultProduct =
                    forValue(new DefaultProduct()).
                            parallel(() -> publicResourceService.getVariable("PRODUCT_NAME"), DefaultProduct::setProductName).
                            parallel(() -> publicResourceService.getVariable("PRODUCT_AMOUNT"), DefaultProduct::setProductAmount).
                            parallel(() -> publicResourceService.getVariable("PRODUCT_DESCRIPTION"), DefaultProduct::setProductDescription).
                            parallel(() -> publicResourceService.getVariable("SHOW_NEW_CONTRACT"), DefaultProduct::setShowNewContract).
                            start();
            return ok(defaultProduct);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询Banner", notes = "查询Banner")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/banners", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<CmsBannerVo>> searchBanners(
            @ApiParam(value = "类型", required = true) @RequestParam(value = "type") String type
    ) {
        try {
            List<CmsBannerVo> banners = publicResourceService.getBanners(type);
            return ok(banners);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询公告", notes = "查询公告")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> searchNotice() {
        try {
            List<CmsNoticeVo> notice = publicResourceService.getNotice();
            return ok(notice);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询轮播信息", notes = "查询轮播信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/carousel-messages", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<LoanNotice> getLoanNotice() {
        try {
            LoanNotice loanNotice =
                    forValue(new LoanNotice(null)).
                            parallel(() -> publicResourceService.getLoanNotice(1), LoanNotice::setLoan).
                            parallel(() -> publicResourceService.getLoanNotice(2), LoanNotice::setRollover).
                            parallel(() -> publicResourceService.getLoanNotice(3), LoanNotice::setCreditLine).
                            start();
            return ok(loanNotice);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询活动信息", notes = "查询活动信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Activity>> getActivities() {
        try {
            List<ActivityInfo> activities = publicResourceService.getActivities();
            if (activities == null) {
                return ok();
            }
            List<Activity> result = activities.stream().
                    map(activity -> {
                        List<ActivitySettingItem> settingItems = propagate(() -> publicResourceService.getActivitySettings(activity.getActivityKey()));
                        return new Activity(activity, settingItems);
                    }).
                    collect(Collectors.toList());

            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询活动配置信息", notes = "查询活动配置信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/activities-market", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ActivityMarketSetting> getActivitiesMarket() {
        try {
            ActivityMarketSetting result = new ActivityMarketSetting();

            // 设置悬浮数据
            List<CmsSuspensionSettingVo> cmsSuspensionSettingVoList = publicResourceService.getSuspentionSetting();
            result.getCmsSuspensionSettingVoList().addAll(cmsSuspensionSettingVoList);

            // 设置最小金额
            String withdrawAmount = publicResourceService.getWithdrawAmount();
            result.setWithdrawAmount(withdrawAmount);

            // 设置活动列表
            List<ActivityInfo> activities = publicResourceService.getActivities();
            if (activities == null) {
                return ok();
            }
            activities.sort(new Comparator<ActivityInfo>() {
                @Override
                public int compare(ActivityInfo o1, ActivityInfo o2) {
                    return o1.getShowSeq() - o2.getShowSeq();
                }
            });
            List<Activity> list = activities.stream().
                    map(activity -> {
                        List<ActivitySettingItem> settingItems = propagate(() -> publicResourceService.getActivitySettings(activity.getActivityKey()));
                        return new Activity(activity, settingItems);
                    }).
                    collect(Collectors.toList());
            result.getActivities().addAll(list);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询产品信息", notes = "查询产品信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Products> getProducts() {
        try {
            String chargeTypeValues = publicResourceService.getVariable("PRODUCT_CHARGE_TYPES");
            String loanText = publicResourceService.getVariable("PRODUCT_LOAN_TEXT");
            String interestRateText = publicResourceService.getVariable("PRODUCT_INTEREST_RATE_TEXT");
            String loanUseText = publicResourceService.getVariable("LOAN_USE");
            String[] loanUse = StringUtils.isBlank(loanUseText) ? null : loanUseText.split(",");
            Set<String> showingChargeTypes = StringUtils.isBlank(chargeTypeValues) ? null : Sets.newHashSet(chargeTypeValues.split(","));
            boolean containsServiceCharge = showingChargeTypes != null && showingChargeTypes.contains(toCamelCase(SERVICE_CHARGE.code));
            List<Product> products = publicResourceService.getProducts(containsServiceCharge);
            return ok(new Products(products, loanText, interestRateText, loanUse));
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询息费信息", notes = "查询息费信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/product-charges", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Map<String, Map<String, BigDecimal>>> getProductCharges() {
        try {
            String chargeTypeValues = publicResourceService.getVariable("PRODUCT_CHARGE_TYPES");
            Set<String> chargeTypes = StringUtils.isBlank(chargeTypeValues) ? null : Sets.newHashSet(chargeTypeValues.split(","));
            List<Product>  products = publicResourceService.getProducts(false);
            List<Product>  deprecatedProducts = publicResourceService.getDeprecatedProducts(true);

            boolean noDeprecatedProducts = deprecatedProducts == null || deprecatedProducts.isEmpty();
            Stream<String> productStream = noDeprecatedProducts ? Stream.empty() : products.stream().flatMap(item -> item.getProductInfos().stream()).map(ProductDetails::getId);
            Stream<String> historyProductStream = (noDeprecatedProducts ? products : deprecatedProducts).stream().flatMap(item -> item.getProductInfos().stream()).map(ProductDetails::getId);

            Map<String, Map<String, BigDecimal>> current = publicResourceService.getProductCharges(productStream, chargeTypes, false);
            Map<String, Map<String, BigDecimal>> history = publicResourceService.getProductCharges(historyProductStream, chargeTypes, true);
            current.putAll(history);
            return ok(current);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询支持的银行列表信息", notes = "查询支持的银行列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/banks", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<BankSourceDetail>> getBanks() {
        try {
            List<BankSourceDetail> banks = publicResourceService.getBanks();
            return ok(banks);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取合作平台列表", notes = "获取合作平台列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/other-channels", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<OtherChannels> getOtherChannels(
            @ApiParam(value = "类型", required = true) @RequestParam(value = "type") String type) {
        try {
            OtherChannels channels = publicResourceService.getOtherChannels(type);
            return ok(channels);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取WifiFeed数据", notes = "获取WifiFeed数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/wifi-feed", method = RequestMethod.GET)
    @ResponseBody
    public Object wifiFeed() {

        try {
            return publicResourceService.searchWifiFeed();
        } catch (ProcessBizException e) {
            return new WifiFeedResponse(Integer.valueOf(e.getCode().getCode()), e.getMessage());
        }
    }

    @ApiOperation(value = "获取还款方式列表", notes = "获取还款方式列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/repayment-modes", method = RequestMethod.GET)
    @ResponseBody
    public Object repaymentModes() {
        try {
            Map<String, List<RepayModeResponse>> result = publicResourceService.getRepaymentModes();
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "刷新CDN数据", notes = "刷新CDN数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @HideBody(request = false, response = true)
    @SignatureIgnore
    @RequestMapping(value = "/cdn-refresh", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> refreshResource(@ApiParam(value = "URL") @RequestParam(value = "url") String url) {
        DubboResponse<String> response = fileService.refreshCDNUrl(url);
        return rest(response);
    }

}
