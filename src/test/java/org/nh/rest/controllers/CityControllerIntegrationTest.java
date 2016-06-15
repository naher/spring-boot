package org.nh.rest.controllers;

import org.apache.log4j.Logger;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.Application;
import org.nh.rest.Constants;
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

    @Test
    public void testCreateCityAccessDenied() {
        HttpHeaders headers = getHeaders();

        String name = "name";
        String country = "country";
        String state = "state";
        String map = "-37.813187, 144.96298";

        City request = new City(name, state, country, map);
        ResponseEntity<String> entity = new TestRestTemplate().exchange(getURL("/city"), HttpMethod.POST,
                new HttpEntity<City>(request, headers), String.class);

        logger.info(entity.getBody());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }

    @Test
    public void testCreateCityAuthenticationFailed() {
        HttpHeaders headers = getHeaders();

        headers.add(Application.X_AUTH_EMAIL, Constants.ADMIN_MAIL_COM);
        headers.add(Application.X_AUTH_PASS, Constants.USERPASS);

        String name = "name";
        String country = "country";
        String state = "state";
        String map = "-37.813187, 144.96298";

        City request = new City(name, state, country, map);
        ResponseEntity<String> entity = new TestRestTemplate().exchange(getURL("/city"), HttpMethod.POST,
                new HttpEntity<City>(request, headers), String.class);

        logger.info(entity.getBody());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }

    @Test
    public void testCreateCity() {
        HttpHeaders headers = getHeaders();

        headers.add(Application.X_AUTH_EMAIL, Constants.ADMIN_MAIL_COM);
        headers.add(Application.X_AUTH_PASS, Constants.ADMINPASS);

        String name = "testCreateCity";
        String country = "country";
        String state = "state";
        String map = "-37.813187, 144.96298";

        City request = new City(name, state, country, map);
        ResponseEntity<City> entity = new TestRestTemplate().exchange(getURL("/city"), HttpMethod.POST,
                new HttpEntity<City>(request, headers), City.class);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        City city = entity.getBody();

        logger.info(entity.getBody());

        Assert.assertNotNull(city);
        Assert.assertNotNull(city.getId());
        Assert.assertEquals(request.getName(), city.getName());
    }
}
