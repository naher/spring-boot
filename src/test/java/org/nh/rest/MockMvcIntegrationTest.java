package org.nh.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
// Enable JMX so we can test the MBeans (you can't do this in a properties file)
@TestPropertySource(properties = {"spring.jmx.enabled:true", "spring.datasource.jmx-enabled:true"})
// Separate profile for web tests to avoid clashing databases
@ActiveProfiles({"scratch", "nonjta"})
public abstract class MockMvcIntegrationTest {

    protected final Logger logger = Logger.getLogger(MockMvcIntegrationTest.class);

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    public <T> T asObject(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        logger.info(json);
        T object = mapper.readValue(json, clazz);
        return object;
    }

    public String asJsonString(Object criteria) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(criteria);
        logger.info(json);
        return json;
    }

}
