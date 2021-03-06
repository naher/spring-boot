/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nh.rest.service;

import java.util.List;

import org.nh.rest.model.City;
import org.nh.rest.model.HotelSummary;
import org.nh.rest.persistence.relational.ds.CityRepository;
import org.nh.rest.persistence.relational.ds.CitySearchCriteria;
import org.nh.rest.persistence.relational.ds.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component("cityService")
@Transactional(transactionManager = "transactionManager")
class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    private HotelRepository hotelRepository;

    protected CityServiceImpl() {
    }

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<City> findCities(CitySearchCriteria criteria, Pageable pageable) {

        Assert.notNull(criteria, "Criteria must not be null");
        String name = criteria.getName();

        if (!StringUtils.hasLength(name)) {
            return cityRepository.findAll((Pageable) null);
        }

        String country = "";
        int splitPos = name.lastIndexOf(",");

        if (splitPos >= 0) {
            country = name.substring(splitPos + 1);
            name = name.substring(0, splitPos);
        }

        return cityRepository.findByNameContainingAndCountryContainingAllIgnoringCase(name.trim(), country.trim(),
                pageable);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(String name, String country) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(country, "Country must not be null");
        return cityRepository.findByNameAndCountryAllIgnoringCase(name, country);
    }

    @Override
    public Page<HotelSummary> getHotels(City city, Pageable pageable) {
        Assert.notNull(city, "City must not be null");
        return hotelRepository.findByCity(city, pageable);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public City create(String name, String state, String country, String map) {
        City city = new City(name, state, country, map);
        return cityRepository.save(city);
    }

    @Override
    @PreAuthorize("hasPermission(#city, 'delete')")
    public void delete(City city) {
        cityRepository.delete(city);
    }

}
