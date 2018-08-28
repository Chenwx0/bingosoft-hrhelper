package bingosoft.hrhelper.service;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.DateTransferUtils;
import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.ApproveMapper;
import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.model.AlreadySendMail;
import bingosoft.hrhelper.model.Approve;
import bingosoft.hrhelper.model.Employee;
import bingosoft.hrhelper.model.Operation;

/**
 * @创建人 zhangyx
 * @功能描述 审批流业务
 * @创建时间 2018-08-03 14:08:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Service
public class ApproveService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	MailUtil mu = new MailUtil();
	
	@Autowired
	ApproveMapper am;
	@Autowired
	OperationMapper om;
	@Autowired
	EmployeeMapper em;
	@Autowired
	AlreadySendMailMapper asmm;
	@Autowired
	CreateMailContentService cmcs;
	
	// 经理点击按钮的时候，调用approve表中的status更改为0(待审核)/1(已通过)/2(未通过)
	public boolean sendStatus(Approve a){
		try{
			//若未通过，则更新审批表。
			if(a.getStatus()==2){
				am.updateByPrimaryKey(a);
				return false;
			}
			//如果通过，则更新审批表，并触发邮件。
			else if(a.getStatus()==1){
				sendApproveMail(a);
				am.updateByPrimaryKey(a);
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 方法：审批并发送单封邮件
	 */
	public void sendApproveMail(Approve a){
		//取得该审批对应业务业务
		String isSpecial = null;
		try {
			isSpecial = om.selectByPrimaryKey(a.getOperationId()).getIsSpecial();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		//处理转正业务，直接发送下一封邮件
		if(isSpecial.equals("1")){
			fullMember(a);
		}
		if(isSpecial.equals("2")){
			contractApprove(a);
		}
	}
	
	/**
	 * 方法：审批并待发送单封邮件__合同续签
	 * @param a
	 */
	private void contractApprove(Approve a) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 方法：审批并发送单封邮件__转正业务
	 * @param a
	 */
	public void fullMember(Approve a){
		try {
			MailUtil mu = new MailUtil();
			Employee e = em.selectByPrimaryKey(a.getApproveObject());
			String operationName = om.selectByPrimaryKey(a.getId()).getOperationName();
			mu.setSubject(e.getName()+operationName);
			//根据规则方法与员工信息 生成邮件模板
			mu.setContent(cmcs.getMailContent(e.getId(),"转正通过的邮件模板ID", DateTransferUtils.dateTimeFormat(e.getFullmenberDay()),"截止日期"));
			
			mu.setRecipientAddresses(e.getMail());
			mu.sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//工作流需要传进来什么？
	//通过工作流传进来一个审批ID
	
	
	@Test
    public void mailApprove(){
    	Approve a = new Approve();
    	a.setId("1");
    	a.setApproveObject("审批对象");
    	a.setApprover("经理");
    	a.setCreateTime(new Date());
    	a.setOperationId("1");
    	a.setStatus(1);
    	
	    sendStatus(a);
	}

	/**
	 * 经理点击同意后，提醒员工填写申请续签合同
	 * @param mailContent
	 * @param employeeAddress
	 */
	public void sendContractMail(String mailContent, String employeeId) {
		try {
			mu.setSubject("合同续签申请提醒");
			mu.setRecipientAddresses(employeeId);
			mu.setContent(mailContent);
			mu.sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 通过经理姓名检索他的审批历史记录
	 * @param approve_name
	 */
	public Result getHistoryRecord(String approve_name) {
		return am.getHistoryRecord(approve_name);
	}
}
