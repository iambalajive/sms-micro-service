package com.balaji.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StopMessageCache {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    private Cache<String, Set<String>> stopMessageCache;

    public StopMessageCache(int cacheExpiryInHrs) {
        stopMessageCache = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheExpiryInHrs, TimeUnit.HOURS)
                .build();
    }

    public void put(String key, String value) {
        try {
            writeLock.lock();
            Set<String> values = stopMessageCache.getIfPresent(key);
            if (values == null) {
                values = new HashSet<>();
            }
            values.add(value);
            stopMessageCache.put(key, values);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean doesPairExist(String key, String value) {
        try {
            readLock.lock();
            Set<String> values = get(key);
            return values != null && values.contains(value);

        } finally {
            readLock.unlock();
        }
    }


    public Set<String> get(String key) {
        try {
            readLock.lock();
            return stopMessageCache.getIfPresent(key);
        } finally {
            readLock.unlock();
        }

    }
}
