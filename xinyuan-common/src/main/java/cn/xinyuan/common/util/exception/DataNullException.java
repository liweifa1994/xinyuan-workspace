package cn.xinyuan.common.util.exception;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午4:00
 * Mail:josli@kargocard.com
 */
public class DataNullException extends RuntimeException {

    public DataNullException() {
    }

    public DataNullException(String message) {
        super(message);
    }
}
