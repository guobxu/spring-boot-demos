package me.codetalk.messaging.redis.pub;

import me.codetalk.messaging.MesgObj;

public interface IMessagePublisher {

	public void sendMessage(String chn, MesgObj msgobj);
	
}
