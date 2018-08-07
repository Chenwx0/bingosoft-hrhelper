package bingosoft.hrhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.ApproveMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.model.AlreadySendMail;
import bingosoft.hrhelper.model.Approve;
import bingosoft.hrhelper.model.Operation;

@Service
public class ApproveService {
	
	MailUtil mu = new MailUtil();
	AlreadySendMail asm = new AlreadySendMail();
	
	@Autowired
	ApproveMapper am;
	@Autowired
	OperationMapper om;
	
	// 经理点击按钮的时候，调用approve表中的status更改为0(待审核)/1(已通过)/2(未通过)
	public void sendStatus(Approve a){
		//若未通过，则更新审批表。
		if(a.getStatus()==2){
			am.updateByPrimaryKey(a);
		}
		//如果通过，则更新审批表，并触发邮件。
		else if(a.getStatus()==1){
			am.updateByPrimaryKey(a);
			sendApproveMail(a);
		}
	}
	
	/**
	 * 方法：审批并发送单封邮件
	 */
	public void sendApproveMail(Approve a){
		try {
			//
			MailUtil mu = new MailUtil();
			String operationName = om.selectByPrimaryKey(a.getId()).getOperationName();
			
			mu.setSubject(a.getApproveObject()+operationName);
			mu.setContent("从邮件模板表中得到"); 
			mu.sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//工作流需要传进来什么？
	//通过工作流传进来一个审批ID
}
