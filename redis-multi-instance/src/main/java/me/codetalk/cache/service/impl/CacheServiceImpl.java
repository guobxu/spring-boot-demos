package me.codetalk.cache.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import me.codetalk.cache.service.ICacheService;

/**
 * Created by Administrator on 2017/4/6.
 */
@Service
public class CacheServiceImpl implements ICacheService {

    @Resource(name = "appRedisTemplate")  
    private RedisTemplate<String, String> redisTemplate;  

    @Override
    public boolean set(byte[] key, byte[] value, long activeTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                boolean rs = true;
                connection.set(key, value);
                if (activeTime > 0) {
                    rs = connection.expire(key, activeTime);
                }
                return rs;
            }
        });
    }

    @Override
    public boolean set(String key, Object obj, long activeTime) {
        Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer)redisTemplate.getValueSerializer();
        byte[] valBytes = serializer.serialize(obj);

        return set(key.getBytes(), valBytes, activeTime);
    }

    @Override
    public boolean set(String key, Object obj) {
        return this.set(key, obj, 0L);
    }

}
