package com.teamk.scoretrack.module.security.session.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

import java.time.Duration;
import java.util.List;

@EnableRedisIndexedHttpSession
@Configuration
public class SessionConfig {
    @Value("${redis.session.lifespan}")
    private long redisSessionLifespan;
    public static final String REDIS_SESSION_TEMPLATE = "redisSessionTemplate";

    @Bean(REDIS_SESSION_TEMPLATE)
    public RedisTemplate<String, Object> redisTemplate(RedisSerializer<Object> sessionSerializer,
                                                       JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = StringRedisSerializer.UTF_8;
        template.setConnectionFactory(jedisConnectionFactory);
        //template.setDefaultSerializer(sessionSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(sessionSerializer);
        template.setHashValueSerializer(sessionSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        return template;
    }

    @Bean
    @Primary
    public RedisSerializer<Object> sessionSerializer(ObjectMapper mapper, List<IMixinAware<?>> mixinProviders) {
        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
        mixinProviders.forEach(provider -> mapper.addMixIn(provider.getTargetClass(), provider.getMixinClass()));
        mapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
        mapper.registerModule(new CoreJackson2Module());
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    @Primary
    public RedisIndexedSessionRepository redisIndexedSessionRepository(
            @Qualifier(REDIS_SESSION_TEMPLATE) RedisTemplate<String, Object> redisTemplate,
            RedisSerializer<Object> sessionSerializer,
            // The behaviour of default eventPublisher in RedisIndexedSessionRepository
            // is to just ignore publishing events completely:
            // private ApplicationEventPublisher eventPublisher = (event) -> {
            //    };
            // I have no idea why there's no mentions of it in the docs whatsoever.
            ApplicationEventPublisher eventPublisher) {
        RedisIndexedSessionRepository sessionRepository = new RedisIndexedSessionRepository(redisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval(Duration.ofMillis(redisSessionLifespan));
        sessionRepository.setDefaultSerializer(sessionSerializer);
        sessionRepository.setApplicationEventPublisher(eventPublisher);
        /*sessionRepository.setFlushMode(FlushMode.IMMEDIATE);
        //sessionRepository.setSaveMode(SaveMode.ON_GET_ATTRIBUTE);*/
        return sessionRepository;
    }
}
