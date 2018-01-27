package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.AgreementTemplateService;
import com.sdxd.api.vo.cms.AgreementTemplate;
import com.sdxd.api.vo.cms.IndexedAgreementTemplate;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;
import java.util.stream.Collectors;

import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/5     melvin                 Created
 */
@Api(value = "Agreement-API", produces = "application/json")
@Controller
@RequestMapping(value = "/agreement-templates", produces = "application/json")
public class AgreementController {

    @Autowired
    private AgreementTemplateService agreementTemplateService;

    @ApiOperation(value = "获取协议模板列表", notes = "获取协议模板列表")
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
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<IndexedAgreementTemplate>> search(
            @Valid @BeanParam PageParameter query) {
        try {
            Integer pageNo = query == null ? null : query.getPageNo();
            Integer pageSize = query == null ? null : query.getPageSize();
            PaginationSupport<IndexedAgreementTemplate> page =
                    forValue(new PaginationSupport<IndexedAgreementTemplate>(null, 0)).
                            parallel(() -> agreementTemplateService.find(pageNo, pageSize), (value, result) -> {
                                if (result != null && result.getList() != null) {
                                    List<IndexedAgreementTemplate> list =
                                            result.getList().stream().
                                                    map(IndexedAgreementTemplate::new).
                                                    collect(Collectors.toList());
                                    value.setList(list);
                                }
                            }).
                            parallel(() -> agreementTemplateService.count(), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "创建协议模板", notes = "创建协议模板")
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
    public RestResponse<Boolean> create(
            @Valid @RequestBody AgreementTemplate template) {
        try {
            Boolean result = agreementTemplateService.create(template);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "删除协议模板", notes = "删除协议模板")
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse<Boolean> delete(@PathVariable("id") Integer id) {
        try {
            Boolean result = agreementTemplateService.delete(id);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
