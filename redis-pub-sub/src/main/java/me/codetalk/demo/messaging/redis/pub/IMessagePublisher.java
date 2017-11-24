package me.codetalk.demo.messaging.redis.pub;

import me.codetalk.demo.messaging.MesgObj;

public interface IMessagePublisher {

	public void sendMessage(String chn, MesgObj msgobj);
	
}
