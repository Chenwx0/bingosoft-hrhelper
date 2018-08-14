package bingosoft.hrhelper.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bingosoft.hrhelper.common.*;

/**
 * @创建人 zhangyx
 * @功能描述 附件上传控制类
 * @创建时间 2018-08-04 15:06:06
 */
@RestController
@RequestMapping("file")
public class FileUploadController {
	    /**
	     * 获取邮件列表
	     * @param params
	     * @return 查询结果对象
	     */
	    @RequestMapping("/upload")
	    public String upload(MultipartFile file,String attachmentHref,String uploadAddress){
	        return FileUploadUtil.fileUpload(file,attachmentHref,uploadAddress);
	    }
}
