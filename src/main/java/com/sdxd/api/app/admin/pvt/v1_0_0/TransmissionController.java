package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.google.common.collect.Lists;
import com.sdxd.api.service.TransmissionService;
import com.sdxd.api.vo.transport.IndexedTransmission;
import com.sdxd.api.vo.transport.IndexedTransmissionResult;
import com.sdxd.common.web.trace.HideBody;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.file.dubbo.response.UploadFileResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.isSuccessfulResponse;
import static com.sdxd.common.web.vo.RestResponse.ok;
import static com.sdxd.framework.constant.Constants.System.SYSTEM_ERROR_CODE;
import static com.sdxd.framework.constant.Constants.System.SYSTEM_ERROR_MSG;


/**
 * Created by liuam on 2017/5/4.
 */
@Api(value = "Transmission-API", produces = "application/json")
@Controller
@RequestMapping(value = "/comp", produces = "application/json")
@HideBody
public class TransmissionController {

    private static final Logger log = LoggerFactory.getLogger(Swagger2Controller.class);

    @Autowired
    private TransmissionService transmissionService;

    @ApiOperation(value = "上传多个文件", notes = "上传多个文件")
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
    @RequestMapping(value = "/transmissions", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<IndexedTransmissionResult>> uploadFiles(
            @Valid @RequestBody List<IndexedTransmission> transmissions
    ) {
        List<IndexedTransmissionResult> results = Lists.newArrayList();
        for (IndexedTransmission transmission : transmissions) {
            log.debug("Uploading transmission info: {} - {} - {}",
                    transmission.getName(), transmission.getSize(), transmission.getPrivacy());

            DubboResponse<UploadFileResponse> response = transmissionService.transmit(transmission);
            IndexedTransmissionResult result = new IndexedTransmissionResult();
            if (!isSuccessfulResponse(response)) {
                result.setError(response == null ? SYSTEM_ERROR_CODE : response.getError());
                result.setMessage(response == null ? SYSTEM_ERROR_MSG : response.getMsg());
            }
            result.setIndex(transmission.getIndex());
            result.setResult(response.getData());
            results.add(result);
        }
        return ok(results);
    }
}