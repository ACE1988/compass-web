
package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.service.RepaymentOfflineDetailService;
import com.sdxd.api.util.AccountCodeBatchImportOfflineUtil;
import com.sdxd.api.util.Map_AccountCode;
import com.sdxd.api.vo.credit.AccountCodeDetail;
import com.sdxd.api.vo.credit.UpLoadStatus;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.creditline.api.CreditLineDubboService;
import com.sdxd.data.dubbo.api.ControlCodeDubboService;
import com.sdxd.data.dubbo.api.response.pojo.ControlCodeBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.user.api.UserControlCodeService;
import com.sdxd.user.api.request.ControlCodeRequest;
import com.sdxd.user.api.request.UserBaseRequest;
import com.sdxd.user.api.response.ControlCodeResponse;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.sdxd.common.web.vo.RestResponse.ok;

;


@Api(value = "Account-Code-API", produces = "application/json")
@Controller
@RequestMapping(value = "/account-code")
public class AccountCodeController {

    private static final Logger log = LoggerFactory.getLogger(AccountCodeController.class);

    @Autowired
    private RepaymentOfflineDetailService repaymentOfflineDetailService;
    @Reference(version = "1.0.0")
    private CreditLineDubboService creditLineDubboService;
    @Reference(version = "1.0.0")
    private UserControlCodeService userControlCodeService;//操作user
    @Reference(version = "1.0.0")
    private ControlCodeDubboService controlCodeDubboService;

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "账户码批量上传", notes = "账户码批量上传")
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
    @RequestMapping(value = "/batch-import", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<UpLoadStatus> batchImport(@ContextParam(value = "principal") String operatorId, HttpServletRequest request) {
        UpLoadStatus upLoadStatus = new UpLoadStatus();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = null;
        file = multipartRequest.getFile("excel_file");// 获取上传文件名
        if (null == file) {
            //判断file是否为null
            upLoadStatus.addError("上传文件未找到");
            return ok(upLoadStatus);
        }
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("", e);
            return ok(upLoadStatus);
        }
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<AccountCodeDetail> list = new AccountCodeBatchImportOfflineUtil(inputStream, prefix).parse();
        if (null == list) {
            upLoadStatus.addError("文件解析失败");
            return ok(upLoadStatus);
        }
        //--调用dubbo
        for (AccountCodeDetail accountCodeDetail : list) {
            //查询 usaerId
            Long userId = null;
            if (StringUtils.isBlank(accountCodeDetail.getPhone())) {
                upLoadStatus.addError("手机号没填");
                break;
            }
            if (StringUtils.isBlank(accountCodeDetail.getAccountCode())) {
                upLoadStatus.addError("用户码没填");
                break;
            }
            try {
                userId = profileService.getUserId(accountCodeDetail.getPhone());
            } catch (Exception e) {
                log.info("【信审系统-用户码上传  --非法手机号】=========================》 列表：" + accountCodeDetail.getPhone());
            }
            if (null == userId) {
                upLoadStatus.addError(String.format("通过手机号码%s未找到用户", accountCodeDetail.getPhone()));
                continue;
            }
            //控制码请求
            ControlCodeRequest controlCodeRequest = new ControlCodeRequest();
            controlCodeRequest.setUserId(userId);
            controlCodeRequest.setControlCode(accountCodeDetail.getAccountCode());//用户码 --需要转换
            controlCodeRequest.setOperatorer(operatorId);//操作人
            controlCodeRequest.setAuthority(Map_AccountCode.get(accountCodeDetail.getAccountCode()));//权限
            controlCodeRequest.setRequestId(BillNoUtils.GenerateBillNo());
            //防止重复提交
            UserBaseRequest userBaseRequest = new UserBaseRequest();
            userBaseRequest.setUserId(userId);
            userBaseRequest.setRequestId(BillNoUtils.GenerateBillNo());
            //比较对象
            DubboResponse<Boolean> dubboBoolen;
            try {
                //根据userId查询这个用户的所以状态
                DubboResponse<List<ControlCodeResponse>> dubboResponse = userControlCodeService.selectControlCode(userBaseRequest);
                if (null != dubboResponse && null != dubboResponse.getData()) {
                    boolean flag = false;
                    for (ControlCodeResponse controlCodeResponse : dubboResponse.getData()) {
                        if (controlCodeResponse.getControlCode().equals(controlCodeRequest.getControlCode())) {
                            //如果用户码重复 , flag就变为true
                            upLoadStatus.addError(String.format("手机号%s状态码已存在%s",
                                    accountCodeDetail.getPhone(), controlCodeResponse.getControlCode()));
                            flag = true;
                            break;
                        }
                    }
                    //如果flag就变为false ， 意味着不重复，保存
                    if (false == flag) {
                        if (!StringUtils.isBlank(controlCodeRequest.getControlCode())) {
                            //如果账户码不为blank
                            dubboBoolen = userControlCodeService.saveControlCode(controlCodeRequest);
                            log.error("" + dubboBoolen);
                        }
                    }
                }
            } catch (Exception e) {
                log.info("【信审系统-用户码上传  --状态码保保存错误】=========================》 列表：" + accountCodeDetail.getPhone());
                upLoadStatus.addError(String.format("%s状态码保保存错误", accountCodeDetail.getAccountCode()));
                continue;
            }
        }
        return ok(upLoadStatus);
    }


    @ApiOperation(value = "查询账户码", notes = "查询账户码")
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
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<AccountCodeDetail>> query(@ApiParam(name = "phone", value = "手机号", required = false)
                                                                    @RequestParam(value = "phone", required = false) String phone,
                                                                    @BeanParam PageParameter page) {
        com.sdxd.data.dubbo.api.request.ControlCodeRequest controlCodeRequest
                = new com.sdxd.data.dubbo.api.request.ControlCodeRequest();
        controlCodeRequest.setCurrentPage(page.getPageNo());
        controlCodeRequest.setPageSize(page.getPageSize());
        if (!StringUtils.isBlank(phone)) {
            //如果不为空，就设置
            controlCodeRequest.setPhone(phone);
        }
        controlCodeRequest.setRequestId(BillNoUtils.GenerateBillNo());
        List<AccountCodeDetail> list = new ArrayList<>();
        DubboResponse<PaginationSupport<ControlCodeBo>> response;
        //返回数据为null的分页
        PaginationSupport<AccountCodeDetail> blankPaginationSupport
                = new PaginationSupport<>(list, -1);
        blankPaginationSupport.setCurrentIndex(page.getPageNo());
        blankPaginationSupport.setPageSize(page.getPageSize());
        try {
            response = controlCodeDubboService.query(controlCodeRequest);
        } catch (Exception e) {
            log.error("", e);
            return ok(blankPaginationSupport);
        }
        //转换
        if (null != response.getData()) {
            if (null != response.getData().getList()) {
                //判断空指针
                for (ControlCodeBo controlCodeBo : response.getData().getList()) {
                    AccountCodeDetail accountCodeDetail = new AccountCodeDetail();
                    accountCodeDetail.setName(controlCodeBo.getName());
                    accountCodeDetail.setPhone(controlCodeBo.getPhone());
                    accountCodeDetail.setAnnotation(controlCodeBo.getDescribe());
                    accountCodeDetail.setAccountCode(controlCodeBo.getControlCode());
                    accountCodeDetail.setPriority(controlCodeBo.getAuthority());
                    list.add(accountCodeDetail);
                }
                //添加总条数controlCodeRequest
                int totalCount = controlCodeDubboService.count(controlCodeRequest).getData();
                PaginationSupport<AccountCodeDetail> paginationSupport
                        = new PaginationSupport<>(list, totalCount);
                paginationSupport.setCurrentIndex(page.getPageNo());//当前index就是gageNo
                paginationSupport.setPageSize(page.getPageSize());
                return ok(paginationSupport);
            }
        }
        return ok(blankPaginationSupport);
    }
}

