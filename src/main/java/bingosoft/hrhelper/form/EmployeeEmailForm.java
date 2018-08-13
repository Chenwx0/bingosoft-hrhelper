package bingosoft.hrhelper.form;

/**
 * @创建人 chenwx
 * @功能描述 员工邮箱信息表单
 * @创建时间 2018-08-13 17:33:33
 */
public class EmployeeEmailForm {

    /**
     * 员工ID
     */
    String id;
    /**
     * 员工姓名
     */
    String name;
    /**
     * 员工邮箱
     */
    String mail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
