package me.codetalk.demo.messaging.redis.pub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import me.codetalk.demo.messaging.redis.pub.IMessagePublisher;

@Component
public class MessagePublisherImpl implements IMessagePublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisherImpl.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void sendMessage(String chn, Object obj) {
		LOGGER.info("Send data: " + obj.toString());
		
		redisTemplate.convertAndSend(chn, obj);
	}
	
}
