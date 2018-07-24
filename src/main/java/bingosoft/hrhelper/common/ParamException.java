package bingosoft.hrhelper.common;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-23 15:46:46
 */
public class ParamException extends Exception{


    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
