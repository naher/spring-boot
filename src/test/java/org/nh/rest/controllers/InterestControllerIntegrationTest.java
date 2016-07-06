package org.nh.rest.controllers;

import org.apache.log4j.Logger;

import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.service.InterestService;
import org.nh.rest.service.ProductService;
import org.nh.rest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class InterestControllerIntegrationTest extends IntegrationTest {

    protected final Logger logger = Logger.getLogger(InterestControllerIntegrationTest.class);

    @Autowired
    private InterestService interestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    public void testDistributedExceptions() {

        new InterestControllerAbstractIntegrationTest(this, interestService, userService, productService)
                .testDistributedExceptions();
    }
}
