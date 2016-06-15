package org.nh.rest.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class CacheServiceInMemoryImpl implements CacheService {

    private Map<Long, Long> simpleCache = new HashMap<Long, Long>();

    @Override
    public void store(Long key, Long value) {
        simpleCache.put(key, value);
    }

    @Override
    public Long get(Long key) {
        return simpleCache.get(key);
    }

}
