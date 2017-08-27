package cn.xinyuan.common.util.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:21
 * Mail:josli@kargocard.com
 */
public class XinYuanCommonUtils {
    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否为手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
