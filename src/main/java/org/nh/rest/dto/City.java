package org.nh.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class City {

    private Long id;

    private String name;

    private String state;

    private String country;

    private String map;

    protected City() {
    }

    public City(org.nh.rest.model.City city) {
        id = city.getId();
        name = city.getName();
        state = city.getState();
        country = city.getCountry();
        map = city.getMap();
    }

    public static City dto(org.nh.rest.model.City city) {
        Preconditions.checkNotNull(city);
        return new City(city);
    }

    public static City[] dtos(List<org.nh.rest.model.City> cities) {
        List<City> result = new ArrayList<City>(cities.size());
        for (org.nh.rest.model.City city : cities) {
            result.add(dto(city));
        }
        return result.toArray(new City[cities.size()]);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getMap() {
        return map;
    }

}
