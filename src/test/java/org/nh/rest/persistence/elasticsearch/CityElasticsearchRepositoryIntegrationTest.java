package org.nh.rest.persistence.elasticsearch;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.model.City;

public class CityElasticsearchRepositoryIntegrationTest extends AbstractElasticsearchRepositoryIntegrationTest {

    @Test
    public void basicTest() {
        saveAll();
        fetchAll();
        fetchIndividual();
    }

    protected void fetchAll() {
        System.out.println("City found with findAll():");
        System.out.println("-------------------------------");
        Iterable<City> allCities = cityEsRepo.findAll();
        for (City city : allCities) {
            System.out.println("--" + city);
        }
        System.out.println();
        Assert.assertEquals(21, cityEsRepo.count());
    }

    protected void fetchIndividual() {
        System.out.println("city found with findByName('Brisbane'):");
        System.out.println("--------------------------------");
        City foundByName = cityEsRepo.findByName("Brisbane");
        System.out.println("--" + foundByName);
        Assert.assertEquals("Brisbane", foundByName.getName());

        System.out.println("city found with findByCountry('Australia'):");
        System.out.println("--------------------------------");
        List<City> foundByCountry = cityEsRepo.findByCountry("Australia");
        for (City city : foundByCountry) {
            System.out.println("--" + city);
        }
        Assert.assertEquals(3, foundByCountry.size());
    }

}
