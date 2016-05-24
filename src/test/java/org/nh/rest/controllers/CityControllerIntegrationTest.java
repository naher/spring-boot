package org.nh.rest.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.nh.rest.IntegrationTest;
import org.nh.rest.dto.City;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CityControllerIntegrationTest extends IntegrationTest {

    @Test
    public void testGetAllCities() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<City[]> entity = new TestRestTemplate().exchange(getURL("/city/all"), HttpMethod.GET,
                new HttpEntity<Void>(headers), City[].class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());

        Assert.assertEquals(21, entity.getBody().length);

        City city = entity.getBody()[0];
        Assert.assertEquals("Brisbane", city.getName());

        System.err.println(entity.getBody());
    }

}
