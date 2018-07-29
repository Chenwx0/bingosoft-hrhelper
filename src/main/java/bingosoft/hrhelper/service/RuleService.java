package bingosoft.hrhelper.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Rule;

@Service
public class RuleService {
	
	@Autowired
	RuleMapper rm ;
	
	//规则：根据规则方法，确定规则计算方式。
	public void addRule(Rule rule){
		
		Rule newRule = new Rule();
		newRule.setId(UUID.randomUUID().toString());
		newRule.setRuleName(rule.getRuleName());
		newRule.setEntryDistanceY(rule.getEntryDistanceY());
    	newRule.setEntryDistanceM(rule.getEntryDistanceM());
    	newRule.setEntryDistanceD(rule.getEntryDistanceD());
    	newRule.setSendingHourofday(rule.getSendingHourofday());
    	newRule.setSendingMinofhour(rule.getSendingMinofhour());
    	newRule.setModelId(rule.getModelId()); //对应的模板id
    	newRule.setOperationId(rule.getOperationId());//对应的业务id
		newRule.setCreateBy(rule.getCreateBy()); //创建人
		newRule.setCreateTime(new Date()); //创建时间
    	
		if(rule.getRuleMethod().equals("1")){
			rule.setEntryDistance(caculateRule_1(rule)); //方法1：入职时长计算
		}else{
			rule.setEarlyDay(caculateRule_2(rule)); //方法2：距离特殊日期计算
		}
		rm.insert(rule);
	}
	
	//删除规则
	public void deleteRule(String rule_id){
		rm.deleteByPrimaryKey(rule_id);
	}
	
	//更新规则
	public void updateRule(Rule rule){
		rm.updateByPrimaryKey(rule);
	}
    
    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离入职多长时间发送邮件
     */
    public String caculateRule_1(Rule rule){
    	String entry_distance =  "入职"+rule.getEntryDistanceY()+"年"+
    						rule.getEntryDistanceM()+"月"+
    						rule.getEntryDistanceD()+"日 "+
    						rule.getSendingHourofday()+"点"+
    						rule.getSendingMinofhour()+"分时发送邮件";
    	rule.setRuleMethod("入职多长时间");
	  	return entry_distance;
    }
    
    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离入职多长时间发送邮件
     */
    public String caculateRule_2(Rule rule){
    	String entry_distance =  "距离"+"某一特殊的日子的"+
				    						rule.getEntryDistanceY()+"年"+
				    						rule.getEntryDistanceM()+"月"+
				    						rule.getEntryDistanceD()+"日 的当天"+
				    						rule.getSendingHourofday()+"点"+
				    						rule.getSendingMinofhour()+"分时发送邮件";
    	rule.setRuleMethod("根据某个日期提前");
	  	return entry_distance;
    }
    
    
    
    /**
     * 业务：根据规则进行计算
     * @param rule 
     * @return 根据该规则，员工距离入职多长时间发送邮件
     *//*
    public String caculateRule2(Rule rule){

	  	  Calendar sendTimeUntil = Calendar.getInstance();
	  	  //获得规则确定的“距离入职后多久发送的日期”(日期)
	      sendTimeUntil.set(0, 0, 0, 0, 0);
	        
	      System.out.println("时间日历"+Calendar.getInstance());
	      
	  	  //添加距离入职时间(年) 备注：年限传到数据库自动加1  原因：如果将0年传到数据库，显示年份为0001。
	  	  sendTimeUntil.add(Calendar.YEAR,rule.getEntryDistanceY()+1);
	  	  //添加距离入职时间(月)
	  	  sendTimeUntil.add(Calendar.MONTH,rule.getEntryDistanceM());
	  	  //添加距离入职时间(日)
	  	  sendTimeUntil.add(Calendar.DAY_OF_MONTH, rule.getEntryDistanceD());
	  	  
	  	  //添加当天准确时间(小时)
	  	  sendTimeUntil.add(Calendar.HOUR_OF_DAY, rule.getSendingHourofday());
	  	  //添加当天准确时间(分钟)
	  	  sendTimeUntil.add(Calendar.MINUTE, rule.getSendingMinofhour());
	  	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	  	  Date date=sendTimeUntil.getTime();
	  	  
	  	  System.out.println("得到的日期1——"+date);
	  	  
	  	  String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
	  	  
	  	  return dateStr;
    }*/
}
