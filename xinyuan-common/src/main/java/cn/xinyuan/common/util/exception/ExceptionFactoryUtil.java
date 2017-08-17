package cn.xinyuan.common.util.exception;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午4:05
 * Mail:josli@kargocard.com
 */
public class ExceptionFactoryUtil {

    /**
     * 数据格式错误异常
     * @param msg
     * @return
     */
    public static DataFromatErrorException createDataFromatErrorException(String msg){
        throw new DataFromatErrorException(msg);
    }

    /**
     * 数据为空异常
     * @param msg
     * @return
     */
    public static DataNullException createDataNullException(String msg){
        throw new DataNullException(msg);
    }

    /**
     * 返回结果为空
     * @param msg
     * @return
     */
    public static ResultException createResultException(String msg){
        throw new ResultException(msg);
    }
}
