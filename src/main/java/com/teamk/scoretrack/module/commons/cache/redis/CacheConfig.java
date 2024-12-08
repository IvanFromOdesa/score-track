package com.teamk.scoretrack.module.commons.cache.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.io.config.IOConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration("redisCacheConfig")
public class CacheConfig {
    @Value("${caching.default.ttl}")
    private Long defaultTtl;
    @Value("${redis.hostname}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.jedis.pool.max-active}")
    private int maxActive;

    @Bean
    @Primary
    public RedisCacheManager redisCacheConfiguration(JedisConnectionFactory jedisConnectionFactory) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader()).entryTtl(Duration.ofMillis(defaultTtl)).disableCachingNullValues().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper mapper = IOConfig.getDefault();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
        return mapper;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    // TODO: testing
    @Bean
    @Primary
    public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory redisConnectionFactory,
                                                                       RedisKeyExpirationListener redisKeyExpirationListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        RedisMessageProcessingErrorHandler errorHandler = new RedisMessageProcessingErrorHandler();
        container.setConnectionFactory(redisConnectionFactory);
        //container.setTopicSerializer();
        container.addMessageListener(redisKeyExpirationListener, new ChannelTopic("__keyevent@0__:expired"));
        container.setErrorHandler(errorHandler);
        //container.setRecoveryBackoff();
        return container;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        /*
         * {@link https://github.com/redis/jedis/issues/2781}
         */
        poolConfig.setJmxEnabled(false);

        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle((int) (maxActive * 0.2));
        poolConfig.setMinIdle((int) (maxActive * 0.1));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        return new JedisPool(poolConfig, host, port);
    }
}
