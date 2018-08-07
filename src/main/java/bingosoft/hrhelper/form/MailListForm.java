package bingosoft.hrhelper.form;

import bingosoft.hrhelper.model.Rule;
import leap.core.doc.annotation.Doc;

import java.util.Date;

/**
 * @创建人 chenwx
 * @功能描述 邮件列表展示表单
 * @创建时间 2018-08-04 15:41:41
 */
public class MailListForm {

    /**
     * 邮件ID
     */
    @Doc("邮件ID")
    private String id;
    /**
     * 邮件主题
     */
    @Doc("邮件主题")
    private String mailName;
    /**
     * 邮件接收人
     */
    @Doc("邮件接收人")
    private String recipient;
    /**
     * 邮件抄送人
     */
    @Doc("邮件抄送人")
    private String copyPeople;
    /**
     * 邮件业务类别
     */
    @Doc("邮件业务类别")
    private String operation;
    /**
     * 计划发送时间
     */
    @Doc("计划发送时间")
    private Date planSendTime;
    /**
     * 发送时间
     */
    @Doc("发送时间")
    private Date sendTime;
    /**
     * 业务负责人
     */
    @Doc("业务负责人")
    private String principal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCopyPeople() {
        return copyPeople;
    }

    public void setCopyPeople(String copyPeople) {
        this.copyPeople = copyPeople;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getPlanSendTime() {
        return planSendTime;
    }

    public void setPlanSendTime(Date planSendTime) {
        this.planSendTime = planSendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
