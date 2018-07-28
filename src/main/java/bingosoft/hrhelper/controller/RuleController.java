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
    
    
    @Test
    public void test(){
    	Rule rule = new Rule();
    	rule.setId(UUID.randomUUID().toString());
    	rule.setEntryDistanceY(5);
    	rule.setEntryDistanceM(6);
    	rule.setEntryDistanceD(31);
    	rule.setSendingHourofday(9);
    	rule.setSendingMinofhour(30);
    	
    	ruleService.addRule(rule);
       System.out.println(ruleService);
    }
    
    @GetMapping(path = "/test")
    public String test1(){
       return "测试成功";
    };
}
