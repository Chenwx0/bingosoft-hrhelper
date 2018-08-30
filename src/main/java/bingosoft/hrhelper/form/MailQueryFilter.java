package bingosoft.hrhelper.form;

/**
 * @创建人 chenwx
 * @功能描述 邮件过滤参数表单
 * @创建时间 2018-08-30
 */
public class MailQueryFilter {

    Integer pageNum;

    Integer pageSize;

    Integer status;

    private String operationId;

    private String employeeName;

    private String department;

    private String recruitClass;

    private String entryDayStart;

    private String entryDayEnd;

    private String contractDayStart;

    private String contractDayEnd;

    private String planFullmenberDayStart;

    private String planFullmenberDayEnd;

    private String orderBy;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getRecruitClass() {
        return recruitClass;
    }

    public void setRecruitClass(String recruitClass) {
        this.recruitClass = recruitClass;
    }

    public String getEntryDayStart() {
        return entryDayStart;
    }

    public void setEntryDayStart(String entryDayStart) {
        this.entryDayStart = entryDayStart;
    }

    public String getEntryDayEnd() {
        return entryDayEnd;
    }

    public void setEntryDayEnd(String entryDayEnd) {
        this.entryDayEnd = entryDayEnd;
    }

    public String getContractDayStart() {
        return contractDayStart;
    }

    public void setContractDayStart(String contractDayStart) {
        this.contractDayStart = contractDayStart;
    }

    public String getContractDayEnd() {
        return contractDayEnd;
    }

    public void setContractDayEnd(String contractDayEnd) {
        this.contractDayEnd = contractDayEnd;
    }

    public String getPlanFullmenberDayStart() {
        return planFullmenberDayStart;
    }

    public void setPlanFullmenberDayStart(String planFullmenberDayStart) {
        this.planFullmenberDayStart = planFullmenberDayStart;
    }

    public String getPlanFullmenberDayEnd() {
        return planFullmenberDayEnd;
    }

    public void setPlanFullmenberDayEnd(String planFullmenberDayEnd) {
        this.planFullmenberDayEnd = planFullmenberDayEnd;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
