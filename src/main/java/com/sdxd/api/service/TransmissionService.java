package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.transport.Transmission;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.file.dubbo.api.FileService;
import com.sdxd.file.dubbo.request.UploadFileRequest;
import com.sdxd.file.dubbo.response.UploadFileResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

/**
 * Created by liuam on 2017/5/4.
 */
@Service
public class TransmissionService {
    @Reference(version = "1.0.0")
    private FileService fileService;

    public DubboResponse<UploadFileResponse> transmit(Transmission transmission) {
        UploadFileRequest request = new UploadFileRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setFileBase64(transmission.getBase64Content());
        request.setFileContentType(transmission.getOriginType());
        request.setFileName(transmission.getName());
        request.setFileSize(transmission.getSize());
        request.setFilePrivacy(transmission.getPrivacy());

        DubboResponse<UploadFileResponse> response = fileService.uploadFile(request);
        return response;
    }
}
