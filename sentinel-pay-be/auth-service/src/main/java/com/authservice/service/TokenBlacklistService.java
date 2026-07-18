package com.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "blacklist:";

    private final RedisTemplate<String, Object> redisTemplate;

    public void blacklistToken(String jti, long remainingValidityMillis){
        redisTemplate.opsForValue().set(
                BLACKLIST_PREFIX + jti,
                Boolean.TRUE,
                Duration.ofMillis(remainingValidityMillis)
        );

        log.info("Blacklisted JWT [{}] for {} ms", jti, remainingValidityMillis);
    }

    public boolean isBlacklisted(String jti){
        return redisTemplate.hasKey(BLACKLIST_PREFIX + jti);
    }

    private String getKey(String jti) {
        return "blacklist:" + jti;
    }
}
