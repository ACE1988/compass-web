package com.sdxd.api.vo.credit;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sdxd.common.web.util.JsonUtil;
import com.sdxd.decision.api.response.RiskHintDetailInfo;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.sdxd.common.web.util.MapUtil.$;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/29     melvin                 Created
 */
public class RiskHint {

    @SuppressWarnings("unchecked")
    private Map<String, Consumer<Object>> consumers = $(
            "userId", value -> this.userIds = merge(List.class.cast(value)),
            "riskLevel", value -> this.riskLevel = String.class.cast(value),
            "supplement", value -> this.supplement = String.class.cast(value),
            "channelRisk", value -> this.channelRisk = String.class.cast(value),
            "fraudRisk", value -> this.fraudRisk = String.class.cast(value));

    private String code;
    private String description;

    private List<String> userIds;
    private String riskLevel;
    private String supplement;
    private String channelRisk;
    private String fraudRisk;

    public RiskHint(RiskHintDetailInfo hint) {
        if (hint != null) {
            this.code = hint.getHintCode();
            this.description = hint.getDescription();

            extract(hint.getDetail());
        }
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getSupplement() {
        return supplement;
    }

    public String getChannelRisk() {
        return channelRisk;
    }

    public String getFraudRisk() {
        return fraudRisk;
    }

    private void extract(String content) {
        //noinspection unchecked
        List<HintDetails> details = JsonUtil.fromJson(content, List.class, HintDetails.class);
        Observable.from(details).forEach(value -> {
            String field = value.getValueType();
            Object v = value.getValue();
            Consumer<Object> consumer = consumers.get(field);
            if (consumer != null) {
                consumer.accept(v);
            }
        });
    }

    private List<String> merge(List<String> userIds) {
        if (userIds == null) {
            return this.userIds;
        }
        Set<String> set = this.userIds == null ? Sets.newHashSet() : Sets.newHashSet(this.userIds);
        set.addAll(userIds);
        return Lists.newArrayList(set);
    }
}
