package org.nh.rest.service;

import org.junit.Assert;
import org.junit.Test;
import org.nh.rest.IntegrationTest;
import org.nh.rest.model.ds01.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCacheServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private UserCacheService cache;

    @Autowired
    private UserService service;

    @Test
    public void createAndGet() {

        String email = "the@email.com";
        String name = "thename01";

        User saved = service.create(name, email);

        Assert.assertEquals(name, saved.getName());

        cache.store(saved);

        User retrieved = cache.get(saved.getId());

        Assert.assertEquals(name, retrieved.getName());

        Assert.assertEquals(saved.getId(), retrieved.getId());
    }
}
