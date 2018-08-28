package bingosoft.hrhelper.service;

import java.sql.SQLException;
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
import bingosoft.hrhelper.common.ReadxmlByDom;
import bingosoft.hrhelper.mapper.ApproveMapper;
import bingosoft.hrhelper.mapper.CancelRecordMapper;
import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.mapper.UserMapper;
import bingosoft.hrhelper.model.Approve;
import bingosoft.hrhelper.model.CancelRecord;
import bingosoft.hrhelper.model.Employee;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.MailConfig;
import bingosoft.hrhelper.model.Operation;
import bingosoft.hrhelper.model.Rule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Service
/**
 * @创建人 zhangyx
 * @功能描述 邮件生成服务
 * @创建时间 2018-08-03 14:08:08
 */
public class MailProductService {
	

	
	@Autowired
	EmployeeMapper em;
	@Autowired
	RuleMapper rm;
	@Autowired
	OperationMapper om;
	@Autowired
	MailMapper mm;
	@Autowired
	CreateMailContentService cmcs;
	@Autowired
	ApproveMapper am;
	@Autowired
	UserMapper um;
	
	int i=0;
	
	/**
	 * 方法：邮件生成主流程。
	 * 每天晚上2:30更新邮件表    表达式为：cron = "0 30 2 * 
	 * @throws SQLException *
	 */
	@Test
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void produceMail() throws ParseException, SQLException{
		//(1)、删除离职员工的邮件
		for(Mail m : mm.listAll()){
			if(deleteLeaveEmployee(m)){
				System.out.println("正在删除邮件"+m.toString()+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^66");
				mm.deleteByPrimaryKey(m.getId());
			}
		}
		
		//(2)、生成当天新邮件
		for(Employee e : em.listAllEmployee() ){
			for(Rule r : rm.listAllRule()){
				setMail(r,e);
			}
		}
	}

	private boolean deleteLeaveEmployee(Mail m) {
			for(Employee e : em.listAllEmployee()){
				//如果邮件对应的员工ID还存在，返回false
				System.out.println(m.getEmployeeId()+"对比"+e.getId()+"如果相同则返回false不删除");
				if(m.getEmployeeId().equals(e.getId())){
					return false;
				}
			}
		//如果邮件对应的员工ID已离职，返回true
		return true;
	}

	/**
	 * 判断是否自动生成邮件的类，需导入实体 1：收件员工 2：具体规则
	 * 如果邮件生成次数为2次或以上，则一个规则生成多封邮件。
	 * @param Rule
	 * @param Employee
	 * @throws ParseException
	 */
	public void setMail(Rule r,Employee e) throws ParseException{
		Mail m = new Mail();
		//根据规则方法计算邮件拟发送时间
		setPlanSendTime(r,e,m);
		//生成判断方式一： (1)、该邮件是否在生成日期区间/是否已经生成过了  (2)、发送次数为1次
		if(judgeExistProduce(r,e,m)){
			//传入规则和具体员工生成邮件
			setMailContent(r,e,m);
			//邮件生成 添加到数据库
			mm.insert(m);
		}
	}
	
	/**
	 * 根据规则方法计算邮件拟发送时间     规则方法1：入职后多久    规则方法2：在某一时间多久之前
	 * @param r
	 * @param e
	 * @param m
	 * @throws ParseException
	 */
	public void setPlanSendTime(Rule r,Employee e,Mail m) throws ParseException{
		if(r.getRuleMethod().equals("1")){
			m.setPlanSendTime(sendTimeCountMethod_1(r,e));//入职后多久
		}else if(r.getRuleMethod().equals("2")){
			m.setPlanSendTime(sendTimeCountMethod_2(r,e)); //特殊日期&根据规则计算提前的时间
		}else if(r.getRuleMethod().equals("3")){
			m.setPlanSendTime(sendTimeCountMethod_3(r,e)); //特殊日期&根据规则计算提前的时间
		}
	}
	
	/**
	 * 设置邮件的具体内容
	 * @param r
	 * @param e
	 * @param m
	 * @return
	 * @throws ParseException
	 */
	public void setMailContent(Rule r,Employee e,Mail m) throws ParseException{
		
		//设置邮件发送人信息
		try {
			//获取邮件对应的接口人(先获取接口人ID 再获得接口人名称)
			String user_id = om.selectByPrimaryKey(r.getOperationId()).getUserId();
			String user_name = um.selectByPrimaryKey(user_id).getUsername();
			m.setSender(user_name);
			m.setSenderAddress("Hr@BingoSoft.com");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		m.setId(UUID.randomUUID().toString());
		m.setMailName(r.getRuleName());
		m.setCreateTime(new Date());
		m.setOperationId(r.getOperationId());
		m.setRuleId(r.getId());
		m.setEmployeeId(e.getId());
		m.setStatus(1);//默认为1：待发送。如果管理员点击取消，则变为0。
		
		//判断是否为需要工作流或者抄送人的特殊邮件
		//根据该规则Id，获取对应业务是否需要特殊处理的信息
		String ifSpecial = om.ifSpecial(r.getOperationId());
		//生成普通邮件内容
		if(ifSpecial==null){
			setCommonMailContent(r,e,m);
		}
		//生成普通邮件内容
		if(ifSpecial.equals("0")){
			setCommonMailContent(r,e,m);
		}
		//生成特殊类型"试用期转正"
		if(ifSpecial.equals("1")){
			setFullMenberMailContent(r,e,m);
		}
		//生成特殊类型"合同续签"
		if(ifSpecial.equals("2")){
			setContractMailContent(r,e,m);
		}
		//生成特殊类型"绩效表填写"
		if(ifSpecial.equals("3")){
			setPerformanceMailContent(r,e,m);
		}
	}
	
	/**
	 * 设置邮件的具体内容___转正提醒邮件(带附件发送给经理，征得经理同意则触发第二封邮件发给HR，提醒员工进行申请)
	 * @param r
	 * @param e
	 * @param m
	 * @throws ParseException
	 */
	private void setFullMenberMailContent(Rule r, Employee e, Mail m) {
		
		m.setRecipient(e.getManager());
		m.setRecipientAddress(e.getManagerMail());
		//根据规则方法与员工信息 生成邮件模板
		m.setMailContent(cmcs.getMailContent(e.getId(), r.getModelId(), DateTransferUtils.dateTimeFormat(m.getPlanSendTime()),"截止日期"));
		
		//生成审批内容，设置审批状态为0:待审批
		String approveId = UUID.randomUUID().toString();
		m.setApproveId(approveId);
		
		Approve a = new Approve();
		a.setId(approveId);
		a.setOperationId(r.getOperationId());
		a.setStatus(0);
		a.setCreateTime(new Date());
		a.setApprover(e.getManager());
		a.setApproveObject(e.getName());
		am.insert(a);
	}
	
	/**
	 * 设置邮件的具体内容___合同续签提醒邮件(发送给经理，征得经理同意则发送给员工进行申请)
	 * @param r
	 * @param e
	 * @param m
	 * @throws ParseException
	 */
	private void setContractMailContent(Rule r, Employee e, Mail m) {
		m.setRecipient(e.getManager());
		m.setRecipientAddress(e.getManagerMail());
		//根据规则方法与员工信息 生成邮件模板
		m.setMailContent(cmcs.getMailContent(e.getId(), r.getModelId(), DateTransferUtils.dateTimeFormat(m.getPlanSendTime()),"截止日期"));
		
		//生成审批内容，设置审批状态为0:待审批
		String approveId = UUID.randomUUID().toString();
		m.setApproveId(approveId);
		
		Approve a = new Approve();
		a.setId(approveId);
		a.setOperationId(r.getOperationId());
		a.setStatus(0);
		a.setCreateTime(new Date());
		a.setApprover("approver");
		a.setApproveObject("approveObject");
		am.insert(a);
	}
	
	/**
	 * 设置邮件的具体内容___试用期绩效表提交提醒邮件(发送给员工，同时抄送给HR。满月前2天开始重复提醒3次)
	 * @param r
	 * @param e
	 * @param m
	 * @throws ParseException
	 */
	private void setPerformanceMailContent(Rule r, Employee e, Mail m) {
		m.setRecipient(e.getName());
		m.setRecipientAddress(e.getMail());
		//根据规则方法与员工信息 生成邮件模板
		m.setMailContent(cmcs.getMailContent(e.getId(), r.getModelId(), DateTransferUtils.dateTimeFormat(m.getPlanSendTime()),"截止日期"));
	}

	/**
	 * 设置邮件的具体内容___普通邮件
	 * @param r
	 * @param e
	 * @param m
	 * @param special 
	 * @throws ParseException
	 */
	public void setCommonMailContent(Rule r,Employee e,Mail m) throws ParseException{
		//设置接收人信息
		m.setRecipient(e.getName());
		m.setRecipientAddress(e.getMail());
		//根据规则方法与员工信息 生成邮件模板
		m.setMailContent(cmcs.getMailContent(e.getId(), r.getModelId(), DateTransferUtils.dateTimeFormat(m.getPlanSendTime()),"截止日期"));
	}
	
	/**
	 * @param r
	 * @param e
	 * 方法：邮件拟发送时间计算 方式一：入职多长时间发送
	 * 距离入职时间：rule.getEntry_distance() : 格式"yyyy-MM-dd hh:mm" 
	 * 入职日期：      e.getEntryDay()              : 格式"yyyy-MM-dd"         
	 * @throws ParseException
	 */
	public Date sendTimeCountMethod_1(Rule r,Employee e) throws ParseException{
				//流程：Date→Calendar
				Calendar entrydayCal = Calendar.getInstance();
				entrydayCal.setTime(e.getEntryDay());
				// 入职日期(具体到日) +入职后时长(具体到分)
				entrydayCal.add(Calendar.YEAR, r.getDistanceY());
				entrydayCal.add(Calendar.MONTH, r.getDistanceM());
				entrydayCal.add(Calendar.DAY_OF_MONTH, r.getDistanceD());
				entrydayCal.add(Calendar.HOUR, r.getSendingHourofday());
				entrydayCal.add(Calendar.MINUTE, r.getSendingMinofhour());
				Date sendTime=entrydayCal.getTime();
				//邮件拟发送时间
				return sendTime;
	}
	
	
	
	/**
	 * 方法：邮件拟发送时间计算 方式二：在合同到期前多久发送
	 * 确定某一特殊日期 ：rule.getSpecialDay : 格式"yyyy-MM-dd"
	 * 提前多久：earlyDate
	 * @param specailDay
	 * @param r
	 * @return
	 * @throws ParseException
	 */
	public Date sendTimeCountMethod_2(Rule r,Employee e) throws ParseException{
		//流程：Date→Calendar
		Calendar specailDayCal = Calendar.getInstance();
		specailDayCal.setTime(e.getContractDay());
		// (特殊日期-提前时间) + 当天发送时间(具体到分)
		specailDayCal.add(Calendar.YEAR, -r.getDistanceY());
		specailDayCal.add(Calendar.MONTH, -r.getDistanceM());
		specailDayCal.add(Calendar.DAY_OF_MONTH,- r.getDistanceD());
		specailDayCal.add(Calendar.HOUR, r.getSendingHourofday());
		specailDayCal.add(Calendar.MINUTE, r.getSendingMinofhour());
		Date sendTime=specailDayCal.getTime();
		//邮件拟发送时间
		return sendTime;
	}
	
	/**
	 * 方法：邮件拟发送时间计算 方式三：在试用期转正前多久发送
	 * 确定某一特殊日期 ：rule.getSpecialDay : 格式"yyyy-MM-dd"
	 * 提前多久：earlyDate
	 * @param specailDay
	 * @param r
	 * @return
	 * @throws ParseException
	 */
	public Date sendTimeCountMethod_3(Rule r,Employee e) throws ParseException{
		//流程：Date→Calendar
		Calendar specailDayCal = Calendar.getInstance();
		specailDayCal.setTime(e.getFullmenberDay());
		// (特殊日期-提前时间) + 当天发送时间(具体到分)
		specailDayCal.add(Calendar.YEAR, -r.getDistanceY());
		specailDayCal.add(Calendar.MONTH, -r.getDistanceM());
		specailDayCal.add(Calendar.DAY_OF_MONTH,- r.getDistanceD());
		specailDayCal.add(Calendar.HOUR, r.getSendingHourofday());
		specailDayCal.add(Calendar.MINUTE, r.getSendingMinofhour());
		Date sendTime=specailDayCal.getTime();
		//邮件拟发送时间
		return sendTime;
	}
	
	/**
	 * 
	 * @param oneDayTime 某特殊日期
	 * @param earlyDay 提前的天数
	 * @return
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
	 * @param m
	 * @throws ParseException 
	 */
	public boolean judgeProduce(Rule r,Employee e,Mail m) throws ParseException{
		//生成"yyyy-MM-dd"的当前时间
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowdayTime = dateFormat.format(new Date());
		Date today = dateFormat.parse(nowdayTime);
		//转换为日历类型并加上7天，再转换为普通的Date类型。
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, 7);
		Date compareDay = c.getTime();
		//拟发送时间如果比当前时间加七天早，并且比今天晚，返回true
		return compareDay.after(m.getPlanSendTime()) &&
				   (m.getPlanSendTime().after(today));
	}
	
	//判断1：该 "规则" 对应 "该名员工" 是否已存在；
	public boolean judgeExistProduce(Rule r,Employee e,Mail m) throws ParseException{
		//如果该“业务”对应“员工”在邮件表中已存在，则不重复生成。
		m.setEmployeeId(e.getId());
		m.setRuleId(r.getId());
		//如果该规则为“禁用”状态在，则不生成邮件。
		if(r.getIsUse()==0){
			return false;
		}
		//如果存在则不判断
		if(mm.selectByEidRid(m)==0){
			System.out.println("1:该名员工为"+e.getId()+"该规则为"+r.getId());
			System.out.println("查询得到的总数"+mm.selectByEidRid(m));
			return judgeDateProduce(m);
		}
		return false;
	}
	
	//判断2：是否在发送时间；
	public boolean judgeDateProduce(Mail m) throws ParseException{
		//生成"yyyy-MM-dd"的当前时间
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String nowdayTime = dateFormat.format(new Date());
				Date today = dateFormat.parse(nowdayTime);
				//转换为日历类型并加上7天，再转换为普通的Date类型。
				Calendar c = Calendar.getInstance();
				c.setTime(today);
				c.add(Calendar.DAY_OF_MONTH, 7);
				Date compareDay = c.getTime();
				//拟发送时间如果比当前时间加七天早，并且比今天晚，返回true
				return compareDay.after(m.getPlanSendTime()) &&
						   (m.getPlanSendTime().after(today));
	}

	
}