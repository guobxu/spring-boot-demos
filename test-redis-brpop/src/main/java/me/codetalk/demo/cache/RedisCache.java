package me.codetalk.demo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public byte[] rawKey(String key) {
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        return keySerializer.serialize(key);
    }

    public byte[] rawValue(Object val) {
        RedisSerializer valSerializer = redisTemplate.getValueSerializer();
        return valSerializer.serialize(val);
    }

    public String deserValue(byte[] bytes) {
        StringRedisSerializer valSerializer = (StringRedisSerializer)redisTemplate.getValueSerializer();

        return valSerializer.deserialize(bytes);
    }

    /**
     * 阻塞rpop
     * @param key
     * @param timeout 单位(秒)
     * @return
     */
    public String bRpop(String key, int timeout) {
        return redisTemplate.execute((RedisCallback<String>) conn -> {
            List<byte[]> valBytesList = conn.bRPop(timeout, rawKey(key));

            return deserValue(valBytesList.get(1));
        });
    }

    public Long lPush(String key, String val) {
        return redisTemplate.execute((RedisCallback<Long>) conn -> {
            return conn.lPush(rawKey(key), rawValue(val));
        });
    }

}
