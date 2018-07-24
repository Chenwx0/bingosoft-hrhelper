package bingosoft.hrhelper.common;

/**
 * @创建人 chenwx
 * @功能描述 服务层数据返回实体
 * @创建时间 2018-07-23 0:20:20
 */
public class Result {

    private Integer code;//状态码
    private Boolean isSuccess;//状态
    private String massege;//消息
    private Object result;//数据对象

    /**
     * 无参构造器
     */
    public Result(){
        this.code = 200;
        this.isSuccess = true;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
