package com.sf.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.demo.cache.impl.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Creator: guobxu
 * Date: 2018/11/28
 */
@Configuration
public class CacheConfigurePostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfigurePostProcessor.class);

    private ConfigurableEnvironment environment;

    private static final String REDIS_HOST = ".redis.host";
    private static final String REDIS_PORT = ".redis.port";
    private static final String REDIS_POOL_MAXTOTAL = ".redis.pool.max-total";
    private static final String REDIS_POOL_MAXIDLE = ".redis.pool.max-idle";
    private static final String REDIS_POOL_MINIDLE = ".redis.pool.min-idle";
    private static final String REDIS_POOL_MAXWAIT = ".redis.pool.max-wait";
    private static final String REDIS_TIMEOUT = ".redis.timeout";

    private static final String CACHE_NAME_SUFFIX = "Cache";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String cacheList = environment.getProperty("app.cacheList");
        if(cacheList == null) return;

        String[] caches = cacheList.split("\\s*,\\s*");
        for(String cache : caches) {
            if(StringUtils.isEmpty(cache)) continue;

            LOGGER.info("configure cache: {}", cache);
            configureCache(beanFactory, cache);
        }
    }

    private void configureCache(ConfigurableListableBeanFactory beanFactory, String cache) {
        String host = environment.getProperty(cache + REDIS_HOST);
        Integer port = environment.getProperty(cache + REDIS_PORT, Integer.class),
                maxTotal = environment.getProperty(cache + REDIS_POOL_MAXTOTAL, Integer.class),
                maxIdle = environment.getProperty(cache + REDIS_POOL_MAXIDLE, Integer.class),
                minIdle = environment.getProperty(cache + REDIS_POOL_MINIDLE, Integer.class),
                maxWait = environment.getProperty(cache + REDIS_POOL_MAXWAIT, Integer.class),
                timeout = environment.getProperty(cache + REDIS_TIMEOUT, Integer.class);

        // connection factory
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setTimeout(timeout);
        connectionFactory.afterPropertiesSet(); // fix

        // redis template
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);

        setKeySerializer(template);
        setValueSerializer(template);

        template.afterPropertiesSet();

        // register bean
        RedisCache redisCache = new RedisCache();
        redisCache.setRedisTemplate(template);

        String beanName = cache + CACHE_NAME_SUFFIX;
        beanFactory.registerSingleton(beanName, redisCache);
    }

    private void setKeySerializer(RedisTemplate<String, Object> template) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
    }

    private void setValueSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

}
