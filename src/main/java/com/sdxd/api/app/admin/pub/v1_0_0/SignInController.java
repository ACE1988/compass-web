package com.sdxd.api.app.admin.pub.v1_0_0;

import com.sdxd.api.vo.SignIn;
import com.sdxd.common.web.rest.SignatureIgnore;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.user.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/10/26     melvin                 Created
 */
@SignatureIgnore
@Api(value = "Sign-In-API", produces = "application/json")
@Controller
@RequestMapping(value = "/admin", produces = "application/json")
public class SignInController {

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse createToken(
            @ApiParam(value = "登录信息") @BeanParam SignIn signIn) {
        return ok();
    }
}
