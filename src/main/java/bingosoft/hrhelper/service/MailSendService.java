package bingosoft.hrhelper.service;

import java.util.Date;






import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.CancelRecordMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.AlreadySendMail;
import bingosoft.hrhelper.model.CancelRecord;
import bingosoft.hrhelper.model.Mail;

/**
 * @创建人 zhangyx
 * @功能描述 邮件发送服务
 * @创建时间 2018-08-03 14:08:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Service
public class MailSendService {
	
	MailUtil mu = new MailUtil();
	AlreadySendMail asm = new AlreadySendMail();
	
	@Autowired
	MailMapper mm;
	@Autowired
	CancelRecordMapper crm;
	@Autowired
	AlreadySendMailMapper asmm;
	
	/**
	 * 需要从Mail表拿到拟运行时间 并监控
	 */
	@Test
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void sendTimer( ){
		for(Mail mail : mm.listAll()){
			//如果当前时间超过拟发送时间
			System.out.println("一分钟到了__"+new Date());
			if(new Date().after(mail.getPlanSendTime())){
				//发送邮件
				sendMail(mail);
				System.out.println("正在发送"+mail.getMailName()+"到员工"+mail.getRecipient());
			}
		}
	}
	
	public void sendMail(Mail mail){
		try {
			//填写发件人信息
			mu.setSenderAccount("zhangyx@bingosoft.net");
			mu.setSenderAddress("zhangyx@bingosoft.net");
			mu.setSenderPassword("user@2018");
			
			//设置接收人和内容
			mu.setSubject(mail.getMailName());
			mu.setRecipientAddresses(mail.getRecipientAddress()); //设置收件人地址
			mu.setCopyToAddresses(mail.getCopyPeopleAddress());//设置抄送人地址
			mu.setContent(mail.getMailContent());						  //设置邮件内容
			/*mu.setAttachmentPaths(mail.getMailAttachmentPath());*///设置附件路径
			
			mu.sendMail();
			addAlreadySendMail(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将邮件表中已经发送的邮件 转移 到已发送邮件表中
	 * @param mail
	 */
	public void addAlreadySendMail(Mail mail){
		//此处应该加锁!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//在邮件表中删除该邮件
		asm.setId(mail.getId());
		asm.setAppoveId(mail.getAppoveId());
		asm.setCopyPeople(mail.getCopyPeople());
		asm.setCreateTime(mail.getCreateTime());
		asm.setMailAttachmentPath(mail.getMailAttachmentPath());
		asm.setMailContent(mail.getMailContent());
		asm.setMailName(mail.getMailName());
		asm.setOperationId(mail.getOperationId());
		asm.setRecipient(mail.getRecipient());
		asm.setRecipientAddress(mail.getRecipientAddress());
		asm.setSender(mail.getSender());
		asm.setSendTime(new Date());
		asm.setStatus(mail.getStatus());
		asm.setUpdateBy(mail.getUpdateBy());
		
		mm.deleteByPrimaryKey(mail.getId());
		asmm.insert(asm);
	}
	
	/**
	 * 取消发送邮件
	 * @param mail
	 */
	@Test
	public void cancelSendMail(String id){
		//将邮件表中该邮件的状态修改 为0：取消发送
		Mail mail = mm.selectByPrimaryKey(id);
		mail.setStatus(0);
		mm.updateByPrimaryKey(mail);
		
		//将该邮件添加到已发送邮件中
		addAlreadySendMail(mail);
		
		//将改邮件信息储存到取消发送记录表
		CancelRecord cr = new CancelRecord();
		cr.setId(UUID.randomUUID().toString());
		cr.setOperationId(mail.getOperationId());
		cr.setPlanSendTime(mail.getPlanSendTime());
		cr.setRecipientAddress(mail.getRecipientAddress());
		
		crm.insert(cr);
	}
}
