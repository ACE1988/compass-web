package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.decision.api.DecisionApproveService;
import com.sdxd.decision.api.request.RiskHintDetailRequest;
import com.sdxd.decision.api.response.RiskHintDetailInfo;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.data;

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
 * 17/3/28     melvin                 Created
 */
@Service
public class RiskHintService {

    @Reference(version = "1.0.0")
    private DecisionApproveService decisionApproveService;

    public List<RiskHintDetailInfo> getRiskHintDetails(String sourceId) throws ProcessBizException {
        RiskHintDetailRequest request = new RiskHintDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSourceId(sourceId);
        DubboResponse<List<RiskHintDetailInfo>> response = decisionApproveService.queryRiskHintDetail(request);
        return data(response);
    }
}
