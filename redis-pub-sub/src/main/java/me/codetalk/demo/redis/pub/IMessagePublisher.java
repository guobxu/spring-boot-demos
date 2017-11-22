package me.codetalk.demo.redis.pub;

public interface IMessagePublisher {

	public void sendMessage(String chn, Object obj);
	
}
