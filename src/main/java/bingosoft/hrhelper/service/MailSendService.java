package bingosoft.hrhelper.service;

import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.Mail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Service
public class MailSendService {
	MailUtil mu = new MailUtil();
	@Autowired
	MailMapper mm;
	
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
