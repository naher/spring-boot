package org.nh.rest.controllers;

import org.apache.log4j.Logger;

import org.junit.Test;

import org.nh.rest.JtaIntegrationTest;
import org.nh.rest.service.InterestService;
import org.nh.rest.service.ProductService;
import org.nh.rest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class InterestControllerJtaIntegrationTest extends JtaIntegrationTest {

    protected final Logger logger = Logger.getLogger(InterestControllerJtaIntegrationTest.class);

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
