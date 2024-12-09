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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom implementation of {@link CacheManager} that provides caching with logging.
 * This class extends {@link ConcurrentMapCacheManager} and logs cache operations like
 * cache hits, cache misses, cache puts, evictions, and clears.
 *
 * <p>The cache manager logs every operation for debugging and monitoring purposes. 
 * The log messages are stored in a static list and are also printed to the logger.</p>
 */
public class LoggingCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(LoggingCacheManager.class);
    private final ConcurrentMapCacheManager delegate;

    // Static list to store log messages
    private static final List<String> logMessages = new ArrayList<>();

    /**
     * Constructor to initialize the LoggingCacheManager with cache names.
     *
     * @param cacheNames the names of the caches to be managed by the cache manager
     */
    public LoggingCacheManager(String... cacheNames) {
        this.delegate = new ConcurrentMapCacheManager(cacheNames);
    }

    /**
     * Returns the list of log messages generated during cache operations.
     *
     * @return the list of log messages
     */
    public static List<String> getLogMessages() {
        return logMessages;
    }

    /**
     * Retrieves the {@link Cache} for the given cache name.
     *
     * @param name the name of the cache to retrieve
     * @return the {@link Cache} instance or null if not found
     */
    @Override
    public Cache getCache(String name) {
        Cache cache = delegate.getCache(name);
        if (cache != null) {
            return new LoggingCache(cache);
        }
        return null;
    }

    /**
     * Retrieves the names of all available caches.
     *
     * @return a collection of cache names
     */
    @Override
    public Collection<String> getCacheNames() {
        return delegate.getCacheNames();
    }

    /**
     * Inner class representing a cache with logging capabilities.
     * This class intercepts cache operations (get, put, evict, clear) 
     * to log the details of each operation.
     */
    private static class LoggingCache implements Cache {

        private final Cache delegate;

        /**
         * Constructor to wrap a delegate cache.
         *
         * @param delegate the delegate {@link Cache} instance
         */
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

        /**
         * Formats the current timestamp in the EST (Fort Wayne) timezone.
         *
         * @return the formatted timestamp as a string
         */
        private String getFormattedTimestamp() {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Indiana/Indianapolis"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return now.format(formatter);
        }

        /**
         * Retrieves a cache entry for the given key and logs the result.
         *
         * @param key the key of the cache entry to retrieve
         * @return the value wrapper of the cache entry, or null if not found
         */
        @Override
        public ValueWrapper get(Object key) {
            ValueWrapper value = delegate.get(key);
            String timestamp = getFormattedTimestamp();
            String logMessage = value != null
                    ? String.format("Cache hit for key: %s in cache: %s at %s", key, getName(), timestamp)
                    : String.format("Cache miss for key: %s in cache: %s at %s", key, getName(), timestamp);
            logMessages.add(logMessage);
            logger.info(logMessage);
            return value;
        }

        /**
         * Retrieves a cache entry for the given key, or loads it using the provided loader, 
         * logging the result of the operation.
         *
         * @param key the key of the cache entry to retrieve
         * @param valueLoader the loader to use if the value is not found in the cache
         * @param <T> the type of the value to be loaded
         * @return the loaded value or the cached value if available
         * @throws Exception if the valueLoader fails to load the value
         */
        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            try {
                T value = delegate.get(key, valueLoader);
                String timestamp = getFormattedTimestamp();
                String logMessage = value != null
                        ? String.format("Cache hit for key: %s in cache: %s at %s", key, getName(), timestamp)
                        : String.format("Cache miss for key: %s in cache: %s at %s", key, getName(), timestamp);
                logMessages.add(logMessage);
                logger.info(logMessage);
                return value;
            } catch (Exception e) {
                String timestamp = getFormattedTimestamp();
                String logMessage = String.format("Cache loading failed for key: %s in cache: %s at %s", key, getName(),
                        timestamp);
                logMessages.add(logMessage);
                logger.error(logMessage, e);
                throw e;
            }
        }

        /**
         * Retrieves a cache entry for the given key and type, logging the result.
         *
         * @param key the key of the cache entry to retrieve
         * @param type the expected type of the value
         * @param <T> the type of the value
         * @return the cached value if found, or null if not found
         */
        @Override
        public <T> T get(Object key, Class<T> type) {
            T value = delegate.get(key, type);
            String timestamp = getFormattedTimestamp();
            String logMessage = value != null
                    ? String.format("Cache hit for key: %s of type: %s in cache: %s at %s", key, type.getName(),
                            getName(), timestamp)
                    : String.format("Cache miss for key: %s of type: %s in cache: %s at %s", key, type.getName(),
                            getName(), timestamp);
            logMessages.add(logMessage);
            logger.info(logMessage);
            return value;
        }

        /**
         * Puts a value into the cache and logs the operation.
         *
         * @param key the key of the cache entry
         * @param value the value to be cached
         */
        @Override
        public void put(Object key, Object value) {
            String timestamp = getFormattedTimestamp();
            String logMessage = String.format("Cache put for key: %s in cache: %s at %s", key, getName(), timestamp);
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.put(key, value);
        }

        /**
         * Evicts a cache entry for the given key and logs the operation.
         *
         * @param key the key of the cache entry to remove
         */
        @Override
        public void evict(Object key) {
            String timestamp = getFormattedTimestamp();
            String logMessage = String.format("Cache evicted for key: %s in cache: %s at %s", key, getName(),
                    timestamp);
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.evict(key);
        }

        /**
         * Clears the cache and logs the operation.
         */
        @Override
        public void clear() {
            String timestamp = getFormattedTimestamp();
            String logMessage = String.format("Cache cleared for cache: %s at %s", getName(), timestamp);
            logMessages.add(logMessage);
            logger.info(logMessage);
            delegate.clear();
        }
    }
}
