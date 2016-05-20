package org.nh.rest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=8889")
public abstract class IntegrationTest {

    @Value("${server.port}")
    private int port;

    protected String getURL(String path) {
        return "http://localhost:" + this.port + path;
    }
}
