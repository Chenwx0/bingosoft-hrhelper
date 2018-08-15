package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.service.MailSendService;
import bingosoft.hrhelper.service.MailService;

import leap.web.annotation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * @param params
     * @return 查询结果对象
     */
    @RequestMapping("/list")
    public Result pageQueryMailList(@RequestBody Map<String,String> params){
        Result result = mailService.pageQueryMailList(params);
        return result;
    }

    /**
     * 删除邮件
     * @param mailId
     * @return 操作结果
     */
    @DeleteMapping("/del")
    public Result deleteMail(String mailId){
        Result result = mailService.deleteMail(mailId);
        return result;
    }

    /**
     * 批量删除邮件
     * @param mailIds
     * @return 操作结果
     */
    @DeleteMapping("/patch_del")
    public Result patchDeleteMail(String[] mailIds){
        Result result = mailService.patchDeleteMail(mailIds);
        return result;
    }

    /**
     * 更新待发送邮件信息
     * @param mail
     * @return 更新结果
     */
    @PatchMapping
    public Result updateMail(@RequestBody Mail mail){
        Result result = mailService.updateMail(mail);
        return result;
    }

    /**
     * 取消发送邮件
     * @param mailId
     */
    @GetMapping(path = "/cancel")
    public Result cancelSendMail(String mailId){
    	Result result = mailService.cancelSend(mailId);
    	return result;
    }

    /**
     * 批量取消发送邮件
     * @param mailIds
     */
    @GetMapping(path = "/patch_cancel")
    public Result patchCancelSendMail(String[] mailIds){
        Result result = mailService.patchCancelSend(mailIds);
        return result;
    }
}
