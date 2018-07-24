package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-20 10:54:54
 */
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;
    @GetMapping(path = "/test")
    public String test(){

       /* MailUtil mailUtil = new MailUtil();
        try {
            mailUtil.sendMail();

        }catch (ParamException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       logger.debug("debug");
       logger.info("info");
       logger.warn("warn");
       logger.error("error");
       return "测试成功";
    };


}
