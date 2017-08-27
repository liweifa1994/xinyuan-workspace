package cn.xinyuan.sso.service;

import cn.xinyuan.common.util.XinYuanResult;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:25
 * Mail:josli@kargocard.com
 */
public interface LoginService {

    /**
     * 该接口用于用户登录
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
   XinYuanResult userLogin(String username, String password);

    /**
     * 用于用户注销
      * @param token
     * @return
     */
    XinYuanResult userLogout(String token);
}
