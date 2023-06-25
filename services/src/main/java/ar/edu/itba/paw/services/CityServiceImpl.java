package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(final CityDao cityDao){
        this.cityDao = cityDao;
    }

    @Transactional
    @Override
    public Optional<City> findCityById(final long id){
        return cityDao.findCityById(id);
    }

    @Transactional
    @Override
    public List<City> getCitiesByProvinceId(final long provinceId){
        return cityDao.getCitiesByProvinceId(provinceId);
    }
}
