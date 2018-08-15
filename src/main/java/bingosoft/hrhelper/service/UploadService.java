package bingosoft.hrhelper.service;


import bingosoft.hrhelper.common.FileUploadUtil;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @创建人 chenwx
 * @功能描述 文件上传服务类
 * @创建时间 2018-08-15 10:07:07
 */
@Service
public class UploadService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * 文件上传
     * @param file
     * @return 文件路径
     */
    public Result<String> upLoadFile(MultipartFile file){

        Result result = new Result();

        try {
            String filePath = fileUploadUtil.fileUpload(file);
            result.setResultEntity(filePath);
        } catch (IOException e) {
            logger.error(TipMessage.UPLOAD_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.UPLOAD_FAIL);
        }

        return result;
    }
}
