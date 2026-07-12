package com.walletservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.walletservice.config.CacheNames.ANALYTICS;
import static com.walletservice.config.CacheNames.PROFILE;
import static com.walletservice.config.CacheNames.WALLET;

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
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            RedisCacheConfiguration redisCacheConfiguration
    ) {
        Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();

        cacheConfiguration.put(WALLET,
                redisCacheConfiguration
                        .entryTtl(Duration.ofSeconds(60)));

        cacheConfiguration.put(
                ANALYTICS,
                redisCacheConfiguration
                        .entryTtl(Duration.ofMinutes(5))
        );

        cacheConfiguration.put(
                PROFILE,
                redisCacheConfiguration
                        .entryTtl(Duration.ofMinutes(30))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfiguration)
                .transactionAware().build();
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
