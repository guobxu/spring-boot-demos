package me.codetalk.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import me.codetalk.demo.Constants;
import me.codetalk.demo.redis.sub.impl.OrderMesgListener;
import me.codetalk.demo.redis.sub.impl.UserMesgListener;

@Configuration
public class RedisMesgConfig {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private UserMesgListener userMesgListener;
	
	@Autowired
	private OrderMesgListener orderMesgListener;
	
	@Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        
        container.setConnectionFactory( redisTemplate.getConnectionFactory() );
        container.addMessageListener( userMesgListener, new ChannelTopic(Constants.CHN_USER) );
        container.addMessageListener( orderMesgListener, new ChannelTopic(Constants.CHN_ORDER) );

        return container;
    }
	
}
