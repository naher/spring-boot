package org.nh.rest.controllers;

import org.nh.rest.IntegrationAbstractTest;

public abstract class ControllerAbstractIntegrationTest implements IntegrationAbstractTest {

    private IntegrationAbstractTest concreteTest;

    public ControllerAbstractIntegrationTest(IntegrationAbstractTest concreteTest) {
        this.concreteTest = concreteTest;
    }

    @Override
    public Integer getPort() {
        return concreteTest.getPort();
    }
}
