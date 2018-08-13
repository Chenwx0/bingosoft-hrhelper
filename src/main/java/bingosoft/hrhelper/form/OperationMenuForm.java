package bingosoft.hrhelper.form;

/**
 * @创建人 chenwx
 * @功能描述 业务菜单表单类
 * @创建时间 2018-08-08 11:46:46
 */
public class OperationMenuForm {

    private String id;

    private String operationName;

    private String isSpecial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial == null ? null:isSpecial.trim();
    }
}
