package org.nh.rest.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.nh.rest.UnitTest;
import org.nh.rest.persistence.CityRepository;
import org.nh.rest.persistence.CitySearchCriteria;
import org.nh.rest.persistence.HotelRepository;
import org.springframework.data.domain.Pageable;

public class CityServiceTest extends UnitTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private CityService service = new CityServiceImpl();

    @Test
    public void findAllCities() {
        service.getAll();
        verify(cityRepository).findAll();
    }

    @Test
    public void findAllEmptyCriteria() {
        CitySearchCriteria criteria = new CitySearchCriteria();
        service.findCities(criteria, null);

        verify(cityRepository).findAll((Pageable) null);
    }

    @Test
    public void findAllWithCityCriteria() {
        String cityname = "cityname";

        CitySearchCriteria criteria = new CitySearchCriteria(cityname);
        service.findCities(criteria, null);

        verify(cityRepository).findByNameContainingAndCountryContainingAllIgnoringCase(cityname.trim(), "",
                (Pageable) null);
    }

    @Test
    public void findAllWithCityAndCountryCriteria() {
        String cityname = "cityname";
        String countryname = "countryname";

        CitySearchCriteria criteria = new CitySearchCriteria(cityname + "," + countryname);
        service.findCities(criteria, null);

        verify(cityRepository).findByNameContainingAndCountryContainingAllIgnoringCase(cityname.trim(),
                countryname.trim(), (Pageable) null);
    }
}
