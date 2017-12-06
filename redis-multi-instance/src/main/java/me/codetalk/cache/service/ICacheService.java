package me.codetalk.cache.service;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface ICacheService {

    public boolean set(byte[] key, byte[] value, long activeTime);

    public boolean set(String key, Object obj, long activeTime);

    public boolean set(String key, Object obj);

}
