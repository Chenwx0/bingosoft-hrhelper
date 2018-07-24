package bingosoft.hrhelper.model;

public class Approve {
    private Integer id;

    private String approver;

    private String approveObject;

    private String mail;

    private Integer status;

    private Integer operationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getApproveObject() {
        return approveObject;
    }

    public void setApproveObject(String approveObject) {
        this.approveObject = approveObject == null ? null : approveObject.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }
}