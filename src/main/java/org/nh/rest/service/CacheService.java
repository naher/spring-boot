package org.nh.rest.service;

public interface CacheService {

    void store(Long key, Long value);

    Long get(Long key);

}
