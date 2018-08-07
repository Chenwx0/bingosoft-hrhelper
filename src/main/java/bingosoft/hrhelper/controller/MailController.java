package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.service.MailService;
import com.github.pagehelper.PageInfo;
import leap.lang.json.JSON;
import leap.web.api.mvc.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
