package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.mapper.AttachmentMapper;
import bingosoft.hrhelper.model.Attachment;
import leap.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @创建人 chenwx
 * @功能描述 附件业务服务类
 * @创建时间 2018-08-30
 */
@Service
public class AttachmentService {

    @Autowired
    AttachmentMapper attachmentMapper;
    @Autowired
    UploadService uploadService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ATTACHMENT_ID_NULL = "附件ID不能为空";
    private static final String ID_NULL = "ID不能为空";
    private static final String ATTACHMENT_TYPE_NULL = "附件类别为空";
    private static final String ATTACHMENT_TYPE_WRONG = "附件类别有误";
    private static final String NO_ATTACHMENT = "附件不存在";

    /**
     * 根据邮件id或规则id获取附件
     * @param type
     * @param id
     * @return 查询结果
     */
    public Result<List<Attachment>> queryAttachment(String type, String id){

        Result<List<Attachment>> result = new Result<>();

        // 参数校验
        if (Strings.isEmpty(id)){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        if (Strings.isEmpty(type)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_TYPE_NULL);
            return result;
        }
        if (!"mail".equals(type) && !"rule".equals(type)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_TYPE_WRONG);
            return result;
        }

        try {
            List<Attachment> list = attachmentMapper.listAttachment(type, id);
            if (list.size()>0){
                result.setResultEntity(list);
            }else{
                result.setSuccess(false);
                result.setMessage(TipMessage.NO_DATA);
            }
        } catch (SQLException e) {
            logger.error(TipMessage.QUERY_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
        }

        return result;
    }

    /**
     * 添加附件
     * @param type
     * @param id
     * @param file
     * @return
     */
    public Result addAttachment(String type, String id, MultipartFile file){

        Result result = new Result();

        // 获取文件名
        String fileName = file.getOriginalFilename();

        // 参数校验
        if (Strings.isEmpty(id)){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        if (Strings.isEmpty(type)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_TYPE_NULL);
            return result;
        }
        if (!"mail".equals(type) && !"rule".equals(type)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_TYPE_WRONG);
            return result;
        }

        // 文件上传
        Result<String> res = uploadService.upLoadFile(file);
        if (!res.isSuccess()){
            result.setSuccess(false);
            result.setMessage(res.getMessage());
            return result;
        }
        // 构建附件实体
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(UUID.randomUUID().toString());
        attachment.setAttachmentName(fileName);
        attachment.setAttachmentPath(res.getResultEntity());
        attachment.setCreateBy(CurrentUser.getUserId());
        attachment.setCreateTime(new Date());
        if("mail".equals(type)){
            attachment.setMailId(id);
        }else if ("rule".equals(type)){
            attachment.setRuleId(id);
        }
        // 插入数据
        try {
            attachmentMapper.insert(attachment);
        } catch (SQLException e) {
            logger.error(TipMessage.CREATE_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.CREATE_FAIL);
        }

        return result;
    }

    /**
     * 删除附件
     * @param attachmentId
     * @return
     */
    public Result deleteAttachment(String attachmentId){
        Result result = new Result();

        // 参数校验
        if (Strings.isEmpty(attachmentId)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_ID_NULL);
            return result;
        }

        try{
            // 获取附件信息
            Attachment attachment = attachmentMapper.selectByPrimaryKey(attachmentId);
            if (attachment == null){
                result.setSuccess(false);
                result.setMessage(NO_ATTACHMENT);
                return result;
            }

            // 删除附件
            File file = new File(attachment.getAttachmentPath());
            file.delete();

            // 删除附件信息
            int res = attachmentMapper.deleteByPrimaryKey(attachmentId);
            if (res > 0){
                result.setMessage(TipMessage.DELETE_SUCCESS);
            }else {
                result.setSuccess(false);
                result.setMessage(TipMessage.NO_DATA_DELETE);
            }
        }catch (SQLException e){
            logger.error(TipMessage.DELETE_FAIL,e);
            result.setSuccess(false);
            result.setMessage(TipMessage.DELETE_FAIL);
        }

        return result;
    }
}
