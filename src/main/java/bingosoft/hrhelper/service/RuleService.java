package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.RuleDetailForm;
import bingosoft.hrhelper.form.RuleListForm;
import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.ModelMapper;
import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Employee;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Model;
import bingosoft.hrhelper.model.Rule;

import com.sun.xml.internal.bind.v2.model.core.ID;

import leap.lang.Strings;
import leap.orm.dao.Dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @创建人 zhangyx
 * @功能描述 规则计算服务
 * @创建时间 2018-08-03 14:08:08
 */
@Service
public class RuleService {

	private static final String ID_NULL = "规则ID不能为空";
	private static final String MODEL_ID_NULL = "模板ID不能为空";

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RuleMapper ruleMapper ;
	@Autowired
	MailMapper mailMapper;
	@Autowired
	ModelMapper modelMapper ;
	@Autowired
	EmployeeMapper employeelMapper ;
	MailProductService mps;

	/**
	 * 添加规则
	 * @param ruleDetailForm
	 * @return 操作结果
	 */
	@Transactional
	public Result addRule(RuleDetailForm ruleDetailForm){

		Result result = new Result();
		// 参数校验
		if (ruleDetailForm == null){
			result.setSuccess(false);
			result.setMessage(TipMessage.PARAM_NULL);
			return result;
		}
		// 构建规则模型
		Rule rule = new Rule();
		rule.setId(UUID.randomUUID().toString());
		rule.setRuleName(ruleDetailForm.getRuleName());
		rule.setRuleMethod(ruleDetailForm.getRuleMethod());
		rule.setDistanceY(ruleDetailForm.getDistanceY());
		rule.setDistanceM(ruleDetailForm.getDistanceM());
		rule.setDistanceD(ruleDetailForm.getDistanceD());
		rule.setSendingHourofday(ruleDetailForm.getSendingHourofday());
		rule.setSendingMinofhour(ruleDetailForm.getSendingMinofhour());
		rule.setOperationId(ruleDetailForm.getOperationId());
		rule.setCreateBy(CurrentUser.getUserId());
		rule.setCreateTime(new Date());
		//设置规则启用状态(0:未启用 1:启用)
		rule.setIsUse(0);
		if(rule.getRuleMethod().equals("1")){
			rule.setEntryDistance(caculateRule_1(rule)); //方法1：入职时长计算
		}else if(rule.getRuleMethod().equals("2")){
			rule.setEntryDistance(caculateRule_2(rule)); //方法2：距离合同续签日期计算
		}else{
			rule.setEntryDistance(caculateRule_3(rule)); //方法3：距离转正日期计算
		}
		// 构建模板模型
		Model model = new Model();
		model.setId(UUID.randomUUID().toString());
		model.setModelName(ruleDetailForm.getRuleName() + "模板");
		model.setModelContent(ruleDetailForm.getModelContent());
		model.setAttachmentHref(ruleDetailForm.getAttachmentHref());
		model.setCreateBy(CurrentUser.getUserId());
		model.setCreateTime(new Date());
		// 执行新增
		try {
			ruleMapper.insert(rule);
			modelMapper.insert(model);
			//增加规则的同时 生成相关邮件
			addMailByRule(rule);
			result.setMessage(TipMessage.CREATE_SUCCESS);
		} catch (SQLException e) {
			logger.error(TipMessage.CREATE_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.CREATE_FAIL);
		}

		return result;
	}

	/**
	 * 获取规则列表
	 * @param operationId
	 * @return 规则列表
	 */
	public Result<List<RuleListForm>> listRule(String operationId){
		Result<List<RuleListForm>> result = new Result<>();
		// 执行查询
		try {
			List<RuleListForm> ruleList = ruleMapper.listRuleListForm(operationId);
			result.setResultEntity(ruleList);
		} catch (SQLException e) {
			logger.error(TipMessage.QUERY_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.QUERY_FAIL);
		}
		return result;
	}

	/**
	 * 获取规则详情
	 * @param ruleId
	 * @return 规则详情
	 */
	public Result<RuleDetailForm> getRuleDetail(String ruleId){

		Result<RuleDetailForm> result = new Result<>();

		// 参数校验
		if (Strings.isEmpty(ruleId)){
			result.setSuccess(false);
			result.setMessage(ID_NULL);
			return result;
		}

		// 执行查询
		try {
			RuleDetailForm ruleDetailForm = ruleMapper.getRuleDetail(ruleId);
			if (ruleDetailForm == null){
				result.setSuccess(false);
				result.setMessage(TipMessage.NO_DATA);
			}else {
				result.setResultEntity(ruleDetailForm);
			}
		} catch (SQLException e) {
			logger.error(TipMessage.QUERY_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.QUERY_FAIL);
		}

		return result;

	}

	/**
	 * 删除规则
	 * @param ruleId
	 * @return 操作结果
	 */
	public Result deleteRule(String ruleId){

		Result result = new Result();

		// 参数校验
		if (Strings.isEmpty(ruleId)){
			result.setSuccess(false);
			result.setMessage(ID_NULL);
			return result;
		}
		// 执行删除
		try {
			int res = ruleMapper.deleteByPrimaryKey(ruleId);
			//删除规则的同时 删除相关邮件
			deleteMailByRule(ruleId);
			if (res > 0){
				result.setMessage(TipMessage.DELETE_SUCCESS);
			}else {
				result.setMessage(TipMessage.NO_DATA_DELETE);
			}
		} catch (SQLException e) {
			logger.error(TipMessage.DELETE_FAIL,e);
			result.setSuccess(false);
			result.setMessage(TipMessage.DELETE_FAIL);
		}

		return result;
	}

	/**
	 * 更新规则
	 * @param ruleDetailForm
	 * @return 操作结果
	 */
	public Result updateRule(RuleDetailForm ruleDetailForm){

		Result result = new Result();
		// 参数校验
		if (ruleDetailForm == null){
			result.setSuccess(false);
			result.setMessage(TipMessage.PARAM_NULL);
			return result;
		}
		if (Strings.isEmpty(ruleDetailForm.getId())){
			result.setSuccess(false);
			result.setMessage(ID_NULL);
			return result;
		}
		if (Strings.isEmpty(ruleDetailForm.getModelId())){
			result.setSuccess(false);
			result.setMessage(MODEL_ID_NULL);
			return result;
		}
		// 构建规则模型
		Rule rule = new Rule();
		rule.setId(ruleDetailForm.getId());
		rule.setRuleName(ruleDetailForm.getRuleName());
		rule.setRuleMethod(ruleDetailForm.getRuleMethod());
		rule.setDistanceY(ruleDetailForm.getDistanceY());
		rule.setDistanceM(ruleDetailForm.getDistanceM());
		rule.setDistanceD(ruleDetailForm.getDistanceD());
		rule.setSendingHourofday(ruleDetailForm.getSendingHourofday());
		rule.setSendingMinofhour(ruleDetailForm.getSendingMinofhour());
		rule.setOperationId(ruleDetailForm.getOperationId());
		rule.setCreateBy(CurrentUser.getUserId());
		rule.setCreateTime(new Date());
		if(rule.getRuleMethod().equals("1")){
			rule.setEntryDistance(caculateRule_1(rule)); //方法1：入职时长计算
		}else if(rule.getRuleMethod().equals("2")){
			rule.setEntryDistance(caculateRule_2(rule)); //方法2：距离合同续签日期计算
		}else{
			rule.setEntryDistance(caculateRule_3(rule)); //方法3：距离转正日期计算
		}
		// 构建模板模型
		Model model = new Model();
		model.setId(ruleDetailForm.getModelId());
		model.setModelName(ruleDetailForm.getRuleName() + "模板");
		model.setModelContent(ruleDetailForm.getModelContent());
		model.setAttachmentHref(ruleDetailForm.getAttachmentHref());
		model.setCreateBy(CurrentUser.getUserId());
		model.setCreateTime(new Date());

		try {
			ruleMapper.updateByPrimaryKeySelective(rule);
			modelMapper.updateByPrimaryKeySelective(model);
			result.setMessage(TipMessage.UPDATE_SUCCESS);
		} catch (SQLException e) {
			logger.error(TipMessage.UPDATE_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.UPDATE_FAIL);
		}

		return result;
	}


	/**
	 * 方法：删除规则的同时 删除规则与该相关的待发送邮件；
	 * @return
	 * @throws SQLException *
	 */
	@SuppressWarnings("rawtypes")
	public Result addMailByRule(Rule rule){
		Result result = new Result();
		// 参数校验
		if (rule == null){
			result.setSuccess(false);
			result.setMessage(TipMessage.PARAM_NULL);
			return result;
		}
		try {
			for(Employee em : employeelMapper.listAllEmployee()){
				mps.setMail(rule, em);
			}
			result.setMessage(TipMessage.CREATE_SUCCESS);
		} catch (Exception e) {
			logger.error(TipMessage.CREATE_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.CREATE_FAIL);
		}
		return result;
	}

	 /**
	 * 方法：删除规则的同时 删除规则与该相关的待发送邮件；
	 * @return
	 * @throws SQLException *
	 */
	@SuppressWarnings("rawtypes")
	public Result deleteMailByRule(String rule_id){
		Result result = new Result();
		// 参数校验
		if (rule_id == null){
			result.setSuccess(false);
			result.setMessage(TipMessage.PARAM_NULL);
			return result;
		}
		try {
			mailMapper.deleteMailByRule(rule_id);
			result.setMessage(TipMessage.DELETE_SUCCESS);
		} catch (Exception e) {
			logger.error(TipMessage.DELETE_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.DELETE_FAIL);
		}
		return result;
	}


    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离入职多长时间发送邮件
     */
    public String caculateRule_1(Rule rule){
    	String entry_distance =  "入职"+rule.getDistanceY()+"年"+
    						rule.getDistanceM()+"月"+
    						rule.getDistanceD()+"日 "+
    						rule.getSendingHourofday()+"点"+
    						rule.getSendingMinofhour()+"分时发送邮件";
    	rule.setRuleMethod("1");
	  	return entry_distance;
    }
    
    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离合同到期多长时间发送邮件
     */
    public String caculateRule_2(Rule rule){
    	String entry_distance =  "距离"+"合同到期的"+
				    						rule.getDistanceY()+"年"+
				    						rule.getDistanceM()+"月"+
				    						rule.getDistanceD()+"日 的当天"+
				    						rule.getSendingHourofday()+"点"+
				    						rule.getSendingMinofhour()+"分时发送邮件";
    	rule.setRuleMethod("2");
	  	return entry_distance;
    }
    
    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离转正多长时间发送邮件
     */
    public String caculateRule_3(Rule rule){
    	String entry_distance =  "距离"+"试用期转正的"+
				    						rule.getDistanceY()+"年"+
				    						rule.getDistanceM()+"月"+
				    						rule.getDistanceD()+"日 的当天"+
				    						rule.getSendingHourofday()+"点"+
				    						rule.getSendingMinofhour()+"分时发送邮件";
    	rule.setRuleMethod("3");
	  	return entry_distance;
    }
    
    /**
     * 改变规则启用状态(0:禁用 1:启用)
     * @param ruleId
     * @return 
     * @return
     */
	public Result changeStatus(String ruleId) {
		Result result = new Result();
		// 参数校验
		if (ruleId == null){
			result.setSuccess(false);
			result.setMessage(TipMessage.PARAM_NULL);
			return result;
		}
		try {
			Rule record = ruleMapper.selectByPrimaryKey(ruleId);
			//启用与禁用的转换设置
			if(record.getIsUse()==0){
				record.setIsUse(1);
			}else{
				record.setIsUse(0);
			}
			ruleMapper.updateByPrimaryKey(record);
			result.setMessage(TipMessage.UPDATE_SUCCESS);
		} catch (Exception e) {
			logger.error(TipMessage.UPDATE_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.UPDATE_FAIL);
		}
		return result;
	}
}
