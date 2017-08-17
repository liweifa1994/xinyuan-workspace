package cn.xinyuan.common.util.exception;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午4:05
 * Mail:josli@kargocard.com
 */
public class DataFromatErrorException extends RuntimeException {
    public DataFromatErrorException() {
    }

    public DataFromatErrorException(String message) {
        super(message);
    }
}
