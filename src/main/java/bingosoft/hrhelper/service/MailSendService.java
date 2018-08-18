package bingosoft.hrhelper.service;

import java.sql.SQLException;
import java.util.Date;






import java.util.UUID;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.CancelRecordMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.AlreadySendMail;
import bingosoft.hrhelper.model.CancelRecord;
import bingosoft.hrhelper.model.Mail;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人 zhangyx
 * @功能描述 邮件发送服务
 * @创建时间 2018-08-03 14:08:08
 */
@Service
public class MailSendService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

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
	
	/**
	 * 获取配置并发送邮件
	 */
	public void sendMail(Mail mail){
		try {
			
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
		asm.setApproveId(mail.getApproveId());
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
		asm.setEmployeeId(mail.getEmployeeId());

		try {
			mm.deleteByPrimaryKey(mail.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		asmm.insert(asm);
	}
}
