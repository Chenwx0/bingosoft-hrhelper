package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.model.Rule;
import bingosoft.hrhelper.service.ModelService;
import bingosoft.hrhelper.service.RuleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 zhangyx
 * @功能描述 规则管理业务控制类
 * @创建时间 2018-07-26 10:54:54
 */
@RestController
@RequestMapping(path = "/rule")
public class RuleController {
	Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RuleService ruleService;
    @Autowired
    ModelService modelService;

    @GetMapping(path = "/add")
    public void addRule(Rule rule){
    	ruleService.addRule(rule);
    	
    }

    /**
     * 删除规则
     * @param ruleId
     * @return 操作结果
     */
    @DeleteMapping(path = "/del")
    public Result deleteRule(String ruleId){
    	Result result = ruleService.deleteRule(ruleId);
    	return result;
    }
    
    @GetMapping(path = "/update")
    public void updateRule(Rule rule){
    	ruleService.updateRule(rule);
    }
}
