package bingosoft.hrhelper.model;

import java.util.Date;

public class Rule {
    private Integer id;

    private String ruleName;

    private Date sendingTime;

    private String sendingCounts;

    private String sendingInterval;

    private Date entryDistance;

    private Integer operationId;

    private Integer modelId;

    private String ruleMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getSendingCounts() {
        return sendingCounts;
    }

    public void setSendingCounts(String sendingCounts) {
        this.sendingCounts = sendingCounts == null ? null : sendingCounts.trim();
    }

    public String getSendingInterval() {
        return sendingInterval;
    }

    public void setSendingInterval(String sendingInterval) {
        this.sendingInterval = sendingInterval == null ? null : sendingInterval.trim();
    }

    public Date getEntryDistance() {
        return entryDistance;
    }

    public void setEntryDistance(Date entryDistance) {
        this.entryDistance = entryDistance;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getRuleMethod() {
        return ruleMethod;
    }

    public void setRuleMethod(String ruleMethod) {
        this.ruleMethod = ruleMethod == null ? null : ruleMethod.trim();
    }
}