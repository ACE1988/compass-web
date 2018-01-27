package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.PublicResourceService;
import com.sdxd.api.util.ResourcePush;
import com.sdxd.api.vo.resource.AppVersion;
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

import static com.sdxd.admin.dubbo.api.constants.SystemVariableCode.*;
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
@Api(value = "App-Version-API", produces = "application/json")
@Controller
@RequestMapping(value = "/app-versions", produces = "application/json")
public class AppVersionController {

    @Autowired
    private PublicResourceService publicResourceService;

    @ApiOperation(value = "保存IOS版本信息", notes = "保存IOS版本信息")
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
    @ResourcePush(pushKey = "APP_VERSION_PUSH_URL", accessKey = "APP_VERSION_ACCESS_URL")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public RestResponse<AppVersion> save(
            @Valid @RequestBody AppVersion version) {
        try {
            publicResourceService.saveVariable(VERSION_CODE_ANDROID.getCode(), version.getAppVersionCode());
            publicResourceService.saveVariable(ANDROID_APPINSTALL_LINK.getCode(), version.getAndroidAppInstallLink());
            publicResourceService.saveVariable(ANDROID_FORCE_UPGRADE.getCode(), version.getAndroidForceUpgrade());

            publicResourceService.saveVariable("XZC_VERSION_CODE_IOS", version.getIosVersionCodeXzc());
            publicResourceService.saveVariable("JZ_VERSION_CODE_IOS", version.getIosVersionCodeJz());
            publicResourceService.saveVariable("XZC_IOS_FORCE_UPGRADE", version.getIosForceUpgradeXzc());
            publicResourceService.saveVariable("JZ_IOS_FORCE_UPGRADE", version.getIosForceUpgradeJz());

            publicResourceService.saveVariable("SIGNATURE_DEBUG", version.getAppDebugEnabled());
            return ok();
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
