package bingosoft.hrhelper.model;

import java.util.Date;

public class Rule {
    private String id;

    private String ruleName;

    private Integer sendingCounts;

    private Date sendingInterval;

    private String operationId;

    private String modelId;

    private String ruleMethod;

    private Integer sendingMinofhour;

    private Integer sendingHourofday;

    private Integer entryDistanceY;

    private Integer entryDistanceM;

    private Integer entryDistanceD;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String earlyDay;

    private String entryDistance;

    private String specialdayDistance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Integer getSendingCounts() {
        return sendingCounts;
    }

    public void setSendingCounts(Integer sendingCounts) {
        this.sendingCounts = sendingCounts;
    }

    public Date getSendingInterval() {
        return sendingInterval;
    }

    public void setSendingInterval(Date sendingInterval) {
        this.sendingInterval = sendingInterval;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId == null ? null : operationId.trim();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public String getRuleMethod() {
        return ruleMethod;
    }

    public void setRuleMethod(String ruleMethod) {
        this.ruleMethod = ruleMethod == null ? null : ruleMethod.trim();
    }

    public Integer getSendingMinofhour() {
        return sendingMinofhour;
    }

    public void setSendingMinofhour(Integer sendingMinofhour) {
        this.sendingMinofhour = sendingMinofhour;
    }

    public Integer getSendingHourofday() {
        return sendingHourofday;
    }

    public void setSendingHourofday(Integer sendingHourofday) {
        this.sendingHourofday = sendingHourofday;
    }

    public Integer getEntryDistanceY() {
        return entryDistanceY;
    }

    public void setEntryDistanceY(Integer entryDistanceY) {
        this.entryDistanceY = entryDistanceY;
    }

    public Integer getEntryDistanceM() {
        return entryDistanceM;
    }

    public void setEntryDistanceM(Integer entryDistanceM) {
        this.entryDistanceM = entryDistanceM;
    }

    public Integer getEntryDistanceD() {
        return entryDistanceD;
    }

    public void setEntryDistanceD(Integer entryDistanceD) {
        this.entryDistanceD = entryDistanceD;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEarlyDay() {
        return earlyDay;
    }

    public void setEarlyDay(String earlyDay) {
        this.earlyDay = earlyDay == null ? null : earlyDay.trim();
    }

    public String getEntryDistance() {
        return entryDistance;
    }

    public void setEntryDistance(String entryDistance) {
        this.entryDistance = entryDistance == null ? null : entryDistance.trim();
    }

    public String getSpecialdayDistance() {
        return specialdayDistance;
    }

    public void setSpecialdayDistance(String specialdayDistance) {
        this.specialdayDistance = specialdayDistance == null ? null : specialdayDistance.trim();
    }
}