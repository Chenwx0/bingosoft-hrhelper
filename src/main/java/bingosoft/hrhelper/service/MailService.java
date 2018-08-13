package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import leap.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @创建人 chenwx
 * @功能描述 邮件前端交互业务服务类
 * @创建时间 2018-08-04 15:59:59
 */
@Service
public class MailService{

    private static final String ID_NULL = "邮件ID不能为空";
    private static final String OPERATION_ID_NULL = "业务类别ID不能为空";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailMapper mailMapper;

    @Autowired
    AlreadySendMailMapper aMailMapper;

    /**
     * @param params(status 0-待发送 1-已发送 isSpecial 0-不需审批，其他-需审批)
     * @return
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
        }catch (Exception e){
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
    public Result deleteMail(Integer status, String mailId){
        Result result = new Result();
        // 参数验证
        if (mailId == null || mailId.length()==0){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        if (status == null){
            status = 1;
        }
        if (status == 0){
            try{
                int res = mailMapper.deleteByPrimaryKey(mailId);
                if (res>0){
                    result.setMessage(TipMessage.DELETE_SUCCESS);
                }else{
                    result.setSuccess(false);
                    result.setMessage(TipMessage.NO_DATA);
                }
            } catch (Exception e){
                result.setSuccess(false);
                result.setMessage(TipMessage.DELETE_FAIL);
                logger.error(TipMessage.DELETE_FAIL,e);
            }
        }else if(status == 1){
            try{
                int res = aMailMapper.deleteByPrimaryKey(mailId);
                if (res>0){
                    result.setMessage(TipMessage.DELETE_SUCCESS);
                }else{
                    result.setSuccess(false);
                    result.setMessage(TipMessage.NO_DATA);
                }
            } catch (Exception e){
                result.setSuccess(false);
                result.setMessage(TipMessage.DELETE_FAIL);
                logger.error(TipMessage.DELETE_FAIL,e);
            }
        }


        return result;
    }

    /**
     * 批量删除邮件
     * @param mailIds
     * @return 操作结果
     */
    @Transactional
    public Result patchDeleteMail(Integer status, String[] mailIds){
        Result result = new Result();
        // 参数验证
        if (mailIds == null || mailIds.length == 0){
            result.setSuccess(false);
            result.setMessage(ID_NULL);
            return result;
        }
        if(status == null){
            status = 1;
        }
        if (status == 0){
            try{
                for (String mailId: mailIds) {
                    mailMapper.deleteByPrimaryKey(mailId);
                }
                result.setMessage(TipMessage.DELETE_SUCCESS);
            } catch (Exception e){
                result.setSuccess(false);
                result.setMessage(TipMessage.DELETE_FAIL);
                logger.error(TipMessage.DELETE_FAIL,e);
            }
        }else if (status == 1){
            try{
                for (String mailId: mailIds) {
                    aMailMapper.deleteByPrimaryKey(mailId);
                }
                result.setMessage(TipMessage.DELETE_SUCCESS);
            } catch (Exception e){
                result.setSuccess(false);
                result.setMessage(TipMessage.DELETE_FAIL);
                logger.error(TipMessage.DELETE_FAIL,e);
            }
        }

        return result;
    }
}
