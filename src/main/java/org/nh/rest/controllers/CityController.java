package org.nh.rest.controllers;

import org.nh.rest.dto.City;
import org.nh.rest.dto.CitySearchCriteria;
import org.nh.rest.exception.NotFoundException;
import org.nh.rest.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody City[] getAllCities() {
        return City.dtos(cityService.getAll());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody City getCities(@RequestBody final CitySearchCriteria criteria) throws NotFoundException {
        org.nh.rest.model.City city = cityService.getCity(criteria.getName(), criteria.getCountry());
        if (city == null) {
            throw new NotFoundException("city not found for parameters n: " + criteria.getName() + ", c: "
                    + criteria.getCountry());
        }
        return City.dto(city);
    }

    @RequestMapping(value = "/search/n/{name}/c/{country}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody City getCities(@PathVariable("name") final String name,
            @PathVariable("country") final String country) throws NotFoundException {
        org.nh.rest.model.City city = cityService.getCity(name, country);
        if (city == null) {
            throw new NotFoundException("city not found for parameters n: " + name + ", c: " + country);
        }
        return City.dto(city);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody String notFound(NotFoundException e) {
        return e.getMessage();
    }

}
