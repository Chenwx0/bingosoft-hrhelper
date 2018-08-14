package bingosoft.hrhelper.form;

import java.util.Date;

/**
 * @创建人 chenwx
 * @功能描述 规则列表信息表单
 * @创建时间 2018-08-14 14:32:32
 */
public class RuleListForm {

    /**
     * 规则ID
     */
    private String id;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 规则概述（对应数据库entry_distance字段）
     */
    private String ruleSummary;
    /**
     * 规则更新时间
     */
    private Date updateTime;
    /**
     * 业务名称
     */
    private String operationName;
    /**
     * 业务负责人
     */
    private String principal;

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

    public String getRuleSummary() {
        return ruleSummary;
    }

    public void setRuleSummary(String ruleSummary) {
        this.ruleSummary = ruleSummary;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
