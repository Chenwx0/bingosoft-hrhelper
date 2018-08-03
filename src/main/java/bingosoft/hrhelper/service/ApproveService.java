package bingosoft.hrhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bingosoft.hrhelper.common.MailUtil;
import bingosoft.hrhelper.mapper.AlreadySendMailMapper;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.model.AlreadySendMail;

@Service
public class ApproveService {
	
	MailUtil mu = new MailUtil();
	AlreadySendMail asm = new AlreadySendMail();
	
	@Autowired
	MailMapper mm;
	@Autowired
	AlreadySendMailMapper asmm;
	
	// 经理点击按钮的时候，调用mail表中的status更改为2(已通过)/3(未通过)
	public void sendStatus(String id){
		asm = asmm.selectByPrimaryKey(id);
		asm.setStatus(2);
	}
	// 如果是2，则调用邮件生成方法，并调用邮件发送方法。
	// 生成
	// 发送
	
	// 如果是3，则邮件状态改变。
	
}
