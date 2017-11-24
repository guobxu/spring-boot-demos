package me.codetalk.demo.messaging.redis.sub.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.demo.Constants;
import me.codetalk.demo.messaging.MesgObj;
import me.codetalk.demo.messaging.redis.sub.AbstractMessageListener;

@Component
public class CommonMesgListener extends AbstractMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonMesgListener.class);
	
	@Autowired
	private ICacheService cacheService;
	
	public void onMessage(Message message, byte[] pattern) {
		MesgObj msgobj = (MesgObj)mesgToObj(message);
		
		String cacheKey = this.getClass().getName() + "-" + msgobj.getId();
		if(!cacheService.setNX(cacheKey, "X")) return;
		
		String type = msgobj.getType();
		if(Constants.MSGTYPE_USER_CREATE.equals(type)) {
			handleUserMessage(msgobj);
		} else if(Constants.MSGTYPE_ORDER_CREATE.equals(type)) {
			handleOrderMessage(msgobj);
		}
	}
	
	private void handleUserMessage(MesgObj msgobj) {
		Map<String, Object> data = (Map<String, Object>)msgobj.getData();
		
		LOGGER.info("Receive data: Message id = " + msgobj.getId() + 
				", type = " + msgobj.getType() + 
				", createDate = " + msgobj.getCreateDate() +  
				", data = { id = " + data.get("id") + 
				", name = " + data.get("name") +
				", birth = " + data.get("birth") + 
				"}");
	}
	
	private void handleOrderMessage(MesgObj msgobj) {
		Map<String, Object> data = (Map<String, Object>)msgobj.getData();
		
		LOGGER.info("Receive data: Message id = " + msgobj.getId() + 
				", type = " + msgobj.getType() + 
				", createDate = " + msgobj.getCreateDate() +  
				", data = { id = " + data.get("id") + 
				", no = " + data.get("no") +
				", createDate = " + data.get("createDate") + 
				"}");
	}

}











