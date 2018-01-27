package com.sdxd.api.app.admin.pub.v1_0_0;

import com.sdxd.common.web.rest.SignatureIgnore;
import com.sdxd.common.web.util.ClassUtil;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pub.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/10    wenzhou.xu              Created
 */
@SignatureIgnore
@Api(value = "Enum-API", produces = "application/json")
@Controller
@RequestMapping(value = "/enum", produces = "application/json")
public class EnumController {

    @ApiOperation(value = "枚举查询接口", notes = "枚举查询接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/search-enum", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> searchEnum(@ApiParam(value = "枚举路径") @QueryParam(value = "enum_path") @RequestParam String enumPath) {
        return ok(ClassUtil.getOptions(enumPath));
    }

}
