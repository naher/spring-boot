package org.nh.rest.controllers;

import org.apache.log4j.Logger;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.dto.City;
import org.nh.rest.dto.CitySearchCriteria;

import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CityControllerIntegrationTest extends IntegrationTest {

    protected final Logger logger = Logger.getLogger(CityControllerIntegrationTest.class);

    @Test
    public void testGetAllCities() throws Exception {
        HttpHeaders headers = getHeaders();

        ResponseEntity<City[]> entity = new TestRestTemplate().exchange(getURL("/city/all"), HttpMethod.GET,
                new HttpEntity<Void>(headers), City[].class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertEquals(21, entity.getBody().length);

        City city = entity.getBody()[0];
        Assert.assertEquals("Brisbane", city.getName());

        logger.info(entity.getBody());
    }

    @Test
    public void testGetCityByNameAndCountry() {
        HttpHeaders headers = getHeaders();

        ResponseEntity<City> entity = new TestRestTemplate().exchange(getURL("/city/search/n/Brisbane/c/Australia"),
                HttpMethod.GET, new HttpEntity<Void>(headers), City.class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        City city = entity.getBody();

        Assert.assertEquals("Brisbane", city.getName());

        logger.info(entity.getBody());
    }

    @Test
    public void testGetCityByNameAndCountryNotFound() {
        HttpHeaders headers = getHeaders();

        ResponseEntity<String> entity = new TestRestTemplate().exchange(getURL("/city/search/n/thename/c/thecountry"),
                HttpMethod.GET, new HttpEntity<Void>(headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        String msg = entity.getBody();

        Assert.assertEquals("city not found for parameters n: thename, c: thecountry", msg);

        logger.info(msg);
    }

    @Test
    public void testGetCityByNameAndCountryPost() {
        HttpHeaders headers = getHeaders();

        CitySearchCriteria criteria = new CitySearchCriteria("Brisbane", "Australia");
        ResponseEntity<City> entity = new TestRestTemplate().exchange(getURL("/city/search"), HttpMethod.POST,
                new HttpEntity<CitySearchCriteria>(criteria, headers), City.class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        City city = entity.getBody();

        Assert.assertEquals("Brisbane", city.getName());

        logger.info(entity.getBody());
    }

    @Test
    public void testGetCityByNameAndCountryNotFoundPost() {
        HttpHeaders headers = getHeaders();

        CitySearchCriteria criteria = new CitySearchCriteria("thename", "thecountry");
        ResponseEntity<String> entity = new TestRestTemplate().exchange(getURL("/city/search"), HttpMethod.POST,
                new HttpEntity<CitySearchCriteria>(criteria, headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        String msg = entity.getBody();

        Assert.assertEquals("city not found for parameters n: thename, c: thecountry", msg);

        logger.info(msg);
    }

}
