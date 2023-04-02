package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(final CityDao cityDao){
        this.cityDao = cityDao;
    }

    @Override
    public City findById(final int id){
        return cityDao.findById(id);
    }

    @Override
    public ArrayList<City> getCities(){
        return cityDao.getCities();
    }
}
