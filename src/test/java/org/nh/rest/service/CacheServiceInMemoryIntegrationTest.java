package org.nh.rest.service;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;

import org.springframework.beans.factory.annotation.Autowired;

public class CacheServiceInMemoryIntegrationTest extends IntegrationTest {

    @Autowired
    private CacheService service;

    @Test
    public void testInMemoryCacheImplementation() {

        Assert.assertEquals("org.nh.rest.service.CacheServiceInMemoryImpl", service.getClass().getCanonicalName());

        Long key = 2l;
        Long value = 33l;

        service.store(key, value);

        Assert.assertEquals(value, service.get(key));
    }

}
