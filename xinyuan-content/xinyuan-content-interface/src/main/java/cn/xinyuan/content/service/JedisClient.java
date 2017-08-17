package cn.xinyuan.content.service;

/**
 * User:josli li
 * Date:17/8/17
 * Time:下午6:27
 * Mail:josli@kargocard.com
 */
public interface JedisClient {
    /**
     * 存储一个key-value
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);

    /**
     * 根据key 获取value
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 判断key是否存在 存在为true 否则返回 false
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 设置key 的生存周期 单位为秒
     * @param key
     * @param seconds
     * @return
     */
    Long expire(String key, int seconds);

    /**
     * 判断key 是否存在 存在返回-1 否则 返回-2
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 设置key 对应的value 自增一
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 命令用于为哈希表中的字段赋值
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hset(String key, String field, String value);

    /**
     * 命令用于为哈希表中的字段获取值
     * @param key
     * @param field
     * @return
     */
    String hget(String key, String field);

    /**
     * 用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略
     * @param key
     * @param field
     * @return
     */
    Long hdel(String key, String... field);

}
