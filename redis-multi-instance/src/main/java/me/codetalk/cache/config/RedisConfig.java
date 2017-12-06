package me.codetalk.cache.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

	/************************** Redis - App  **************************/
	@Value("${app.redis.cluster.nodes}")
	private String appClusterNodes;
	
	@Value("${app.redis.pool.max-total}")
	private int appMaxTotal;
	@Value("${app.redis.pool.max-idle}")
	private int appMaxIdle;
	@Value("${app.redis.pool.min-idle}")
	private int appMinIdle;
	
	/************************** Redis - Mesg **************************/
	@Value("${mesg.redis.host}")
	private String mesgHost;
	
	@Value("${mesg.redis.port}")
	private int mesgPort;
	
	@Value("${mesg.redis.pool.max-total}")
	private int mesgMaxTotal;
	@Value("${mesg.redis.pool.max-idle}")
	private int mesgMaxIdle;
	@Value("${mesg.redis.pool.min-idle}")
	private int mesgMinIdle;
	
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(appMaxTotal);
        poolConfig.setMaxIdle(appMaxIdle);
        poolConfig.setMinIdle(appMinIdle);
        
        // cluster config
        List<String> clusterHostNodes = Arrays.asList(appClusterNodes.split(","));
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(clusterHostNodes);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, poolConfig);
        
        return jedisConnectionFactory;
    }

    @Bean
    public RedisConnectionFactory mesgRedisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(mesgMaxTotal);
        poolConfig.setMaxIdle(mesgMaxIdle);
        poolConfig.setMinIdle(mesgMinIdle);
        
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(mesgHost);
        jedisConnectionFactory.setPort(mesgPort);
        
        return jedisConnectionFactory;
    }

    @Bean(name = "appRedisTemplate")
    public RedisTemplate<String, String> redisTemplateObject() throws Exception {
        RedisTemplate<String, String> redisTemplateObject = new RedisTemplate<String, String>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactory());
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        
        return redisTemplateObject;
    }

    @Bean(name = "mesgRedisTemplate")
    public RedisTemplate<String, String> redisTemplateObject2() throws Exception {
        RedisTemplate<String, String> redisTemplateObject = new RedisTemplate<String, String>();
        redisTemplateObject.setConnectionFactory(mesgRedisConnectionFactory());
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        
        return redisTemplateObject;
    }

    private void setSerializer(RedisTemplate<String, String> template) {
    	Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
    }

}