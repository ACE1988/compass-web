package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.certification.Addresses;
import com.sdxd.api.vo.certification.EBusinessType;
import com.sdxd.api.vo.certification.ShippingAddress;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.data.dubbo.api.risk.jingdong.JDDataDubboService;
import com.sdxd.data.dubbo.api.risk.jingdong.dto.ReceiveRequest;
import com.sdxd.data.dubbo.api.risk.jingdong.dto.ReceiveResponse;
import com.sdxd.data.dubbo.api.risk.taobao.TaobaoDataDubboService;
import com.sdxd.data.dubbo.api.risk.taobao.dto.TaobaoShippingAddressBo;
import com.sdxd.data.dubbo.api.risk.user.dto.UserBaseRequest;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sdxd.api.vo.certification.EBusinessType.JD;
import static com.sdxd.api.vo.certification.EBusinessType.TAO_BAO;
import static com.sdxd.common.web.util.ConstraintUtil.isConstraintViolationException;
import static com.sdxd.common.web.util.ConstraintUtil.throwValidationException;
import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.util.Throwables.propagate;
import static com.sdxd.common.web.util.Throwables.throwProcessBizException;

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
 * 17/5/27     melvin                 Created
 */
@Service
public class ShippingAddressService {

    @Reference(version = "1.0.0")
    private JDDataDubboService jdDataDubboService;

    @Reference(version = "1.0.0")
    private TaobaoDataDubboService taobaoDataDubboService;

    private EnumMap<EBusinessType, Function<Long, Callable<List<ShippingAddress>>>> functions = new EnumMap<>(EBusinessType.class);

    public ShippingAddressService() {
        functions.put(JD, userId -> () -> getJDShippingAddresses(userId));
        functions.put(TAO_BAO, userId -> () -> getTaoBaoShippingAddresses(userId));
    }

    public List<Addresses> getAll(Long userId) throws ProcessBizException {
        try {
            return functions.entrySet().stream().map(entry -> {
                EBusinessType type = entry.getKey();
                Function<Long, Callable<List<ShippingAddress>>> function = entry.getValue();
                Callable<List<ShippingAddress>> callable = function.apply(userId);
                List<ShippingAddress> shippingAddresses = propagate(callable);
                return new Addresses(type, shippingAddresses);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            if (isConstraintViolationException(e)) {
                throwValidationException(e);
            }
            throwProcessBizException(e);
        }
        return null;
    }

    public List<ShippingAddress> getTaoBaoShippingAddresses(Long userId) throws ProcessBizException {
        UserBaseRequest request = new UserBaseRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<List<TaobaoShippingAddressBo>> response = taobaoDataDubboService.queryUserShippingAddress(request);
        return data(response, result ->
                result == null ?
                        null :
                        result.stream().
                                map(v -> new ShippingAddress(
                                        v.getFullName(),
                                        v.getMobile(),
                                        v.getAddressDetail(),
                                        v.getStatus(),
                                        v.getOrderTime())).
                                collect(Collectors.toList()));
    }

    public List<ShippingAddress> getJDShippingAddresses(Long userId) throws ProcessBizException {
        ReceiveRequest request = new ReceiveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId == null ? null : String.valueOf(userId));
        DubboResponse<ReceiveResponse> response = jdDataDubboService.selectReceiveInfos(request);
        return data(response, result ->
                (result == null || result.getList() == null) ?
                        null :
                        result.getList().stream().
                                map(v -> new ShippingAddress(
                                    v.getName(),
                                    v.getPhone(),
                                    v.getFullAddress(),
                                    v.isDefault(),
                                    v.getOrderTime())).
                                collect(Collectors.toList()));
    }
}
