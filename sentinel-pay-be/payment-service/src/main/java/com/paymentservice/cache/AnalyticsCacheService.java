package com.paymentservice.cache;

public interface AnalyticsCacheService {
    void evictUserAnalytics(Long userId);
    void evictAllAnalytics();
}
