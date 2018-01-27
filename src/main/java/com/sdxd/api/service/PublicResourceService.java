package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sdxd.activities.api.ActivityDubboService;
import com.sdxd.activities.api.dto.activity.ActivityInfo;
import com.sdxd.activities.api.dto.activity.ActivitySettingItem;
import com.sdxd.activities.api.dto.activity.QueryByKeyReq;
import com.sdxd.admin.dubbo.api.SystemVariableDubboService;
import com.sdxd.admin.dubbo.api.constants.SystemVariableCode;
import com.sdxd.admin.dubbo.api.request.AppVesionRequest;
import com.sdxd.admin.dubbo.api.request.VariableCodeRequest;
import com.sdxd.admin.dubbo.api.response.AppVersionResponse;
import com.sdxd.admin.dubbo.api.response.SystemVariableResponse;
import com.sdxd.api.vo.admin.Feed;
import com.sdxd.api.vo.admin.QueryFeedList;
import com.sdxd.api.vo.resource.*;
import com.sdxd.balance.api.BalanceSettingDubboService;
import com.sdxd.balance.enums.SettingVariable;
import com.sdxd.balance.request.withdraw.GetBalanceSettingRequest;
import com.sdxd.cms.dubbo.api.CmsBannerDubboService;
import com.sdxd.cms.dubbo.api.CmsNoticeDubboService;
import com.sdxd.cms.dubbo.api.CmsShareDubboService;
import com.sdxd.cms.dubbo.api.OtherChannelDetailDubboService;
import com.sdxd.cms.dubbo.api.enums.FeedTemplate;
import com.sdxd.cms.dubbo.api.pojo.CmsBannerVo;
import com.sdxd.cms.dubbo.api.pojo.CmsNoticeVo;
import com.sdxd.cms.dubbo.api.pojo.OtherChannelDetailVo;
import com.sdxd.cms.dubbo.api.request.CmsShareRequest;
import com.sdxd.cms.dubbo.api.request.CmstomNoticeRequest;
import com.sdxd.cms.dubbo.api.request.QueryAllOtherChannelsRequest;
import com.sdxd.cms.dubbo.api.request.QueryCmsBannerRequest;
import com.sdxd.cms.dubbo.api.response.CmsShareResponse;
import com.sdxd.cms.dubbo.api.response.QueryCmsBannerResponse;
import com.sdxd.cms.dubbo.api.response.QueryCmsNoticeResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.QueryStatisticDataDubboService;
import com.sdxd.data.dubbo.api.cms.CmsSuspensionSettingDataDubboService;
import com.sdxd.data.dubbo.api.finance.H5WifiLoginDubboService;
import com.sdxd.data.dubbo.api.request.CmsSuspensionSettingRequest;
import com.sdxd.data.dubbo.api.request.H5WifiLoginRequest;
import com.sdxd.data.dubbo.api.response.CmsSuspensionSettingListResponse;
import com.sdxd.data.dubbo.api.response.pojo.CmsSuspensionSettingVo;
import com.sdxd.data.dubbo.api.response.pojo.H5WifiLoginBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.pay.api.BankSourceDubboService;
import com.sdxd.pay.response.BankSourceDetail;
import com.sdxd.product.api.ProductDubboService;
import com.sdxd.product.enums.ProductChargeType;
import com.sdxd.product.request.SingleProductIdRequest;
import com.sdxd.product.response.ProductChargeDetail;
import com.sdxd.product.response.ProductInfo;
import com.sdxd.product.response.ProductListItem;
import com.sdxd.repayment.dubbo.RepaymentModeDubboService;
import com.sdxd.repayment.dubbo.response.RepayModeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rx.Observable;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sdxd.common.web.util.HttpUtil.toCamelCase;
import static com.sdxd.common.web.util.MapUtil.$;
import static com.sdxd.common.web.util.ResponseUtil.copy;
import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.product.enums.ProductChargeType.*;
import static java.math.BigDecimal.ZERO;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.service
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/12/5     melvin                 Created
 */
@Service
public class PublicResourceService {

    @Reference(version = "1.0.0")
    private CmsBannerDubboService cmsBannerDubboService;

    @Reference(version = "1.0.0")
    private CmsNoticeDubboService cmsNoticeDubboService;

    @Reference(version = "1.0.0")
    private CmsShareDubboService cmsShareDubboService;

    @Reference(version = "1.0.0")
    private CmsSuspensionSettingDataDubboService cmsSuspensionSettingDataDubboService;

    @Reference(version = "1.0.0")
    private QueryStatisticDataDubboService queryStatisticDataDubboService;

    @Reference(version = "1.0.0")
    private SystemVariableDubboService systemVariableDubboService;

    @Reference(version = "1.0.0")
    private H5WifiLoginDubboService h5WifiLoginDubboService;

    @Reference(version = "1.0.0")
    private ActivityDubboService activityDubboService;

    @Reference(version = "1.0.0")
    private ProductDubboService productDubboService;

    @Reference(version = "1.0.0")
    private BankSourceDubboService bankSourceDubboService;

    @Reference(version = "1.0.0")
    private OtherChannelDetailDubboService otherChannelDetailDubboService;

    @Reference(version = "1.0.0")
    private BalanceSettingDubboService balanceSettingDubboService;

    @Reference(version = "1.0.0")
    private RepaymentModeDubboService repaymentModeDubboService;

    @Resource
    private FeedService feedService;

    public List<CmsBannerVo> getBanners(String type) throws ProcessBizException {
        QueryCmsBannerRequest request = new QueryCmsBannerRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setType(type);
        DubboResponse<QueryCmsBannerResponse> response = cmsBannerDubboService.queryCmsBanner(request);
        return data(response, QueryCmsBannerResponse::getList);
    }

    public List<CmsNoticeVo> getNotice() throws ProcessBizException {
        CmstomNoticeRequest request = new CmstomNoticeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStatus(true);
        DubboResponse<QueryCmsNoticeResponse> response = cmsNoticeDubboService.queryCmsNoticeStatus(request);
        return data(response, QueryCmsNoticeResponse::getList);
    }

    public String getVariable(SystemVariableCode code) throws ProcessBizException {
        AppVesionRequest request = new AppVesionRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSystemVariableCode(code);
        DubboResponse<AppVersionResponse> response = systemVariableDubboService.getValueByCode(request);
        return data(response, resp -> resp == null ? null : resp.getValue());
    }

    public ShareContent getShareContent(String id) throws ProcessBizException {
        CmsShareRequest request = new CmsShareRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(id);
        DubboResponse<CmsShareResponse> response = cmsShareDubboService.findById(request);
        return data(response, share -> share == null || share.getCmsShareVo() == null ?
                null : new ShareContent(
                share.getCmsShareVo().getId(), share.getCmsShareVo().getTitle(),
                share.getCmsShareVo().getContent(), share.getCmsShareVo().getImageCode(),
                share.getCmsShareVo().getImageUrl(), share.getCmsShareVo().getLink(),
                share.getCmsShareVo().getDescription()));
    }

    public String getLoanNoticeVersion() throws ProcessBizException {
        DubboResponse<String> response = h5WifiLoginDubboService.queryVersion();
        return data(response);
    }

    public List<H5WifiLoginBo> getLoanNotice(int type) throws ProcessBizException {
        H5WifiLoginRequest request = new H5WifiLoginRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setType(type);
        DubboResponse<List<H5WifiLoginBo>> response = h5WifiLoginDubboService.query(request);
        return data(response);
    }

    public String getVariable(String code) throws ProcessBizException {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableCode(code);
        DubboResponse<SystemVariableResponse> response = systemVariableDubboService.getValueByCodeStr(request);
        return data(response, resp -> resp == null ? null : resp.getValue());
    }

    public Boolean saveVariable(String code, String value) throws ProcessBizException {
        VariableCodeRequest request = new VariableCodeRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariableValue(code);
        request.setVariableCode(value);
        DubboResponse<Boolean> response = systemVariableDubboService.updateValueByCodeStr(request);
        return data(response);
    }

    public List<ActivityInfo> getActivities() throws ProcessBizException {
        DubboResponse<List<ActivityInfo>> response = activityDubboService.queryOpenActivitys();
        return data(response);
    }


    public List<ActivitySettingItem> getActivitySettings(String key) throws ProcessBizException {
        QueryByKeyReq request = new QueryByKeyReq();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setKey(key);
        DubboResponse<List<ActivitySettingItem>> response = activityDubboService.queryActivitySettings(request);
        return data(response);
    }

    public Map<String, Map<String, BigDecimal>> getProductCharges(Stream<String> stream, Set<String> showingChargeTypes, boolean history) {
        Map<String, Map<String, BigDecimal>> all = Maps.newHashMap();
        return stream.reduce(all, (map, productId) -> {
            Map<String, BigDecimal> charge = map.get(productId);
            if (charge == null) {
                charge = getCharge(productId);

                if (charge == null) {
                    return map;
                }

                String serviceChargeKey = toCamelCase(SERVICE_CHARGE.code);
                BigDecimal capital = charge.containsKey(CAPITAL.code) ? charge.get(CAPITAL.code) : ZERO;    //借款本金
                BigDecimal serviceCharge = charge.containsKey(serviceChargeKey) ? charge.get(serviceChargeKey) : ZERO;  //居间服务费
                BigDecimal interest = charge.containsKey(INTEREST.code) ? charge.get(INTEREST.code) : ZERO; //借款利息
                BigDecimal actualCapital = capital.subtract(serviceCharge);
                BigDecimal repayableAmount = capital.add(interest);
                if (!history && showingChargeTypes != null && showingChargeTypes.contains(serviceChargeKey)) {
                    repayableAmount = repayableAmount.add(serviceCharge);
                }
                charge.put("actualCapital", actualCapital);     //实际到手金额
                charge.put("arriveAmount", capital);            //到帐本金
                charge.put("repayableAmount", repayableAmount); //到期应还

                if (!history) {
                    charge.put("serviceChargeText", serviceCharge);
                    charge.put("repayableFullAmountText", repayableAmount.add(serviceCharge));

                    charge = charge.entrySet().stream().
                            filter(entry -> showingChargeTypes == null || showingChargeTypes.contains(entry.getKey())).
                            collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                } else if (charge.containsKey(serviceChargeKey)){
                    charge.put(serviceChargeKey, charge.get(serviceChargeKey).negate());
                }

                map.put(productId, charge);
            }
            return map;
        }, (m1, m2) -> m1);
    }

    public List<String> getDeprecatedProductIds() throws ProcessBizException {
        DubboResponse<List<String>> response = productDubboService.getOldVersionProductIdList();
        return data(response);
    }

    public List<Product> getDeprecatedProducts(boolean containsServiceCharge) throws ProcessBizException {
        DubboResponse<List<ProductListItem>> response = productDubboService.getOldVersionProductList();
        return data(response, data -> data.stream().map(item -> {
            List<ProductDetails> productInfos =
                    Observable.from(item.getProductInfos()).
                            map(info -> copy(info, new ProductDetails(containsServiceCharge))).
                            toList().toBlocking().first();
            return new Product(item.getAmount(), productInfos);
        }).collect(Collectors.toList()));
    }

    public List<Product> getProducts(boolean containsServiceCharge) throws ProcessBizException {
        DubboResponse<List<ProductListItem>> response = productDubboService.getProductList();
        return data(response, result -> result == null ?
                null :
                result.stream().
                        map(item -> {
                            List<ProductInfo> info = item.getProductInfos();
                            List<ProductDetails> products =
                                    info == null ?
                                            null :
                                            info.stream().
                                                    map(p -> copy(p, new ProductDetails(containsServiceCharge))).
                                                    collect(Collectors.toList());
                            return new Product(item.getAmount(), products);
                        }).
                        collect(Collectors.toList()));
    }

    public Map<String, BigDecimal> getCharge(String productId) {
        SingleProductIdRequest request = new SingleProductIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setProductId(productId);
        DubboResponse<List<ProductChargeDetail>> response = productDubboService.getChargeDetail(request);
        try {
            return data(response, charges -> charges == null ?
                    null :
                    charges.stream().filter(charge -> {
                        ProductChargeType currentType = ProductChargeType.getChargeTypeByCode(charge.getChargeType());
                        return (currentType != ProductChargeType.OVERDUE_INTEREST_RATE && currentType != ProductChargeType.OVERDUE_SERVICE_RATE);
                    }).collect(Collectors.toMap(charge -> toCamelCase(charge.getChargeType()), ProductChargeDetail::getValue)));
        } catch (ProcessBizException e) {
            return null;
        }
    }

    public List<BankSourceDetail> getBanks() throws ProcessBizException {
        DubboResponse<List<BankSourceDetail>> response = bankSourceDubboService.selectAllValid();
        return data(response);
    }

    public OtherChannels getOtherChannels(String type) throws ProcessBizException {
        OtherChannels channels = new OtherChannels() ;
        String value = getVariable("CHANNEL_DESCRIBE");
        String showType = getVariable("CHANNEL_SHOW_TYPE");
        QueryAllOtherChannelsRequest request = new QueryAllOtherChannelsRequest() ;
        request.setChannelType(type);
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<List<OtherChannelDetailVo>> response = otherChannelDetailDubboService.queryAll(request);
        List<OtherChannelDetailVo> list = response == null ? null : response.getData();
        channels.setDescribe(value);
        channels.setChannelShowType(showType);
        channels.setList(
                list == null ?
                        null :
                        list.stream().filter(channel -> {
                            String showTypeValue = channel.getChannelShowType();
                            if (StringUtils.isBlank(showTypeValue)) {
                                return false;
                            }
                            String[] showTypes = showTypeValue.split(",");
                            Set<String> types = Sets.newHashSet(showTypes);
                            return types.contains(showType);
                        }).collect(Collectors.toList()));
        return channels;
    }

    public WifiFeedResponse searchWifiFeed() throws ProcessBizException {
        WifiFeedResponse wifiFeedResponse;

        //2.查询信息
        QueryFeedList query = new QueryFeedList();
        query.setPageNo(1);
        query.setPageSize(100);
        query.setDisplay(1);//只查询需要显示的
        List<Integer> templateList = new ArrayList<>();
        templateList.add(FeedTemplate.SMALL_MULTI_PIC_TXT.getCode());
        query.setTemplateList(templateList);

        PaginationSupport<Feed> page = feedService.searchCmsFeedList(query);
        List<Feed> feedList = page.getList();
        //3.重新封装信息
        if (feedList == null || feedList.isEmpty())
            wifiFeedResponse = new WifiFeedResponse(-1, "未找到Feed信息");
        else
            wifiFeedResponse = new WifiFeedResponse(feedList);
        return wifiFeedResponse;
    }

    public List<CmsSuspensionSettingVo> getSuspentionSetting() {
        CmsSuspensionSettingRequest request = new CmsSuspensionSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        DubboResponse<CmsSuspensionSettingListResponse> response = cmsSuspensionSettingDataDubboService.findAll(request);
        return response.getData().getCmsSuspensionSettingVoList();
    }

    public String getWithdrawAmount() {
        GetBalanceSettingRequest request = new GetBalanceSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariable(SettingVariable.WITHDRAW_AMOUNT);
        DubboResponse<String> response = balanceSettingDubboService.getVariable(request);
        return response.getData();
    }

    public Map<String, List<RepayModeResponse>> getRepaymentModes() throws ProcessBizException {
        DubboResponse<List<RepayModeResponse>> response = repaymentModeDubboService.getRepayModeList();
        List<RepayModeResponse> modes = data(response, result -> result == null ? null : result.stream().peek(r -> {
            r.setCreateTime(null);
            r.setUpdateTime(null);
            r.setDeleteFlag(null);
        }).collect(Collectors.toList()));
        return $("repaymentModes", modes);
    }
}
