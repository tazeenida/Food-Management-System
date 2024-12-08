package com.acs560.FoodManagementSystem.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class LoggingCacheManager implements CacheManager {
    private static final Logger logger = LoggerFactory.getLogger(LoggingCacheManager.class);
    private final ConcurrentMapCacheManager delegate;

    // Static list to store log messages
    private static final List<String> logMessages = new ArrayList<>();

    public LoggingCacheManager(String... cacheNames) {
        this.delegate = new ConcurrentMapCacheManager(cacheNames);
    }

    public static List<String> getLogMessages() {
        return logMessages;
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
            String logMessage = value != null
                    ? String.format("Cache hit for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis())
                    : String.format("Cache miss for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis());
            logMessages.add(logMessage); // Add the log message to the list
            logger.info(logMessage);
            return value;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            try {
                T value = delegate.get(key, valueLoader);
                String logMessage = value != null
                        ? String.format("Cache hit for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis())
                        : String.format("Cache miss for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis());
                logMessages.add(logMessage);
                logger.info(logMessage);
                return value;
            } catch (Exception e) {
                String logMessage = String.format("Cache loading failed for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis());
                logMessages.add(logMessage);
                logger.error(logMessage, e);
                throw e;
            }
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            T value = delegate.get(key, type);
            String logMessage = value != null
                    ? String.format("Cache hit for key: %s of type: %s in cache: %s at %d", key, type.getName(), getName(), System.currentTimeMillis())
                    : String.format("Cache miss for key: %s of type: %s in cache: %s at %d", key, type.getName(), getName(), System.currentTimeMillis());
            logMessages.add(logMessage);
            logger.info(logMessage);
            return value;
        }

        @Override
        public void put(Object key, Object value) {
            String logMessage = String.format("Cache put for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis());
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.put(key, value);
        }

        @Override
        public void evict(Object key) {
            String logMessage = String.format("Cache evicted for key: %s in cache: %s at %d", key, getName(), System.currentTimeMillis());
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.evict(key);
        }

        @Override
        public void clear() {
            String logMessage = String.format("Cache cleared for cache: %s at %d", getName(), System.currentTimeMillis());
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.clear();
        }
    }
}
