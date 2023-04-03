package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.City;
import java.util.ArrayList;

public interface CityService {

    City findById(final int id);

    ArrayList<City> getCities();
}
