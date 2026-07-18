package com.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory,
            StringRedisSerializer stringRedisSerializer,
            GenericJackson2JsonRedisSerializer redisSerializer
    ) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.setValueSerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);

        // Optional but recommended
        template.setDefaultSerializer(redisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(
            StringRedisSerializer stringRedisSerializer,
            GenericJackson2JsonRedisSerializer redisSerializer
    ) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))

                .disableCachingNullValues()

                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(stringRedisSerializer)
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(redisSerializer)
                );
    }

    @Bean
    public GenericJackson2JsonRedisSerializer redisSerializer(
            ObjectMapper objectMapper) {

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }
}
