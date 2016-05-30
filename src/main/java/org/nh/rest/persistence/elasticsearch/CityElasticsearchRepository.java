package org.nh.rest.persistence.elasticsearch;

import java.util.List;

import org.nh.rest.model.City;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 * @see <a href=
 *      "http://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/">
 *      spring data elasticsearch</a>
 * @see <a href=
 *      "http://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.query-methods.criterions">
 *      query methods criterions</a>
 * 
 * @author hernan.leoni
 *
 */
@RepositoryRestResource(exported = false)
public interface CityElasticsearchRepository extends ElasticsearchRepository<City, Long> {

    public City findByName(String name);

    public List<City> findByCountry(String country);
}
