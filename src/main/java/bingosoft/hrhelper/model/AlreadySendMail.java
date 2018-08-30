package bingosoft.hrhelper.model;

import java.util.Date;

public class AlreadySendMail {
    private String id;

    private String mailName;

    private String recipient;

    private String recipientAddress;

    private String sender;

    private String senderAddress;

    private String copyPeople;

    private String copyPeopleAddress;

    private String mailAttachmentPath;

    private String employeeId;

    private String ruleId;

    private String operationId;

    private String approveId;

    private Integer status;

    private String updateBy;

    private Date createTime;

    private Date planSendTime;

    private Date sendTime;

    private String mailContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName == null ? null : mailName.trim();
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress == null ? null : recipientAddress.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress == null ? null : senderAddress.trim();
    }

    public String getCopyPeople() {
        return copyPeople;
    }

    public void setCopyPeople(String copyPeople) {
        this.copyPeople = copyPeople == null ? null : copyPeople.trim();
    }

    public String getCopyPeopleAddress() {
        return copyPeopleAddress;
    }

    public void setCopyPeopleAddress(String copyPeopleAddress) {
        this.copyPeopleAddress = copyPeopleAddress == null ? null : copyPeopleAddress.trim();
    }

    public String getMailAttachmentPath() {
        return mailAttachmentPath;
    }

    public void setMailAttachmentPath(String mailAttachmentPath) {
        this.mailAttachmentPath = mailAttachmentPath == null ? null : mailAttachmentPath.trim();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId == null ? null : operationId.trim();
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId == null ? null : approveId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent == null ? null : mailContent.trim();
    }
}