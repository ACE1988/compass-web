package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.PublicResourceService;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.api.vo.resource.DefaultProduct;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

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
 * 17/1/9     melvin                 Created
 */
@Api(value = "Default-Product-API", produces = "application/json")
@Controller
@RequestMapping(value = "/default-product", produces = "application/json")
public class DefaultProductController {

    @Autowired
    private PublicResourceService publicResourceService;

    @ApiOperation(value = "保存默认产品信息", notes = "保存默认产品信息")
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
    @ResourcePush(pushKey = "DEFAULT_PRODUCT_PUSH_URL", accessKey = "DEFAULT_PRODUCT_ACCESS_URL")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public RestResponse<DefaultProduct> save(
            @Valid @RequestBody DefaultProduct product) {
        try {
            publicResourceService.saveVariable("PRODUCT_NAME", product.getProductName());
            publicResourceService.saveVariable("PRODUCT_AMOUNT", product.getProductAmount());
            publicResourceService.saveVariable("PRODUCT_DESCRIPTION", product.getProductDescription());
            return ok();
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
