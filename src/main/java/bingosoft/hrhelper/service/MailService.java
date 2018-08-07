package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.mapper.MailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 chenwx
 * @功能描述 邮件前端交互业务服务类
 * @创建时间 2018-08-04 15:59:59
 */
@Service
public class MailService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailMapper mailMapper;

    /**
     * 分页查询邮件列表
     * @param pageNum
     * @param pageSize
     * @param status
     * @param recipient
     * @param operationId
     * @param startTime
     * @param endTime
     * @return 邮件分页对象
     */
    public Result<PageInfo<MailListForm>> pageQueryMailList(Integer pageNum, Integer pageSize, Integer status, String recipient, String operationId, String startTime, String endTime){

        Result<PageInfo<MailListForm>> result = new Result<>();
        // 参数验证
        if (pageNum == null || pageNum == 0){
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 20;
        }
        if (status == null || status == 0){
            status = 0;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<MailListForm> mailListForms = new ArrayList<>();
        try{
            if (status == 0){
                mailListForms = mailMapper.selectNotSendMailList(recipient, operationId, startTime, endTime);
            }else if(status == 1){
                mailListForms = mailMapper.selectSentMailList(recipient, operationId, startTime, endTime);
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
}
