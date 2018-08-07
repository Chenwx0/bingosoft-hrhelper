package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.service.MailSendService;
import bingosoft.hrhelper.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chenwx
 * @功能描述 邮件展示控制类
 * @创建时间 2018-08-04 15:06:06
 */
@RestController
@RequestMapping("mail")
public class MailController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailService mailService;
    @Autowired
    MailSendService mailSendService;

    /**
     * 获取邮件列表
     * @param pageNum
     * @param pageSize
     * @param status
     * @param recipient
     * @param operationId
     * @param startTime
     * @param endTime
     * @return 查询结果对象
     */
    @GetMapping("/list")
    public Result pageQueryMailList(Integer pageNum, Integer pageSize, Integer status, String recipient, String operationId, String startTime, String endTime){

       return mailService.pageQueryMailList(pageNum, pageSize, status, recipient, operationId, startTime, endTime);
    }

    /**
     * 删除邮件
     * @param mailId
     * @return 操作结果
     */
    @DeleteMapping("/del")
    public Result deleteMail(Integer status, String mailId){
        return mailService.deleteMail(status, mailId);
    }

    /**
     * 批量删除邮件
     * @param mailIds
     * @return 操作结果
     */
    @DeleteMapping("/patchDel")
    public Result patchDeleteMail(Integer status, String[] mailIds){
        return mailService.patchDeleteMail(status, mailIds);
    }

    /**
     * 取消发送邮件
     * @param id
     */
    @GetMapping(path = "/cancelSend")
    public void cancelSendMail(String id){
    	mailSendService.cancelSendMail(id);
    }
}
