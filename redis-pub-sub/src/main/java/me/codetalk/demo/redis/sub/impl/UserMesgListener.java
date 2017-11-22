package me.codetalk.demo.redis.sub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import me.codetalk.demo.pojo.User;

@Component
public class UserMesgListener extends AbstractMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserMesgListener.class);
	
	public void onMessage(Message message, byte[] pattern) {
		User user = (User)mesgToObj(message);
		
		LOGGER.info("Received user data = { id = " + user.getId() + 
				", name = " + user.getName() +
				", birth = " + user.getBirth() + 
				"}");
	}

}











