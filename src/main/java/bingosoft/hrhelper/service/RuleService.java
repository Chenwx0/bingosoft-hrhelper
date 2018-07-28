package bingosoft.hrhelper.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Rule;

@Service
public class RuleService {
	
	@Autowired
	RuleMapper rm ;
	
	//规则：规则填写
	public void addRule(Rule rule){
		rule.setEntryDistance(caculateRule(rule)); 
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
    public String caculateRule(Rule rule){

	  	  Calendar sendTimeUntil = Calendar.getInstance();
	  	  //获得规则确定的“距离入职后多久发送的日期”(日期)
	      sendTimeUntil.set(0, 0, 0, 0, 0);
	        
	  	  //添加距离入职时间(年)
	  	  sendTimeUntil.add(Calendar.YEAR,rule.getEntryDistanceY());
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
	  	  
	  	/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	  	  
	  	 sdf.parse(arg0)
	  	  
	  	 
	  	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 Date d =new Date();
		 //把日期转换为字符串
		 String str=sdf.format(d);
		 System.out.println(str);
		 //将字符串转换为日期
		 Date d1=sdf.parse("yyyy-mm-dd HH:mm");*/
	  	 
	  	//1、定义转换格式
	    /*SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");*/
	    //2、调用formatter2.parse(),将"19570323"转化为date类型  输出为：Sat Mar 23 00:00:00 GMT+08:00 1957
	    
	   //3、将date类型  (Sat Mar 23 00:00:00 GMT+08:00 1957)转化为String类型 
	    //注意现在用的是formatter来做转换,输出为String类型的："1957-03-23"
	    /*String  dString = formatter.format(date);
	    Date data = Date.valueOf(dString);
	    //4、将String转化为date，需要注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的
	    System.out.println("得到的日期2——"+data);*/
	  	  
	  	  return dateStr;
    }
    
   
	/*@Test
    public void caculateRule(){

	  	  Calendar sendTimeUntil = Calendar.getInstance();
	  	  //获得规则确定的“距离入职后多久发送的日期”(日期)
	        sendTimeUntil.set(0, 0, 0, 0, 0);
	        
	  	  //添加距离入职时间(年)
	  	  sendTimeUntil.add(Calendar.YEAR,1);
	  	  //添加距离入职时间(月)
	  	  sendTimeUntil.add(Calendar.MONTH,1);
	  	  //添加距离入职时间(日)
	  	  sendTimeUntil.add(Calendar.DAY_OF_MONTH, 1);
	  	  
	  	  //添加当天准确时间(小时)
	  	  sendTimeUntil.add(Calendar.HOUR_OF_DAY, 1);
	  	  //添加当天准确时间(分钟)
	  	  sendTimeUntil.add(Calendar.MINUTE, 1);
	  	  
	  	  Date date=sendTimeUntil.getTime();
	  	  
	  	  String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
	  	  System.out.println(dateStr);
    }*/
}
