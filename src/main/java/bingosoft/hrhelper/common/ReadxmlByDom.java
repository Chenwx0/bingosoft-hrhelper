package bingosoft.hrhelper.common;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import bingosoft.hrhelper.model.MailConfig;

/**
 * 用DOM方式读取xml文件
 * @author lune
 */
public class ReadxmlByDom {
    private static DocumentBuilderFactory dbFactory = null;
    private static DocumentBuilder db = null;
    private static Document document = null;
    private static List<MailUtil> mcs = null;
    private static MailConfig mc = null;
    static{
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public static MailConfig getMailConfig(String fileName) throws Exception{
        //将给定 URI 的内容解析为一个 XML 文档,并返回Document对象
        document = db.parse(fileName);
        mc = new MailConfig();
        
	    //从XML文件中读取内容
	    String senderAccount = 
	    		document.getElementsByTagName("senderAccount").item(0).getFirstChild().getNodeValue();
	    String senderAddress = 
	    		document.getElementsByTagName("senderAddress").item(0).getFirstChild().getNodeValue();
	    String senderPassword = 
	    		document.getElementsByTagName("senderPassword").item(0).getFirstChild().getNodeValue();
	    String mcServerAddress = 
	    		document.getElementsByTagName("mailServerAddress").item(0).getFirstChild().getNodeValue();
	    String mcAuth = 
	    		document.getElementsByTagName("mailAuth").item(0).getFirstChild().getNodeValue();
	    String mcTransferProtocol = 
	    		document.getElementsByTagName("mailTransferProtocol").item(0).getFirstChild().getNodeValue();
	    String mcPort = 
	    		document.getElementsByTagName("mailPort").item(0).getFirstChild().getNodeValue();
	   
	    //添加邮件发送人信息
	    mc.setSenderAccount(senderAccount);
	    mc.setSenderAddress(senderAddress);
	    mc.setSenderPassword(senderPassword);
	    //添加邮件配置信息
	    mc.setMailAuth(mcAuth);
	    mc.setMailPort(mcPort);
	    mc.setMailServerAddress(mcServerAddress);
	    mc.setMailTransferProtocol(mcTransferProtocol);
	    
        return mc;
    }
}