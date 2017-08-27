package cn.xinyuan.sso.service;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbUser;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:16
 * Mail:josli@kargocard.com
 */
public interface RegisterService {

    /**
     * 用来判断用户填写的信息是否被人使用过 填写的信息可能是用户名
     * 或者手机号码
     * 或者电子邮箱
     * @param param
     * @return
     */
    XinYuanResult checkData(String param);

    /**
     * 注册用户信息
     * @param user
     * @return
     */
    XinYuanResult register(TbUser user);
}
