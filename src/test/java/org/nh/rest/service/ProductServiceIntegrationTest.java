package org.nh.rest.service;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.model.ds02.Product;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private ProductService service;

    @Test
    public void createAndGet() {

        double price = 10.01f;
        String name = "thename01";

        Product saved = service.create(name, price);

        Assert.assertEquals(name, saved.getName());

        Product retrieved = service.get(name);

        Assert.assertEquals(name, retrieved.getName());

        Assert.assertEquals(saved.getId(), retrieved.getId());
    }
}
