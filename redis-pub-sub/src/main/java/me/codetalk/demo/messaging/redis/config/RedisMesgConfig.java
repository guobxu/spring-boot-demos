package me.codetalk.demo.messaging.redis.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import me.codetalk.demo.Constants;
import me.codetalk.demo.messaging.redis.sub.impl.CommonMesgListener;
import me.codetalk.demo.messaging.redis.sub.impl.OrderMesgListener;
import me.codetalk.demo.messaging.redis.sub.impl.UserMesgListener;

@Configuration
public class RedisMesgConfig {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private UserMesgListener userMesgListener;
	
	@Autowired
	private OrderMesgListener orderMesgListener;
	
	@Autowired
	private CommonMesgListener commMesgListener;
	
	@Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        
        container.setConnectionFactory( redisTemplate.getConnectionFactory() );
        
        ChannelTopic userCreateTopic = new ChannelTopic(Constants.CHN_USER_CREATE),
        		orderCreateTopic = new ChannelTopic(Constants.CHN_ORDER_CREATE);
        container.addMessageListener( userMesgListener, userCreateTopic );
        container.addMessageListener( orderMesgListener, orderCreateTopic );

        container.addMessageListener( commMesgListener, Arrays.asList(userCreateTopic, orderCreateTopic) );
        
        return container;
    }
	
}
