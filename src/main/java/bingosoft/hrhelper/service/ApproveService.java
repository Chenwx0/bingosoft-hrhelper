package bingosoft.hrhelper.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.ApproveMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.model.AlreadySendMail;
import bingosoft.hrhelper.model.Approve;
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
	
	MailUtil mu = new MailUtil();
	
	@Autowired
	ApproveMapper am;
	@Autowired
	OperationMapper om;
	
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
				am.updateByPrimaryKey(a);
				sendApproveMail(a);
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
		try {
			//
			MailUtil mu = new MailUtil();
			String operationName = om.selectByPrimaryKey(a.getId()).getOperationName();
			
			mu.setSubject(a.getApproveObject()+operationName);
			mu.setContent("从邮件模板表中得到"); 
			mu.setRecipientAddresses("18826108872@163.com");
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
}