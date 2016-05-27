package org.nh.rest.dto;

import org.springframework.util.Assert;

public class CitySearchCriteria {

    private String name;
    private String country;

    public CitySearchCriteria() {
    }

    public CitySearchCriteria(String name, String country) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(country, "Name must not be null");
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
