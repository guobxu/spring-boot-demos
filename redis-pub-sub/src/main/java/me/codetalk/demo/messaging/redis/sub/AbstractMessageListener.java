package me.codetalk.demo.messaging.redis.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import me.codetalk.demo.messaging.MesgObj;

public abstract class AbstractMessageListener implements MessageListener {

	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
    protected MesgObj mesgToObj(Message mesg) {
    	Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer)redisTemplate.getValueSerializer();
    	
    	return (MesgObj)serializer.deserialize(mesg.getBody());
    }
	
}
