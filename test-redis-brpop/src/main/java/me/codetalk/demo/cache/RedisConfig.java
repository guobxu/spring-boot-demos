package me.codetalk.demo.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /************************** Redis **************************/
    @Value("${app.redis.host}")
    private String host;
    @Value("${app.redis.port}")
    private int port;
    @Value("${app.redis.timeout}")
    private int timeout;

    @Value("${app.redis.pool.max-total}")
    private int maxTotal;
    @Value("${app.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${app.redis.pool.min-idle}")
    private int minIdle;
    @Value("${app.redis.pool.max-wait}")
    private long maxWait;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setTimeout(timeout);

        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }

}
