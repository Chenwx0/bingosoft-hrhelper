package bingosoft.hrhelper.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.Mail;

public class MailSendService {
	@Autowired
	MailUtil mu;
	@Autowired
	MailMapper mm;
	
	/**
	 * 需要从Mail表拿到拟运行时间 并监控
	 */
	@Scheduled(cron = "0 5 * * * ? ")
	public void sendTimer( ){
		
		for(Mail mail : mm.listAll()){
			//如果当前时间超过拟发送时间
			if(new Date().after(mail.getPlanSendTime())){
				sendMail(mail);
			}
		}
	}
	
	public void sendMail(Mail mail){
		try {
			//填写发件人信息
			mu.setSenderAccount("填发件人账号");
			mu.setSenderAddress("填发件人地址");
			mu.setSenderPassword("填发件人密码");
			
			//设置接收人和内容
			mu.setRecipientAddresses(mail.getRecipientAddress()); //设置收件人地址
			mu.setCopyToAddresses(mail.getCopyPeopleAddress());//设置抄送人地址
			mu.setContent(mail.getMailContent());						  //设置邮件内容
			
			/*mu.setAttachmentPaths(mail.getMailAttachmentPath());*///设置附件路径
			
			mu.sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
