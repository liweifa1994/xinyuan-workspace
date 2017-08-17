package cn.xinyuan.test.service;

import cn.xinyuan.common.util.exception.DataFromatErrorException;
import cn.xinyuan.common.util.exception.DataNullException;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import org.junit.Test;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午4:09
 * Mail:josli@kargocard.com
 */
public class ExceptionTest {

    @Test
    public void testException(){
        try {
            String hello = hello();
            System.out.println(hello);

        }catch (DataNullException e){
            System.out.println(e.getMessage());
        }
    }
    public String  hello(){
        ExceptionFactoryUtil.createDataNullException("数据为空");
        return "hhh";
    }
}
