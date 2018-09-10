package com.balaji.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SmsMeterCache {

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock readLock = readWriteLock.readLock();
    Lock writeLock = readWriteLock.writeLock();

    private Cache<String, Integer> smsMeterCache;

    public SmsMeterCache(int cacheExpiryInHrs) {
        smsMeterCache = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheExpiryInHrs, TimeUnit.HOURS)
                .build();
    }

    public void put(String key, int value) {
        try {
            writeLock.lock();
            smsMeterCache.put(key, value);
        } finally {
            writeLock.unlock();
        }

    }

    public void increment(String key) {
        writeLock.lock();
        try {
            Integer value = get(key);
            if (value == null) {
                value = 0;
            }
            value = value + 1;
            put(key, value);
        } finally {
            writeLock.unlock();
        }
    }



    public Integer get(String key) {
        try {
            readLock.lock();
            return smsMeterCache.getIfPresent(key);
        } finally {
            readLock.unlock();
        }

    }
}
