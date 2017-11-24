package me.codetalk.demo.messaging.redis.pub;

public interface IMessagePublisher {

	public void sendMessage(String chn, Object obj);
	
}
