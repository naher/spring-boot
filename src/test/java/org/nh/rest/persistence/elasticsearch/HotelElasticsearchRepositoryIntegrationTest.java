package org.nh.rest.persistence.elasticsearch;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.model.City;
import org.nh.rest.model.Hotel;

public class HotelElasticsearchRepositoryIntegrationTest extends AbstractElasticsearchRepositoryIntegrationTest {

    @Test
    public void basicTest() {
        saveAll();
        fetchAll();
        // FIXME fetchIndividual();
    }

    protected void fetchAll() {
        System.out.println("Hotel found with findAll():");
        System.out.println("-------------------------------");
        Iterable<Hotel> allHotels = hotelEsRepo.findAll();
        for (Hotel hotel : allHotels) {
            System.out.println("--" + hotel);
        }
        System.out.println();
        Assert.assertEquals(27, hotelEsRepo.count());
    }

    protected void fetchIndividual() {
        System.out.println("Hotel found with findByName('Conrad Treasury Place'):");
        System.out.println("--------------------------------");
        Hotel foundByName = hotelEsRepo.findByName("Conrad Treasury Place");
        System.out.println("--" + foundByName);
        Assert.assertEquals("Conrad Treasury Place", foundByName.getName());

        System.out.println("Hotel found with findByCity('Bath'):");
        System.out.println("--------------------------------");

        // City foundCityByName = cityEsRepo.findByName("Bath");
        City foundCityByName = cityRepo.findByNameAndCountryAllIgnoringCase("Bath", "UK");

        List<Hotel> foundByCity = hotelEsRepo.findByCity(foundCityByName);
        for (Hotel hotel : foundByCity) {
            System.out.println("--" + hotel);
        }
        Assert.assertEquals(3, foundByCity.size());
    }
}
