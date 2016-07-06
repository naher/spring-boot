package org.nh.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface IntegrationAbstractTest {

    Integer getPort();

    default String getURL(String path) {
        return "http://localhost:" + getPort() + path;
    }

    default HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
