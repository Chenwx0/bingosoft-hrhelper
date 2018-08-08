package bingosoft.hrhelper.form;

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
    private String id;
    /**
     * 邮件主题
     */
    private String mailName;
    /**
     * 邮件内容
     */
    private String mailContent;
    /**
     * 附件路径
     */
    private String mailAttachmentPath;
    /**
     * 邮件接收人
     */
    private String recipient;
    /**
     * 邮件接收人邮箱
     */
    private String recipientAddress;
    /**
     * 邮件抄送人
     */
    private String copyPeople;
    /**
     * 邮件抄送人邮箱
     */
    private String copyPeopleAddress;
    /**
     * 邮件业务类别
     */
    private String operation;
    /**
     * 计划发送时间
     */
    private Date planSendTime;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 业务负责人
     */
    private String principal;
    /**
     * 业务负责人邮箱
     */
    private String principalAddress;

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

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getMailAttachmentPath() {
        return mailAttachmentPath;
    }

    public void setMailAttachmentPath(String mailAttachmentPath) {
        this.mailAttachmentPath = mailAttachmentPath;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getCopyPeople() {
        return copyPeople;
    }

    public void setCopyPeople(String copyPeople) {
        this.copyPeople = copyPeople;
    }

    public String getCopyPeopleAddress() {
        return copyPeopleAddress;
    }

    public void setCopyPeopleAddress(String copyPeopleAddress) {
        this.copyPeopleAddress = copyPeopleAddress;
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

    public String getPrincipalAddress() {
        return principalAddress;
    }

    public void setPrincipalAddress(String principalAddress) {
        this.principalAddress = principalAddress;
    }
}
