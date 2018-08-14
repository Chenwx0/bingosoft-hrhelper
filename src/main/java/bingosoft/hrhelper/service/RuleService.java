package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @创建人 zhangyx
 * @功能描述 规则计算服务
 * @创建时间 2018-08-03 14:08:08
 */
@Service
public class RuleService {
	
	@Autowired
	RuleMapper rm ;
	
	//规则：根据规则方法，确定规则计算方式。
	public void addRule(Rule rule){

		rule.setId(UUID.randomUUID().toString());
		rule.setCreateBy(CurrentUser.getUserId());
		rule.setCreateTime(new Date());
		if(rule.getRuleMethod().equals("1")){
			rule.setEntryDistance(caculateRule_1(rule)); //方法1：入职时长计算
		}else{
			rule.setSpecialdayDistance(caculateRule_2(rule)); //方法2：距离特殊日期计算
		}

		rm.insert(rule);
	}
	
	//删除规则
	public void deleteRule(String rule_id){
		rm.deleteByPrimaryKey(rule_id);
	}
	
	//更新规则
	public void updateRule(Rule rule){

		rule.setUpdateBy(CurrentUser.getUserId());
		rule.setUpdateTime(new Date());

		rm.updateByPrimaryKey(rule);
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
     * @return 根据该规则，员工距离入职多长时间发送邮件
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
     * @return 根据该规则，员工距离入职多长时间发送邮件
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
