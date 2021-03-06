package org.nh.rest;

import org.apache.log4j.Logger;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=8890")
// Separate profile for web tests to avoid clashing databases
@ActiveProfiles({"scratch", "test", "nonjta"})
@Transactional
public abstract class IntegrationTest implements IntegrationAbstractTest {

    protected final Logger logger = Logger.getLogger(IntegrationTest.class);

    @Value("${server.port}")
    private int port;

    @Override
    public Integer getPort() {
        return port;
    }

}
