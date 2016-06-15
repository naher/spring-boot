package org.nh.rest.service;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("fullIntegrationTest")
@ContextConfiguration(classes = CacheServiceMemcachedIntegrationTest.class)
public class CacheServiceMemcachedIntegrationTest {

    @Bean
    @Profile("fullIntegrationTest")
    public CacheService memcached() {
        return new CacheServiceMemcachedImpl();
    }

    @Bean
    @Profile("test")
    public CacheService inMemory() {
        return new CacheServiceInMemoryImpl();
    }

    @Autowired
    private CacheService service;

    @Test(expected = NotImplementedException.class)
    public void testFullyIntegrated() {

        Assert.assertEquals("org.nh.rest.service.CacheServiceMemcachedImpl", service.getClass().getCanonicalName());

        Long key = 2l;
        Long value = 33l;

        service.store(key, value);
    }

}
