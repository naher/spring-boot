package org.nh.rest.service;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "production", "fullIntegrationTest"})
public class CacheServiceMemcachedImpl implements CacheService {

    @Override
    public void store(Long key, Long value) {
        // TODO: complete this implementation
        throw new NotImplementedException("not yet implemented");
    }

    @Override
    public Long get(Long key) {
        // TODO: complete this implementation
        throw new NotImplementedException("not yet implemented");
    }

}
