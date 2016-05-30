package org.nh.rest.persistence.elasticsearch;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.model.City;

import org.springframework.beans.factory.annotation.Autowired;

public class CityElasticsearchRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private CityElasticsearchRepository repositoryES;

    @Test
    public void basicTest() {
        repositoryES.deleteAll();
        save();
        fetchAll();
        fetchIndividual();
    }

    private void save() {
        repositoryES.save(new City("Brisbane", "Australia"));
        repositoryES.save(new City("Melbourne", "Australia"));
        repositoryES.save(new City("Montreal", "Canada"));
        repositoryES.save(new City("Tel Aviv", "Israel"));
    }

    private void fetchAll() {
        System.out.println("City found with findAll():");
        System.out.println("-------------------------------");
        Iterable<City> allCities = repositoryES.findAll();
        Assert.assertEquals(4, repositoryES.count());
        for (City city : allCities) {
            System.out.println("--" + city);
        }
        System.out.println();
    }

    private void fetchIndividual() {
        System.out.println("city found with findByName('Brisbane'):");
        System.out.println("--------------------------------");
        City foundByName = repositoryES.findByName("Brisbane");
        System.out.println("--" + foundByName);
        Assert.assertEquals("Brisbane", foundByName.getName());

        System.out.println("city found with findByCountry('Australia'):");
        System.out.println("--------------------------------");
        List<City> foundByCountry = repositoryES.findByCountry("Australia");
        Assert.assertEquals(2, foundByCountry.size());
        for (City city : foundByCountry) {
            System.out.println("--" + city);
        }
    }
}
