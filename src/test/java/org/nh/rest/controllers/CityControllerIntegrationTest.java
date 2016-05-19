package org.nh.rest.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nh.rest.Application;
import org.nh.rest.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=8889")
public class CityControllerIntegrationTest {

    @Value("${server.port}")
    private int port;

    @Test
    public void testCustomContextPath() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<City[]> entity = new TestRestTemplate().exchange("http://localhost:" + this.port + "/city/all",
                HttpMethod.GET, new HttpEntity<Void>(headers), City[].class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());

        Assert.assertEquals(21, entity.getBody().length);

        City city = entity.getBody()[0];
        Assert.assertEquals("Brisbane", city.getName());

        System.err.println(entity.getBody());
    }
}
