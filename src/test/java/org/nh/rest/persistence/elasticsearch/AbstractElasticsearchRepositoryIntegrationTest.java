package org.nh.rest.persistence.elasticsearch;

import org.nh.rest.IntegrationTest;
import org.nh.rest.persistence.relational.CityRepository;
import org.nh.rest.persistence.relational.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractElasticsearchRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    protected CityRepository cityRepo;

    @Autowired
    protected HotelRepository hotelRepo;

    @Autowired
    protected CityElasticsearchRepository cityEsRepo;

    @Autowired
    protected HotelElasticsearchRepository hotelEsRepo;

    protected void saveAll() {
        // cityEsRepo.save(new City("Brisbane", "Australia"));
        // cityEsRepo.save(new City("Melbourne", "Australia"));
        // cityEsRepo.save(new City("Montreal", "Canada"));
        // cityEsRepo.save(new City("Tel Aviv", "Israel"));

        cityEsRepo.deleteAll();
        hotelEsRepo.deleteAll();

        cityEsRepo.save(cityRepo.findAll());
        hotelEsRepo.save(hotelRepo.findAll());
    }

}
