package me.codetalk.messaging.redis.pub.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import me.codetalk.messaging.MesgObj;
import me.codetalk.messaging.redis.pub.IMessagePublisher;

@Component
public class MessagePublisherImpl implements IMessagePublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisherImpl.class);
	
	@Resource(name = "mesgRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	public void sendMessage(String chn, MesgObj msgobj) {
		LOGGER.info("Send data: " + msgobj.toString());
		
		redisTemplate.convertAndSend(chn, msgobj);
	}
	
}
