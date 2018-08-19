package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.MailListForm;
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

    /**
     * 获取邮件列表
     * @param params(status 0-待发送 1-已发送 isSpecial 0-不需审批，其他-需审批)
     * @return 查询结果对象
     */
    public Result<PageInfo<MailListForm>> pageQueryMailList(Map<String,String> params){

        Result<PageInfo<MailListForm>> result = new Result<>();

        // 参数验证
        String operationId = params.get("operationId");
        if (Strings.isEmpty(operationId)){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }
        int pageNum = 1;
        int pageSize = 20;
        int status = 1;
        int isSpecial = 0;
        try{
            int pageNumTemp = Integer.parseInt(params.get("pageNum"));
            int pageSizeTemp = Integer.parseInt(params.get("pageSize"));
            int statusTemp = Integer.parseInt(params.get("status"));
            int isSpecialTemp = Integer.parseInt(params.get("isSpecial"));
            if (pageNumTemp > 0){
                pageNum = pageNumTemp;
            }
            if (pageSize > 0){
                pageSize = pageSizeTemp;
            }
            if (status == 0 || status == 1){
                status = statusTemp;
            }
            isSpecial = isSpecialTemp;
        }catch (NumberFormatException e){
            logger.error(TipMessage.PARAM_ILLEGAL_CHAR,e);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<MailListForm> mailListForms = new ArrayList<>();
        try{
            if (status == 0){
                mailListForms = mailMapper.selectListNotSend(params);
            }else if(status == 1 && isSpecial == 0){
                mailListForms = mailMapper.selectListsentNoApprove(params);
            }else if(status == 1 && isSpecial != 0){
                mailListForms = mailMapper.selectListSentApprove(params);
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
}
