package com.sf.demo.cache;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Cache {

    byte[] rawKey(String key);

    byte[] rawValue(Object val);

    Object deserValue(byte[] bytes);

    Long incr(String key);

    List<Long> incr(List<String> keys);

    Long incrBy(String key, long delta);

    boolean exists(String key);

    void set(String key, Object obj, long activeTime);

    void set(String key, Object obj);

    void delete(String key);

    void delete(String... keys);

    Object get(String key);

    /**
     * 获取指定key, 以Long类型返回
     * @param key 存储数值
     * @param nullAsZero null是否处理为0
     * @return null如果可以不存在或者不是数值
     */
    Long getAsLong(String key, boolean nullAsZero);

    List<Long> getAsLong(List<String> keys, boolean nullAsZero);

    <T> T get(String key, Class<T> clazz);

    Object[] mGet(String... keys);

    List<Object> mGet(List<String> keys);

    <T> List<T> mGet(List<String> keys, Class<T> clazz);

    // hash
    Object hGet(String hash, String key);

    Long hGetAsLong(String hash, String key, boolean nullAsZero);

    Map<String, Object> hGetAll(String hash);

    Long hDel(String hash, String key);

    List<Object> hMGet(String hash, List<String> keys);

    List<Long> hMGetAsLong(String hash, List<String> keys, boolean nullAsZero);

    List<Object> hMGet(String hash, String[] keys);

    // 返回值仅用于判断是新增加键值对, 或是更新了已有键值对
    void hSet(String hash, String key, Object obj);

    void hMSet(String hash, Map<String, Object> kvs);

    long hIncrBy(String hash, String key, long delta);

    Object execCallback(RedisCallback callback);

    // callback不能返回非NULL值
    List<Object> execPipelined(RedisCallback callback);

    // 允许执行事务
    List<Object> executeSessionCallback(SessionCallback callback);

    boolean setNX(String key, Object obj);

//    boolean setNX(byte[] key, byte[] value);

    Set<String> keys(String ptrn);

    boolean setNX(String key, Object obj, long activeTime);

    boolean hsetNX(String key, String field, Object obj);

    Long hDel(String key, List<String> keys);

    boolean expire(String key, long seconds);

    // 指定member的分数 + scoreDelta
    Double zIncrBy(String key, Object member, double scoreDelta);

    /**
     * Note:
     * 1. zset按分数从低到高排序
     * 2. 下标从0开始, -1 表示最后一个, -2表示倒数第二个; 一次类推
     * 3. 示例: start = 0, stop = -1 表示获取所有的
     * @param key
     * @param start
     * @param end
     * @return 如果没有找到 返回空(非null)
     */
    <T> List<T> zRange(String key, long start, long end);

    // 最后一个的序号是0, 倒数第二个序号为1, 以此类推
    <T> List<T> zRevRange(String key, long start, long end);

//    <T> List<T> zRevRange(String key, long start, long end, Class<T> clazz);

    List<Long> zRevRangeAsLong(String key, long start, long end);

    <T> List<T> zRangeByScore(String key, double min, double max);
    <T> List<T> zRangeByScore(String key, double min, double max, long offset, long count);

    <T> Map<T, Double> zRangeByScoreWithScores(String key, double min, double max);
    <T> Map<T, Double> zRangeByScoreWithScores(String key, double min, double max, long offset, long count);

    <T> List<T> zRevRangeByScore(String key, double min, double max);
    <T> List<T> zRevRangeByScore(String key, double min, double max, long offset, long count);

    <T> Map<T, Double> zRevRangeByScoreWithScores(String key, double min, double max);
    <T> Map<T, Double> zRevRangeByScoreWithScores(String key, double min, double max, long offset, long count);

    Long zRemRangeByScore(String key, double min, double max);

    /**
     *
     * @param key
     * @param start
     * @param end
     * @return Ordered Map
     */
    <T> Map<T, Double> zRangeWithScore(String key, long start, long end);

    <T> Map<T, Double> zRevRangeWithScore(String key, long start, long end);

    <T> Long zRem(String key, List<T> memberList);

    <T> Long zRem(String key, T[] members);

    <T> Long zRem(String key, T member);

    Long zLen(String key);

    List<Long> zLen(List<String> keys);

    // True 新增 False 更新
    <T> Boolean zAdd(String key, T member, Double score);

    // 返回新增的个数
    <T> Long zAdd(String key, List<T> memberList, List<Double> scoreList);

    /**
     * @param key
     * @param member
     * @param <T>
     * @return 返回null 如果key或者member不存在
     */
    <T> Double zScore(String key, T member);

    /**
     * 判断某个成员是否存在
     * @param key
     * @param member
     * @param <T>
     * @return
     */
    <T> boolean zIsMember(String key, T member);

    <T> List<Boolean> zIsMember(String key, List<T> memberList);

    <T> List<Boolean> zIsMember(List<String> keyList, List<T> memberList);

    <T> List<Boolean> zIsMember(List<String> keys, T member);

    /************************* List Operations *************************/

    /**
     * 从list头部加入
     * @param key
     * @param objs
     * @return push之后list中元素的个数
     */
    <T> Long lPush(String key, List<T> objs);

    <T> Long lPush(String key, T... objs);

    /**
     *
     * @param key
     * @param start 从0开始
     * @param stop
     *
     * lTrim 0 2, 保留list前三个元素
     */
    void lTrim(String key, long start, long stop);

    /**
     * 从list头部读取数据
     * @param key
     * @param start
     * @param stop
     * @return lRange 0 2, 读取list前三个元素
     */
    <T> List<T> lRange(String key, long start, long stop);

    /**
     * 列表长度
     * @param key
     * @return 如果对应的列表不存在, 返回0
     */
    Long lLen(String key);

//    List<Object> lRange(String key, int start, int stop);

    Object rPop(String key);

    <T> T rPop(String key, Class<T> clazz);




    /************************* Set  Operations *************************/

    /**
     *
     * @param key
     * @param objList
     * @return 返回实际新增的个数, 不包括已存在的
     */
    <T> Long sAdd(String key, List<T> objList);

    <T> Long sAdd(String key, T... objs);

    <T> Long sRem(String key, T... objs);

    boolean sIsMember(String key, Object obj);

    <T> List<Boolean> sIsMember(String key, List<T> objList);

    <T> List<Boolean> sIsMember(List<String> keyList, List<T> objList);

}
