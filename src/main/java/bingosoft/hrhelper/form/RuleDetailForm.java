package bingosoft.hrhelper.form;

/**
 * @创建人 chenwx
 * @功能描述 规则详情表单类
 * @创建时间 2018-08-14 15:13:13
 */
public class RuleDetailForm {

    /**
     * 规则ID
     */
    private String id;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 参考时间(1:入职后 2:合同结束前 3:转正前)
     */
    private String ruleMethod;
    /**
     * 距离参考时间几年
     */
    private Integer distanceY;
    /**
     * 距离参考时间几个月
     */
    private Integer distanceM;
    /**
     * 距离参考时间几天
     */
    private Integer distanceD;
    /**
     * 在一天中哪个小时发送
     */
    private Integer sendingHourofday;
    /**
     * 在一小时中哪一分钟发送
     */
    private Integer sendingMinofhour;
    /**
     * 规则是否启用 0-禁用 1-启用
     */
    private Integer isUse;
    /**
     * 业务ID
     */
    private String operationId;
    /**
     * 模板ID
     */
    private String modelId;
    /**
     * 模板内容
     */
    private String modelContent;
    /**
     * 附件路径
     */
    private String attachmentHref;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleMethod() {
        return ruleMethod;
    }

    public void setRuleMethod(String ruleMethod) {
        this.ruleMethod = ruleMethod;
    }

    public Integer getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(Integer distanceY) {
        this.distanceY = distanceY;
    }

    public Integer getDistanceM() {
        return distanceM;
    }

    public void setDistanceM(Integer distanceM) {
        this.distanceM = distanceM;
    }

    public Integer getDistanceD() {
        return distanceD;
    }

    public void setDistanceD(Integer distanceD) {
        this.distanceD = distanceD;
    }

    public Integer getSendingHourofday() {
        return sendingHourofday;
    }

    public void setSendingHourofday(Integer sendingHourofday) {
        this.sendingHourofday = sendingHourofday;
    }

    public Integer getSendingMinofhour() {
        return sendingMinofhour;
    }

    public void setSendingMinofhour(Integer sendingMinofhour) {
        this.sendingMinofhour = sendingMinofhour;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelContent() {
        return modelContent;
    }

    public void setModelContent(String modelContent) {
        this.modelContent = modelContent;
    }

    public String getAttachmentHref() {
        return attachmentHref;
    }

    public void setAttachmentHref(String attachmentHref) {
        this.attachmentHref = attachmentHref;
    }
}
