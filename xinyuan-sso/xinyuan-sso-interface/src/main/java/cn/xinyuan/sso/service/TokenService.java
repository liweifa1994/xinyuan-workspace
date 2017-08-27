package cn.xinyuan.sso.service;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbUser;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:29
 * Mail:josli@kargocard.com
 */
public interface TokenService {

    /**
     *
     * 根据token获取用户的信息，直接从redis中获取
     * @param token
     * @return
     */
    XinYuanResult getUserByToken(String token);
}
