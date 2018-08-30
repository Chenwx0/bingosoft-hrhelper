package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @创建人 chenwx
 * @功能描述 附件业务控制类
 * @创建时间 2018-08-30
 */
public class AttachmetController {

    @Autowired
    AttachmentService attachmentService;

    /**
     * 查询附件
     * @param type
     * @param id
     * @return
     */
    @GetMapping
    public Result queryAttachment(String type, String id){

        Result result = attachmentService.queryAttachment(type, id);
        return result;
    }

    /**
     * 添加附件
     * @param type
     * @param id
     * @param file
     * @return
     */
    @PostMapping
    public Result addAttachment(String type, String id, MultipartFile file){

        Result result = attachmentService.addAttachment(type, id, file);
        return result;
    }

    /**
     * 删除附件
     * @param attachmentId
     * @return
     */
    @DeleteMapping
    public Result deleteAttachment(String attachmentId){

        Result result = attachmentService.deleteAttachment(attachmentId);
        return result;
    }
}
