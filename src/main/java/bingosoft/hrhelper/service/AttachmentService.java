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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
     * @param mailId
     * @param ruleId
     * @return 查询结果
     */
    public Result<List<Attachment>> queryAttachment(String mailId, String ruleId){

        Result<List<Attachment>> result = new Result<>();

        try {
            List<Attachment> list = attachmentMapper.listAttachment(mailId, ruleId);
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
     * 附件下载
     * @param attachmentId
     * @param resp
     * @return
     */
    public Result<Attachment> downloadAttachment(String attachmentId, HttpServletResponse resp){

        Result<Attachment> result = new Result<>();

        // 参数校验
        if (Strings.isEmpty(attachmentId)){
            result.setSuccess(false);
            result.setMessage(ATTACHMENT_ID_NULL);
            return result;
        }

        try {
            Attachment attachment = attachmentMapper.selectByPrimaryKey(attachmentId);
            if (attachment == null){
                result.setSuccess(false);
                result.setMessage(NO_ATTACHMENT);
                return result;
            }
            String filePath = attachment.getAttachmentPath();
            String fileName = attachment.getAttachmentName();
            File file = new File(filePath);
            // 构建响应对象
            resp.setContentType("application/octet-stream");
            resp.setCharacterEncoding("utf-8");
            resp.setContentLength((int) file.length());
            try {
                resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(TipMessage.DOWNLOAD_FAIL,e);
                result.setSuccess(false);
                result.setMessage(TipMessage.DOWNLOAD_FAIL );
                return result;
            }
            // 文件输出
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            try {
                OutputStream os = resp.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    os.write(buff, 0, i);
                    os.flush();
                }
            } catch (IOException e) {
                logger.error(TipMessage.DOWNLOAD_FAIL,e);
                result.setSuccess(false);
                result.setMessage(TipMessage.DOWNLOAD_FAIL);
                return result;
            } finally {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        } catch (SQLException e) {
            logger.error(TipMessage.DOWNLOAD_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.DOWNLOAD_FAIL);
        }

        return result;
    }
    /**
     * 添加附件
     * @param type
     * @param id
     * @param request
     * @return
     */
    public Result addAttachment(String type, String id, HttpServletRequest request){

        Result result = new Result();

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

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        for (MultipartFile file: files) {
            // 文件上传
            Result<String> res = uploadService.upLoadFile(file);
            if (!res.isSuccess()){
                result.setSuccess(false);
                result.setMessage(res.getMessage());
                return result;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 查询邮件或规则下是否存在同名附件
            Attachment attachmentOld = attachmentMapper.getAttachmentByName(type, id, fileName);
            if (attachmentOld != null){
                // 获取旧文件
                String attachmentOldPath = attachmentOld.getAttachmentPath();
                File oldFile = new File(attachmentOldPath);
                // 构建附件新实体
                attachmentOld.setAttachmentPath(res.getResultEntity());
                attachmentOld.setCreateBy(CurrentUser.getUserId());
                attachmentOld.setCreateTime(new Date());
                try {
                    // 修改数据
                    attachmentMapper.updateByPrimaryKey(attachmentOld);
                    // 删除旧文件
                    oldFile.delete();
                } catch (SQLException e) {
                    logger.error(TipMessage.UPDATE_FAIL, e);
                    result.setSuccess(false);
                    result.setMessage(TipMessage.UPDATE_FAIL);
                    return result;
                }
            }else{
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
                    return result;
                }
            }

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
