package bingosoft.hrhelper.model;

import java.util.Date;

public class Employee {
    private String id;

    private String name;

    private String mail;

    private String department;

    private String manager;

    private Date fullmenberDay;

    private Date planFullmenberDay;

    private Date entryDay;

    private String recruitClass;

    private Integer signCount;

    private Integer dayOfWork;

    private Integer yearOfWork;

    private Date contractDay;

    private String signClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public Date getFullmenberDay() {
        return fullmenberDay;
    }

    public void setFullmenberDay(Date fullmenberDay) {
        this.fullmenberDay = fullmenberDay;
    }

    public Date getPlanFullmenberDay() {
        return planFullmenberDay;
    }

    public void setPlanFullmenberDay(Date planFullmenberDay) {
        this.planFullmenberDay = planFullmenberDay;
    }

    public Date getEntryDay() {
        return entryDay;
    }

    public void setEntryDay(Date entryDay) {
        this.entryDay = entryDay;
    }

    public String getRecruitClass() {
        return recruitClass;
    }

    public void setRecruitClass(String recruitClass) {
        this.recruitClass = recruitClass == null ? null : recruitClass.trim();
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getDayOfWork() {
        return dayOfWork;
    }

    public void setDayOfWork(Integer dayOfWork) {
        this.dayOfWork = dayOfWork;
    }

    public Integer getYearOfWork() {
        return yearOfWork;
    }

    public void setYearOfWork(Integer yearOfWork) {
        this.yearOfWork = yearOfWork;
    }

    public Date getContractDay() {
        return contractDay;
    }

    public void setContractDay(Date contractDay) {
        this.contractDay = contractDay;
    }

    public String getSignClass() {
        return signClass;
    }

    public void setSignClass(String signClass) {
        this.signClass = signClass == null ? null : signClass.trim();
    }

    public String getValue(String field){
        String result = "";
        if (field.equals("id"))
            result = this.id;
        if (field.equals("name"))
            result = this.name;
        if (field.equals("mail"))
            result = this.mail;
        if (field.equals("department"))
            result = this.department;
        if (field.equals("manager"))
            result = this.manager;
        if (field.equals("fullmenberDay"))
            result = this.fullmenberDay.toString();
        if (field.equals("planFullmenberDay"))
            result = this.planFullmenberDay.toString();
        if (field.equals("entryDay"))
            result = this.entryDay.toString();
        if (field.equals("recruitClass"))
            result = this.recruitClass;
        if (field.equals("signCount"))
            result = this.signCount.toString();
        if (field.equals("dayOfWork"))
            result = this.dayOfWork.toString();
        if (field.equals("yearOfWork"))
            result = this.yearOfWork.toString();
        if (field.equals("contractDay"))
            result = this.contractDay.toString();
        if (field.equals("signClass"))
            result = this.signClass;
        return result;
    }
}