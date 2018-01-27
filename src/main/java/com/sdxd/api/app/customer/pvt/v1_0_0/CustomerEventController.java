package com.sdxd.api.app.customer.pvt.v1_0_0;

import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.service.CustomerEventAccess;
import com.sdxd.api.service.CustomerEventService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.customer.CustomerEvent;
import com.sdxd.api.vo.customer.CustomerEventQuery;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.rest.MultiViewResponseBody;
import com.sdxd.common.web.rest.View;
import com.sdxd.common.web.security.Restrict;
import com.sdxd.common.web.security.Subject;
import com.sdxd.common.web.util.Pages;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.customer.dubbo.api.response.CustomerEventsCreateResponse;
import com.sdxd.customer.dubbo.api.response.CustomerEventsInfo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.user.api.response.UserInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import static com.sdxd.api.vo.CustomerErrors.INVALID_INCOMING_PHONE;
import static com.sdxd.common.web.vo.PagingResponse.paging;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.customer.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
@Api(value = "Customer-Event-API", produces = "application/json")
@Controller
@RequestMapping(value = "/customer-events", produces = "application/json")
public class CustomerEventController {

    @Autowired
    private CustomerEventService customerEventService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "查询/导出客户事件", notes = "查询/导出客户事件")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @Restrict
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    @MultiViewResponseBody(view = @View(name = "customer-event-view", model = "events"))
    public RestResponse<PaginationSupport<CustomerEventsInfo>> search(
            @ContextParam(value = "subject") Subject subject, @Valid @BeanParam CustomerEventQuery query) {
        if (StringUtils.isBlank(query.getIncomingPhone())) {
            CustomerEventAccess access = CustomerEventAccess.of(subject);
            access.restrict(query::setReceptionistId);
        }
        Pages<CustomerEventsInfo> pages = new Pages<>(100, (pageNo, pageSize) -> customerEventService.search(query, pageNo, pageSize));
        return paging(pages, query.getPageNo(), query.getPageSize());
    }

    @ApiOperation(value = "创建客户事件", notes = "创建客户事件")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<CustomerEventsCreateResponse> create(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody CustomerEvent event) {
        UserInfo customer = null;
        try {
            String userId = event.getUserId();
            customer = StringUtils.isBlank(userId) ? null : profileService.getUserInfo(Long.parseLong(event.getUserId()));
            if (customer != null && !customer.getPhone().equals(event.getIncomingPhone())) {
                return fail(INVALID_INCOMING_PHONE);
            }
        } catch (ProcessBizException e) {
            if (!"010103".equals(e.getCode().getCode())) {
                return e.toResult();
            }
        }
        try {
            AdminUserResponse admin = adminService.getAdminById(operatorId);
            CustomerEventsCreateResponse result = customerEventService.createEvent(event, customer, admin, operatorId);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
