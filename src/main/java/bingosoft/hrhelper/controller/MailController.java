package bingosoft.hrhelper.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import bingosoft.hrhelper.model.Rule;
import bingosoft.hrhelper.service.MailSendService;
import bingosoft.hrhelper.service.RuleService;

/**
 * @创建人 zhangyx
 * @功能描述
 * @创建时间 2018-07-26 10:54:54
 */
@Controller
public class MailController {
	Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MailSendService mailSendService;
    
    
    @GetMapping(path = "/cancelSend")
    public void updateRule(String id){
    	mailSendService.cancelSendMail(id);
    }
}