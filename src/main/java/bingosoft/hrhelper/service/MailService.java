package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.ExcelUtil;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.form.MailQueryFilter;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.Mail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.activation.registries.MimeTypeFile;
import leap.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * @创建人 chenwx
 * @功能描述 邮件前端交互业务服务类
 * @创建时间 2018-08-04 15:59:59
 */
@Service
public class MailService{

    private static final String ID_NULL = "邮件ID不能为空";
    private static final String OPERATION_ID_NULL = "业务类别ID不能为空";
    private static final String CANCEL_SUCCESS = "取消成功";
    private static final String CANCEL_FAIL = "取消失败";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailMapper mailMapper;

    @Autowired
    AlreadySendMailMapper aMailMapper;
    @Autowired
    ExcelUtil excelUtil;

    /**
     * 分页获取邮件列表
     * @param mailQueryFilter (status 0-待发送 1-已发送)
     * @return 查询结果
     */
    public Result<PageInfo<MailListForm>> pageQueryMailList(MailQueryFilter mailQueryFilter){

        Result<PageInfo<MailListForm>> result = new Result<>();

        // 参数验证
        String operationId = mailQueryFilter.getOperationId();
        if (Strings.isEmpty(operationId)){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }
        Integer pageNum = mailQueryFilter.getPageNum();
        Integer pageSize = mailQueryFilter.getPageSize();
        Integer status = mailQueryFilter.getStatus();

        if (pageNum == null || pageNum <= 0){
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize = 20;
        }
        if (status == null || status != 0 && status != 1){
            status = 0;
        }

        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<MailListForm> mailListForms = new ArrayList<>();
        try{
            if (status == 0){
                mailListForms = mailMapper.selectListNotSend(mailQueryFilter);
            }else {
                mailListForms = mailMapper.selectListSent(mailQueryFilter);
            }
            PageInfo<MailListForm> pageInfo = new PageInfo<>(mailListForms);
            result.setResultEntity(pageInfo);
        }catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
            logger.error(TipMessage.QUERY_FAIL,e);
        }

        return result;
    }

    /**
     * 获取邮件列表
     * @param mailQueryFilter (status 0-待发送 1-已发送)
     * @return 查询结果
     */
    public Result<List<MailListForm>> queryMailList(MailQueryFilter mailQueryFilter){

        Result<List<MailListForm>> result = new Result<>();

        // 参数验证
        String operationId = mailQueryFilter.getOperationId();
        if (Strings.isEmpty(operationId)){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }
        Integer status = mailQueryFilter.getStatus();
        if (status == null || status != 0 && status != 1){
            status = 0;
        }

        List<MailListForm> mailListForms = new ArrayList<>();
        try{
            if (status == 0){
                mailListForms = mailMapper.selectListNotSend(mailQueryFilter);
            }else {
                mailListForms = mailMapper.selectListSent(mailQueryFilter);
            }
            result.setResultEntity(mailListForms);
        }catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
            logger.error(TipMessage.QUERY_FAIL,e);
        }

        return result;
    }


    /**
     * 删除邮件
     * @param mailId
     * @return 操作结果
     */
    public Result deleteMail(String mailId){
        Result result = new Result();
        // 参数验证
        if (mailId == null || mailId.length()==0){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        try{
            int res = aMailMapper.deleteByPrimaryKey(mailId);
            if (res>0){
                result.setMessage(TipMessage.DELETE_SUCCESS);
            }else{
                result.setSuccess(false);
                result.setMessage(TipMessage.NO_DATA_DELETE);
            }
        } catch (SQLException e){
            logger.error(TipMessage.DELETE_FAIL,e);
            result.setSuccess(false);
            result.setMessage(TipMessage.DELETE_FAIL);

        }
        return result;
    }

    /**
     * 批量删除邮件
     * @param mailIds
     * @return 操作结果
     */
    @Transactional
    public Result patchDeleteMail(String[] mailIds){
        Result result = new Result();
        // 参数验证
        if (mailIds == null || mailIds.length == 0){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }

        try{
            for (String mailId: mailIds) {
                aMailMapper.deleteByPrimaryKey(mailId);
            }
            result.setMessage(TipMessage.DELETE_SUCCESS);
        } catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.DELETE_FAIL);
            logger.error(TipMessage.DELETE_FAIL,e);
        }

        return result;
    }

    /**
     * 更新待发送邮件信息
     * @param mail
     * @return 更新结果
     */
    public Result updateMail(Mail mail){

        Result result = new Result();
        // 参数校验
        if (Strings.isEmpty(mail.getId())){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        mail.setUpdateBy(CurrentUser.getUserId());
        try{
            // 执行更新
            int res = mailMapper.updateByPrimaryKeySelective(mail);
            // 判断更新结果
            if (res > 0){
                result.setMessage(TipMessage.UPDATE_SUCCESS);
            }else{
                result.setMessage(TipMessage.NO_DATA_CHANGE);
            }
        }catch (SQLException e){
            logger.error(TipMessage.UPDATE_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.UPDATE_FAIL);
        }

        return result;
    }

    /**
     * 取消发送邮件
     * @param mailId
     * @return
     */
    public Result cancelSend(String mailId){

        Result result = new Result();
        // 参数校验
        if (Strings.isEmpty(mailId)){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }

        // 构造对象
        Mail mail = new Mail();
        mail.setId(mailId);
        mail.setStatus(0);

        // 执行更新
        try {
            mailMapper.updateByPrimaryKeySelective(mail);
            result.setMessage(CANCEL_SUCCESS);
        } catch (SQLException e) {
            logger.error(CANCEL_FAIL, e);
            result.setSuccess(false);
            result.setMessage(CANCEL_FAIL);
        }

        return result;
    }

    /**
     * 批量取消发送邮件
     * @param mailIds
     * @return 操作结果
     */
    @Transactional
    public Result patchCancelSend(String[] mailIds){

        Result result = new Result();
        // 参数校验
        if (mailIds == null || mailIds.length == 0){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }

        // 执行更新
        try {
            for (int i=0; i<mailIds.length; i++){
                // 构造对象
                Mail mail = new Mail();
                mail.setId(mailIds[i]);
                mail.setStatus(0);
                mailMapper.updateByPrimaryKeySelective(mail);
            }
            result.setMessage(CANCEL_SUCCESS);
        } catch (SQLException e) {
            logger.error(CANCEL_FAIL, e);
            result.setSuccess(false);
            result.setMessage(CANCEL_FAIL);
        }
        return result;
    }

    public Result addAttachment(MimeTypeFile file){

        Result result = new Result();

        return result;
    }

    /**
     * 邮件数据导出
     * @param mailQueryFilter
     * @param resp
     * @return
     */
    public Result exportMail(MailQueryFilter mailQueryFilter, HttpServletResponse resp){

        // 根据条件获取数据
        Result<List<MailListForm>> result = this.queryMailList(mailQueryFilter);
        if (result.isSuccess()){
            String operationId = mailQueryFilter.getOperationId();
            Integer status = mailQueryFilter.getStatus();
            // 构建exccel文件
            Result<String> res = excelUtil.buildExcel(operationId,status,result.getResultEntity());
            if (res.isSuccess()){
                // 获取文件路径
                String filePath = res.getResultEntity();
                // 构建文件对象
                File file = new File(filePath);
                // 构建文件名
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = simpleDateFormat.format(new Date());
                String fileName = "邮件数据导出-" + time + ".xls";
                // 构建响应对象
                resp.setContentType("application/vnd.ms-excel");
                resp.setCharacterEncoding("utf-8");
                resp.setContentLength((int) file.length());
                try {
                    resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(TipMessage.EXPORT_FAIL,e);
                    result.setSuccess(false);
                    result.setMessage(TipMessage.EXPORT_FAIL );
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
                    logger.error(TipMessage.EXPORT_FAIL,e);
                    result.setSuccess(false);
                    result.setMessage(TipMessage.EXPORT_FAIL);
                    return result;
                } finally {
                    try {
                        bis.close();
                        file.delete();
                    } catch (IOException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            }else {
                result.setSuccess(false);
                result.setMessage(res.getMessage());
            }

        }
        return result;
    }
}
