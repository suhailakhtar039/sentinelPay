package com.paymentservice.cache.impl;

import com.paymentservice.cache.AnalyticsCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import static com.paymentservice.config.CacheNames.ANALYTICS;

@Service
@RequiredArgsConstructor
public class AnalyticsCacheServiceImpl implements AnalyticsCacheService {

    private final CacheManager cacheManager;

    @Override
    public void evictUserAnalytics(Long userId) {

        var cache = cacheManager.getCache(ANALYTICS);

        if (cache == null) {
            return;
        }

        cache.evict("overview:" + userId);
        cache.evict("daily-transactions:" + userId);
        cache.evict("monthly-volume:" + userId);
        cache.evict("payment-status:" + userId);
        cache.evict("top-receivers:" + userId);
        cache.evict("average-amount:" + userId);
        cache.evict("dashboard:" + userId);
    }

    @Override
    public void evictAllAnalytics() {

        var cache = cacheManager.getCache(ANALYTICS);

        if (cache != null) {
            cache.clear();
        }
    }
}