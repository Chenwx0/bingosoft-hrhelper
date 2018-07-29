package bingosoft.hrhelper.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.DateTransferUtils;
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
	@Autowired
	MailMapper mm;
	int i=0;
	
	@Test
	@Scheduled(cron = "0 30 2 * * ?") /*每天晚上2:30更新邮件表*/
	public void produceMail() throws ParseException{
		/**
		 * 1、遍历员工(2、嵌套遍历规则)
		 * 2、生成邮件但不存储
		 * 3、日期计算，与转正时期相差少于7天 存储
		 * 4、已经生成的邮件不去修改(这个怎么做到？遍历前判断员工_业务之间是否已经存在邮件，存在则停止操作数据库)
		 */
		mm.deleteAll();
		for(Employee e : em.listAllEmployee() ){
			for(Rule r : rm.listAllRule()){
				setMail(r,e);
			}
		}
	}

	/**
	 * 判断是否自动生成邮件的类，需导入实体 1：收件员工 2：具体规则
	 * @param Rule
	 * @param Employee
	 * @throws ParseException
	 */
	public void setMail(Rule r,Employee e) throws ParseException{
		Mail mail = new Mail();
		mail.setPlanSendTime(sendTimeCountMethod_1(r,e));
		//判断该邮件是否在生成日期区间
		if(judgeProduce(mail)){
			//传入规则和具体员工生成邮件
			setMailContent(r,e,mail);
			mm.insert(mail);
		}
		//根据规则方法计算邮件拟发送时间
		if(r.getRuleMethod().equals("1")){
			mail.setPlanSendTime(sendTimeCountMethod_1(r,e));
		}else{
			mail.setPlanSendTime(sendTimeCountMethod_2(new Date(),r)); //特殊日期&根据规则计算提前的时间
		}
	}
	
	public Mail setMailContent(Rule r,Employee e,Mail mail) throws ParseException{

		//规则在数据库拿_每个规则_另外写一个方法
		//员工在数据库拿_每个员工_进行遍历
		mail.setId(UUID.randomUUID().toString());
		mail.setMailName(r.getRuleName());
		mail.setRecipient(e.getName());
		mail.setRecipientAddress(e.getMail());
		mail.setSender("人力资源部");
		mail.setSenderAddress("Hr@BingoSoft.com");
		mail.setCopyPeople("抄送人");
		mail.setCopyPeopleAddress("抄送人邮箱");
		mail.setOperationId(r.getOperationId());
		mail.setStatus(0);//默认为0：待审核。如果发生员工在审批前离职的情况，则由人工取消邮件发送。
		mail.setOperationId("");/*业务id*/
		
		if(r.getRuleMethod().equals("1")){
			mail.setPlanSendTime(sendTimeCountMethod_1(r,e));
		}else{
			mail.setPlanSendTime(sendTimeCountMethod_2(new Date(),r));  //需要特殊日期和提前的时间
		}
		return mail;
	}
	
	/**
	 * 方法：邮件拟发送时间计算 方式一：入职多长时间发送
	 * @throws ParseException 
	 * 距离入职时间：rule.getEntry_distance() : 格式"yyyy-MM-dd hh:mm" 
	 * 入职日期：      e.getEntryDay()              : 格式"yyyy-MM-dd"           
	 */ 
	public Date sendTimeCountMethod_1(Rule rule,Employee e) throws ParseException{
				//流程：Date→Calendar
				Calendar entrydayCal = Calendar.getInstance();
				entrydayCal.setTime(e.getEntryDay());
				
				// 入职日期(具体到日) +入职后时长(具体到分)
				entrydayCal.add(Calendar.YEAR, rule.getEntryDistanceY());
				entrydayCal.add(Calendar.MONTH, rule.getEntryDistanceM());
				entrydayCal.add(Calendar.DAY_OF_MONTH, rule.getEntryDistanceD());
				entrydayCal.add(Calendar.HOUR, rule.getSendingHourofday());
				entrydayCal.add(Calendar.MINUTE, rule.getSendingMinofhour());
				
				Date sendTime=entrydayCal.getTime();
				
				//邮件拟发送时间
				return sendTime;
	}
	
	
	/**
	 * 方法：邮件拟发送时间计算 方式二：在某一个特殊日期提前多久发送
	 * 确定某一特殊日期 ：rule.getSpecialDay : 格式"yyyy-MM-dd"
	 * 提前多久 earlyDate
	 */ 
	public Date sendTimeCountMethod_2(Date specailDay,Rule rule) throws ParseException{
		
		//流程：Date→Calendar
		Calendar specailDayCal = Calendar.getInstance();
		specailDayCal.setTime(specailDay);
		
		// (特殊日期-提前时间) + 当天发送时间(具体到分)
		specailDayCal.add(Calendar.YEAR, -rule.getEntryDistanceY());
		specailDayCal.add(Calendar.MONTH, -rule.getEntryDistanceM());
		specailDayCal.add(Calendar.DAY_OF_MONTH,- rule.getEntryDistanceD());
		specailDayCal.add(Calendar.HOUR, rule.getSendingHourofday());
		specailDayCal.add(Calendar.MINUTE, rule.getSendingMinofhour());
		
		Date sendTime=specailDayCal.getTime();
		
		//邮件拟发送时间
		return sendTime;
	}
	
	/**
	 * 需要根据某日期提前N天发送：调用该方法。
	 * @param 某特殊日期
	 * @param 提前的天数
	 */
	public Date earlyDayUtil(Date oneDayTime,int earlyDay){
		//转换日历类型：并计算某天时间-提前天数
		Calendar countTime = Calendar.getInstance();
		countTime.setTime(oneDayTime);
		countTime.add(Calendar.DAY_OF_MONTH, -earlyDay);
		//返回计算结果(拟发送时间)
		Date sendTime = countTime.getTime();
		return sendTime;
	}
	
	/**
	 * 判断一封邮件是否在生成日期范围内
	 * @param Mail实体
	 * @throws ParseException 
	 */
	public boolean judgeProduce(Mail mail) throws ParseException{
		/**
		* 获得以 yyyy-MM-dd 为形式的当前时间
		* 将String转化成date 格式
		*/
		
		//生成"yyyy-MM-dd"的当前时间
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowdayTime = dateFormat.format(new Date());
		Date today = dateFormat.parse(nowdayTime);
		
		//转换为日历类型并加上7天，再转换为普通的Date类型。
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, 7);
		Date compareDay = c.getTime();
		
		System.out.println("七天后是"+DateTransferUtils.fDateCNYRSFM(compareDay)+"计划发送日期是"+DateTransferUtils.fDateCNYRSFM(mail.getPlanSendTime()));
		
		/*拟发送时间如果比当前时间加七天早，并且比今天晚，返回true*/
		return compareDay.after(mail.getPlanSendTime())&&mail.getPlanSendTime().after(today);
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