package org.nh.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.log4j.Logger;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.MockMvcIntegrationTest;
import org.nh.rest.dto.City;
import org.nh.rest.dto.CitySearchCriteria;

import org.springframework.http.MediaType;

public class CityControllerMockMvcIntegrationTest extends MockMvcIntegrationTest {

    protected final Logger logger = Logger.getLogger(CityControllerMockMvcIntegrationTest.class);

    @Test
    public void testGetAllCities() throws Exception {
        String json = mvc
                .perform(get("/city/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();;

        logger.info(json);

        Assert.assertTrue(json.contains("\"id\":1,\"name\":\"Brisbane\""));
    }

    @Test
    public void testGetCityByNameAndCountry() throws UnsupportedEncodingException, Exception {

        String json = mvc
                .perform(get("/city/search/n/Brisbane/c/Australia").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();;

        logger.info(json);

        Assert.assertTrue(json.contains("\"id\":1,\"name\":\"Brisbane\""));
        City city = asObject(json, City.class);
        Assert.assertEquals("Brisbane", city.getName());
    }

    @Test
    public void testGetCityByNameAndCountryNotFound() throws UnsupportedEncodingException, Exception {

        String json = mvc
                .perform(get("/city/search/n/thename/c/thecountry").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();;

        logger.info(json);

        Assert.assertEquals("city not found for parameters n: thename, c: thecountry", json);
    }

    @Test
    public void testGetCityByNameAndCountryPost() throws UnsupportedEncodingException, Exception {

        CitySearchCriteria criteria = new CitySearchCriteria("Brisbane", "Australia");

        String json = mvc
                .perform(post("/city/search").content(asJsonString(criteria)).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        logger.info(json);

        Assert.assertTrue(json.contains("\"id\":1,\"name\":\"Brisbane\""));
        City city = asObject(json, City.class);
        Assert.assertEquals("Brisbane", city.getName());
    }

    @Test
    public void testGetCityByNameAndCountryNotFoundPost()
            throws UnsupportedEncodingException, JsonProcessingException, Exception {

        CitySearchCriteria criteria = new CitySearchCriteria("thename", "thecountry");

        String json = mvc
                .perform(post("/city/search").content(asJsonString(criteria)).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        logger.info(json);

        Assert.assertEquals("city not found for parameters n: thename, c: thecountry", json);

    }

}
