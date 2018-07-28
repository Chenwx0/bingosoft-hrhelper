package bingosoft.hrhelper.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.UpdateMailTimer;
import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Employee;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Rule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Service
public class MailService {
	
	@Autowired
	EmployeeMapper em;
	@Autowired
	RuleMapper rm;
	/*@Autowired
	private Employee e;*/
	@Autowired
	MailMapper mm;
	UpdateMailTimer u = new UpdateMailTimer();
	
	@Test
	public void produceMail() throws ParseException{
		/**
		 * 1、遍历员工(2、嵌套遍历规则)
		 * 2、生成邮件但不存储
		 * 3、日期计算，与转正时期相差少于7天 存储
		 * 4、已经生成的邮件不去修改(这个怎么做到？遍历前判断员工_业务之间是否已经存在邮件，存在则停止操作数据库)
		 */
		//！！！！！！！！！！！！！！！此处rm注入为空
		mm.deleteAll();
		for(Employee e : em.listAllEmployee() ){
			for(Rule r : rm.listAllRule()){
				setMail(r,e);
				/*if((拟发送日期-当前日期)<=7天){
					邮件生成
				}
				（每天的模板都要生成吗）*/
			}
		}
		
		/*EmployeeService e = new   */
		System.out.println(UUID.randomUUID().toString());
	}
	
	
	
	
	//根据规则自动生成邮件的类，需导入实体 1：收件员工 2：具体规则
	public Mail setMail(Rule rule,Employee e) throws ParseException{
		Mail mail = new Mail();
		//规则在数据库拿_每个规则_另外写一个方法
		//员工在数据库拿_每个员工_进行遍历
		mail.setId(UUID.randomUUID().toString());
		mail.setMailName(rule.getRuleName());
		mail.setRecipient(e.getName());
		mail.setRecipientAddress(e.getMail());
		mail.setSender("人力资源部");
		mail.setSenderAddress("Hr@BingoSoft.com");
		mail.setCopyPeople("抄送人");
		mail.setCopyPeopleAddress("抄送人邮箱");
		mail.setOperationId(rule.getOperationId());
		mail.setStatus(0);//默认为0：待审核。如果发生员工在审批前离职的情况，则由人工取消邮件发送。
		mail.setOperationId("");/*业务id*/
		
		mail.setPlanSendTime(sendTimeCount(rule,e));
		
		mm.insert(mail);
		
		return mail;
	}
	
	/**
	 * 方法：邮件拟发送时间计算
	 * @throws ParseException 
	 * 距离入职时间：rule.getEntry_distance() : 格式"yyyy-MM-dd hh:mm" 
	 * 入职日期：      e.getEntryDay()              : 格式"yyyy-MM-dd"           
	 */ 
	public Date sendTimeCount(Rule rule,Employee e) throws ParseException{
				
				//流程：Date→Calendar
				Calendar entrydayCal = Calendar.getInstance();
				entrydayCal.setTime(e.getEntryDay());
				
				
				//流程：String→Date→Calendar类型转换(计算出结果后)→Date返回类型
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Date date = sdf.parse(rule.getEntryDistance().toString());
				Calendar entrydisCal = Calendar.getInstance();
				entrydisCal.setTime(date);
				
				// 入职日期(具体到日) +入职后时长(具体到分)
				entrydisCal.add(Calendar.YEAR, entrydayCal.get(Calendar.YEAR));
				entrydisCal.add(Calendar.MONTH, entrydayCal.get(Calendar.MONTH));
				entrydisCal.add(Calendar.DAY_OF_MONTH, entrydayCal.get(Calendar.DAY_OF_MONTH));
				
				Date sendTime=entrydisCal.getTime();
				
				//邮件拟发送时间
				return sendTime;
	}




	public EmployeeMapper getEm() {
		return em;
	}




	public void setEm(EmployeeMapper em) {
		this.em = em;
	}




	public RuleMapper getRm() {
		return rm;
	}




	public void setRm(RuleMapper rm) {
		this.rm = rm;
	}


}
