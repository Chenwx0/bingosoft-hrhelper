package bingosoft.hrhelper.controller;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bingosoft.hrhelper.mapper.UserMapper;
import bingosoft.hrhelper.model.Rule;
import bingosoft.hrhelper.service.RuleService;

/**
 * @创建人 zhangyx
 * @功能描述
 * @创建时间 2018-07-26 10:54:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Controller
public class RuleController {
	Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RuleService ruleService;
    
    @GetMapping(path = "/addRule")
    public void addRule(Rule rule){
    	ruleService.addRule(rule);
    }
    
    @GetMapping(path = "/deleteRule")
    public void deleteRule(String rule_id){
    	ruleService.deleteRule(rule_id);
    }
    
    @GetMapping(path = "/updateRule")
    public void updateRule(Rule rule){
    	ruleService.updateRule(rule);
    }
}
