package org.nh.rest;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=8889")
// Separate profile for web tests to avoid clashing databases
@ActiveProfiles({"scratch", "test", "nonjta"})
// @ActiveProfiles({"scratch", "test", "jta"})
@Transactional
public abstract class IntegrationTest {

    protected final Logger logger = Logger.getLogger(IntegrationTest.class);

    @Value("${server.port}")
    private int port;

    protected String getURL(String path) {
        return "http://localhost:" + this.port + path;
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
