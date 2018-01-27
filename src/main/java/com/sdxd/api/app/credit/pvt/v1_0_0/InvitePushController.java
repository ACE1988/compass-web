package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.sdxd.api.service.PushMessageService;
import com.sdxd.common.web.util.CommonFileUtil;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * Created by matteo on 2017/8/3.
 * 定向push功能
 */

@Api(value = "PUSH-API", produces = "application/json")
@Controller
@RequestMapping(value = "/invite", produces = "application/json")
public class InvitePushController {

    @Resource
    private PushMessageService pushMessageService;


    @ApiOperation(value = "定向PUSH", notes = "定向PUSH")
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
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @ResponseBody
    public synchronized RestResponse<List<String>> push(HttpServletRequest request,
                                                        @RequestParam(value = "title") String title,
                                                        @RequestParam(value = "content") String content
    ) throws IOException, ProcessBizException, ParseException {
        MultipartFile file = CommonFileUtil.getMultipartFile(request, "file");// 获取上传文件名
        pushMessageService.pushMessage(file, title, content);
        return ok();
    }


}
