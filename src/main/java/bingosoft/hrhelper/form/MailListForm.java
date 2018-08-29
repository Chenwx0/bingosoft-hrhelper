package bingosoft.hrhelper.form;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date planSendTime;
    /**
     * 发送时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date sendTime;
    /**
     * 业务负责人
     */
    private String principal;
    /**
     * 业务负责人邮箱
     */
    private String principalAddress;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 所属部门
     */
    private String department;
    /**
     * 入职时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date entryDay;
    /**
     * 拟转正时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date planFullmenberDay;
    /**
     * 合同到期时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date contractDay;
    /**
     * 招聘类型
     */
    private String recruitClass;
    /**
     * 审批状态(0-审核中；1-通过；2-未通过)
     */
    private Integer approveStatus;
    /**
     * 是否已发送 0-待发送 1-已发送
     */
    private Integer isSent;

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getEntryDay() {
        return entryDay;
    }

    public void setEntryDay(Date entryDay) {
        this.entryDay = entryDay;
    }

    public Date getPlanFullmenberDay() {
        return planFullmenberDay;
    }

    public void setPlanFullmenberDay(Date planFullmenberDay) {
        this.planFullmenberDay = planFullmenberDay;
    }

    public Date getContractDay() {
        return contractDay;
    }

    public void setContractDay(Date contractDay) {
        this.contractDay = contractDay;
    }

    public String getRecruitClass() {
        return recruitClass;
    }

    public void setRecruitClass(String recruitClass) {
        this.recruitClass = recruitClass;
    }

    public String getApproveStatus() {
        if (approveStatus!=null){
            if (approveStatus==0){
                return "待审核";
            }else if (approveStatus==1){
                return "已通过";
            }else if (approveStatus==2){
                return "未通过";
            }else if (approveStatus==3){
                return "已发送";
            }else {
                return "未知";
            }
        }else {
            return null;
        }
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }
}
