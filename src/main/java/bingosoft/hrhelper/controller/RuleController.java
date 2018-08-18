package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.form.RuleDetailForm;
import bingosoft.hrhelper.model.Rule;
import bingosoft.hrhelper.service.ModelService;
import bingosoft.hrhelper.service.RuleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @创建人 zhangyx
 * @功能描述 规则管理业务控制类
 * @创建时间 2018-07-26 10:54:54
 */
@RestController
@RequestMapping(path = "rule")
@CrossOrigin
public class RuleController {
	Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RuleService ruleService;
    @Autowired
    ModelService modelService;

    /**
     * 获取规则列表
     * @param operationId
     * @return 规则列表
     */
    @GetMapping("/list")
    public Result listRule(String operationId){
        Result result = ruleService.listRule(operationId);
        return result;
    }

    /**
     * 获取规则详情
     * @param ruleId
     * @return 规则详情
     */
    @GetMapping("/get")
    public Result getRuleDetail(String ruleId){
        Result result = ruleService.getRuleDetail(ruleId);
        return result;
    }

    /**
     * 添加规则
     * @param ruleDetailForm
     * @return 操作结果
     */
    @PostMapping
    public Result addRule(RuleDetailForm ruleDetailForm){
        Result result = ruleService.addRule(ruleDetailForm);
        return result;
    }

    /**
     * 删除规则
     * @param ruleId
     * @return 操作结果
     */
    @DeleteMapping("/del")
    public Result deleteRule(String ruleId){
    	Result result = ruleService.deleteRule(ruleId);
    	return result;
    }
    
    /**
     * 更新规则
     * @param ruleDetailForm
     * @return 操作结果
     */
    @PatchMapping
    public Result updateRule(RuleDetailForm ruleDetailForm){
        Result result = ruleService.updateRule(ruleDetailForm);
        return result;
    }
}
