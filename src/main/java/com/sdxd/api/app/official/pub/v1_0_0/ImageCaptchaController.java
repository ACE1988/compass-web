package com.sdxd.api.app.official.pub.v1_0_0;

import com.sdxd.api.vo.CaptchaAnswer;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.captcha.CaptchaImage;
import com.sdxd.common.web.captcha.ICaptchaGenerator;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.io.IOException;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.official.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/3     melvin                 Created
 */
@Api(value = "Image-Captcha-API", produces = "application/json")
@Controller
@RequestMapping(value = "/captcha", produces = "application/json")
public class ImageCaptchaController {

    @Autowired
    private ICaptchaGenerator captchaGenerator;

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse createCaptcha() {
        try {
            CaptchaImage image = captchaGenerator.question(BillNoUtils.GenerateBillNo(), null);
            return ok(image);
        } catch (IOException e) {
            return ok();
        }
    }

    @ApiOperation(value = "验证图片验证码", notes = "验证图片验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse verifyCaptcha(@Valid @BeanParam CaptchaAnswer answer) {
        boolean valid = captchaGenerator.answer(answer.getId(), answer.getAnswer());
        return ok(valid);
    }
}
