package bingosoft.hrhelper.common;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bingosoft.hrhelper.model.MailConfig;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @创建人 chenwx
 * @功能描述 邮件发送工具
 * @创建时间 2018-07-18 14:08:08
 */
public class MailUtil {
	
    /**
     * 收件人地址，多个以","隔开
     */
    private String recipientAddresses;
    /**
     * 抄送人地址，多个以","隔开
     */
    private String copyToAddresses;
    /**
     * 接口人姓名
     */
    private String liaisonOfficer;
    //邮件标题
    private String subject;
    //邮件正文
    private String content;
    //附件路径
    private List<String> attachmentPaths;
    //邮件发送记录工具
    public static Log logger = LogFactory.getLog(MailUtil.class);
    //邮件配置对象
    static MailConfig mc = new MailConfig();
    	
    public void setRecipientAddresses(String recipientAddresses) {
        this.recipientAddresses = recipientAddresses;
    } 

    public void setCopyToAddresses(String copyToAddresses) {
        this.copyToAddresses = copyToAddresses;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachmentPaths(List<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }

	public void setLiaisonOfficer(String liaisonOfficer) {
		this.liaisonOfficer = liaisonOfficer;
	}

    			
    /**
     * 邮件发送
     * @throws Exception
     */
    public void sendMail() throws Exception {
    	try{
    		mc = ReadxmlByDom.getMailConfig("mailConfig.xml");
	        // 连接邮件服务器的参数配置
	        Properties props = new Properties();
	        // 设置用户的认证方式
	        props.setProperty("mail.smtp.auth", mc.getMailAuth());
	        // 设置传输协议(JavaMail规范要求)
	        props.setProperty("mail.transport.protocol", mc.getMailTransferProtocol());
	        // 设置发件人的SMTP服务器地址
	        props.setProperty("mail.smtp.host", mc.getMailServerAddress());//     网易服务器：smtp.163.com      品高服务器：mail1.bingosoft.net  备注：使用邮箱登录密码应当填写授权码，授权码是用于登录第三方邮件客户端的专用密码。 适用于登录以下服务: POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务。
	        props.setProperty("mail.smtp.port",mc.getMailPort());// 一般端口：25   品高端口：587
	        // 创建定义整个应用程序所需的环境信息的 Session 对象
	        Session session = Session.getInstance(props);
	        // 设置调试信息在控制台打印出来
	        session.setDebug(true);
	        // 创建邮件的实例对象
	        Message msg = getMimeMessage(session);
	        // 根据session对象获取邮件传输对象Transport
	        Transport transport = session.getTransport();
	        // 设置发件人的账户名和密码
	        if(mc.getSenderAccount()==null || mc.getSenderAccount().isEmpty()){
	            throw new ParamException("发件人账户为空");
	        }
	        if(mc.getSenderPassword()==null || mc.getSenderPassword().isEmpty()){
	            throw new ParamException("发件人密码为空");
	        }
	        
	        //设置自定义发件人昵称----------------------为业务接口人名称
	        String nick="";  
	        try {  
	            nick=javax.mail.internet.MimeUtility.encodeText(liaisonOfficer);  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }
	        msg.setFrom(new InternetAddress(nick+" <"+mc.getSenderAddress()+">")); 
	        
	        System.out.println(mc.getSenderAccount()+mc.getSenderPassword());
	        transport.connect(mc.getSenderAccount(), mc.getSenderPassword());
	        // 发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
	        transport.sendMessage(msg,msg.getAllRecipients());
	        logger.info(new Date()+"_邮件发送成功 收件人:"+recipientAddresses+" 抄送人:"+copyToAddresses);
	        logger.info(" 邮件主题:"+subject+" 邮件内容:"+content);
	        logger.error("------------------------------------------不同邮件分割线------------------------------------------");
	        
	        // 关闭邮件连接
	        transport.close();
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("邮件发送失败,收件人:"+recipientAddresses+" 抄送人:"+copyToAddresses);
    		logger.error("邮件主题:"+subject+" 邮件内容:"+content);
    		logger.error("错误信息:"+e.getMessage());
    		logger.error("------------------------------------------不同邮件分割线------------------------------------------");
    	}
	}

    /**
     * 获取邮件的实例对象
     * @param session
     * @return MimeMessage
     * @throws MessagingException
     * @throws AddressException
     */
    private MimeMessage getMimeMessage(Session session) throws Exception{
        // 创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);

        // 设置发件人地址
        if (mc.getSenderAddress()!=null && !mc.getSenderAddress().isEmpty()){
            msg.setFrom(new InternetAddress(mc.getSenderAddress()));
        }else{
            throw new ParamException("发件人地址为空");
        }
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
       if (recipientAddresses!=null && !recipientAddresses.isEmpty()){
           InternetAddress[] recipients = new InternetAddress().parse(recipientAddresses);
           msg.setRecipients(MimeMessage.RecipientType.TO, recipients);
       }else{
            throw new ParamException("收件人地址为空");
        }
        if (copyToAddresses!=null && !copyToAddresses.isEmpty()){
            InternetAddress[] copyTos = new InternetAddress().parse(copyToAddresses);
            msg.setRecipients(MimeMessage.RecipientType.CC,copyTos);
        }
        // 设置邮件主题
        if (subject!=null && !subject.isEmpty()){
            msg.setSubject(subject,"UTF-8");
        }else{
            throw new ParamException("邮件主题为空");
        }
        //设置邮件正文
        // 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        // 混合关系
        mm.setSubType("mixed");
        // 创建附件"节点"
        MimeBodyPart body = new MimeBodyPart();
        if (content!=null && !content.isEmpty()){
            body.setContent(content, "text/html;charset=UTF-8");
        }else{
            throw new ParamException("邮件内容为空");
        }
        mm.addBodyPart(body);     // 如果有多个附件，可以创建多个多次添加
        if(attachmentPaths!=null && attachmentPaths.size()>0){
            for (String attachmentPath: attachmentPaths) {
                // 创建附件"节点"
                MimeBodyPart attachment = new MimeBodyPart();
                // 读取本地文件
                DataHandler dh = new DataHandler(new FileDataSource(attachmentPath));
                // 将附件数据添加到"节点"
                attachment.setDataHandler(dh);
                // 设置附件的文件名（需要编码）
                attachment.setFileName(MimeUtility.encodeText(dh.getName()));
                mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
            }
        }
        // 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        // 设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        msg.saveChanges();
        return msg;
    }
}