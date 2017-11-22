package me.codetalk.demo.redis.sub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import me.codetalk.demo.pojo.Order;

@Component
public class OrderMesgListener extends AbstractMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderMesgListener.class);
	
	public void onMessage(Message message, byte[] pattern) {
		Order order = (Order)mesgToObj(message);
		
		LOGGER.info("Received order data = { id = " + order.getId() + 
				", no = " + order.getNo() +
				", createDate = " + order.getCreateDate() + 
				"}");
	}

}











