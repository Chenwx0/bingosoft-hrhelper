package bingosoft.hrhelper.common;


/**
 * @创建人 chenwx
 * @功能描述 服务层数据返回实体
 * @创建时间 2018-07-23 0:20:20
 */
public class Result<T>{

    private Boolean isSuccess;//状态
    private String message;//消息
    private T resultEntity;//数据对象

    /**
     * 无参构造器
     */
    public Result(){
        this.isSuccess = true;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(T resultEntity) {
        this.resultEntity = resultEntity;
    }

}
