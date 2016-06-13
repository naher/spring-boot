package org.nh.rest.persistence.elasticsearch;

import java.util.List;

import org.nh.rest.model.City;
import org.nh.rest.model.Hotel;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HotelElasticsearchRepository extends ElasticsearchRepository<Hotel, Long> {

    public Hotel findByName(String name);

    public List<Hotel> findByCity(City city);
}
