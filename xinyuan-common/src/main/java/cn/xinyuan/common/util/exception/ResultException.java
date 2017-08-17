package cn.xinyuan.common.util.exception;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午4:16
 * Mail:josli@kargocard.com
 */
public class ResultException extends RuntimeException{
    public ResultException() {
        super();
    }

    public ResultException(String message) {
        super(message);
    }
}
