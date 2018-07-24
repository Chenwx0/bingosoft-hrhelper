package bingosoft.hrhelper.model;

public class Operation {
    private Integer id;

    private String opeartionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpeartionName() {
        return opeartionName;
    }

    public void setOpeartionName(String opeartionName) {
        this.opeartionName = opeartionName == null ? null : opeartionName.trim();
    }
}