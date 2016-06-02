/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nh.rest;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;

import org.apache.log4j.Logger;

import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;

/**
 * Integration test to run the application.
 *
 * @author Oliver Gierke
 * @author Dave Syer
 */
public class SampleDataJpaApplicationTests extends MockMvcIntegrationTest {

    protected final Logger logger = Logger.getLogger(SampleDataJpaApplicationTests.class);

    @Test
    public void testRepoOptions() throws Exception {
        String json = mvc
                .perform(options("/repo/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        logger.info(json);

        json = mvc.perform(get("/repo").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        logger.info(json);
    }

    // @Test
    // TODO after adding elasticsearch the support for @RepositoryRestResource
    // was broken,
    // maybe due an internal spring-data bug
    // see also
    // http://stackoverflow.com/questions/31247132/using-spring-data-jpa-and-spring-data-elastichsearch-in-same-app-with-same-domai
    public void testRepoGetAllHotels() throws Exception {
        String json = mvc
                .perform(get("/repo/hotels").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        logger.info(json);

        Assert.isTrue(json.contains("\"totalElements\" : 21"));
    }

    // @Test
    // TODO after adding elasticsearch the support for @RepositoryRestResource
    // was broken,
    // maybe due an internal spring-data bug
    // see also
    // http://stackoverflow.com/questions/31247132/using-spring-data-jpa-and-spring-data-elastichsearch-in-same-app-with-same-domai
    public void testRepoGetAllCities() throws Exception {
        String json = mvc
                .perform(get("/repo/cities").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        logger.info(json);

        Assert.isTrue(json.contains("\"totalElements\" : 21"));
    }

    @Test
    public void testJmx() throws Exception {
        assertEquals(
                1,
                ManagementFactory.getPlatformMBeanServer()
                        .queryMBeans(new ObjectName("org.nh:type=ConnectionPool,*"), null).size());
    }

}
