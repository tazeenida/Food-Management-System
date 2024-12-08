package com.acs560.FoodManagementSystem.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.Callable;

public class LoggingCacheManager implements CacheManager {
    private static final Logger logger = LoggerFactory.getLogger(LoggingCacheManager.class);
    private final ConcurrentMapCacheManager delegate;

    public LoggingCacheManager(String... cacheNames) {
        this.delegate = new ConcurrentMapCacheManager(cacheNames);
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = delegate.getCache(name);
        if (cache != null) {
            return new LoggingCache(cache);
        }
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return delegate.getCacheNames();
    }

    // Inner LoggingCache class
    private static class LoggingCache implements Cache {
        private final Cache delegate;

        LoggingCache(Cache delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public Object getNativeCache() {
            return delegate.getNativeCache();
        }

        @Override
        public ValueWrapper get(Object key) {
            ValueWrapper value = delegate.get(key);
            if (value != null) {
                logger.info("Cache hit for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
            } else {
                logger.info("Cache miss for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
            }
            return value;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            try {
                T value = delegate.get(key, valueLoader);
                if (value != null) {
                    logger.info("Cache hit for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
                } else {
                    logger.info("Cache miss for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
                }
                return value;
            } catch (Exception e) {
                logger.error("Cache loading failed for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis(), e);
                throw e;
            }
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            T value = delegate.get(key, type);
            if (value != null) {
                logger.info("Cache hit for key: {} of type: {} in cache: {} at {}", key, type.getName(), getName(), System.currentTimeMillis());
            } else {
                logger.info("Cache miss for key: {} of type: {} in cache: {} at {}", key, type.getName(), getName(), System.currentTimeMillis());
            }
            return value;
        }

        @Override
        public void put(Object key, Object value) {
            logger.info("Cache put for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
            delegate.put(key, value);
        }

        @Override
        public void evict(Object key) {
            logger.info("Cache evicted for key: {} in cache: {} at {}", key, getName(), System.currentTimeMillis());
            delegate.evict(key);
        }

        @Override
        public void clear() {
            logger.info("Cache cleared for cache: {} at {}", getName(), System.currentTimeMillis());
            delegate.clear();
        }
    }
}
