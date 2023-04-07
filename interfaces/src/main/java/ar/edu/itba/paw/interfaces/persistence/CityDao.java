package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;

import java.util.ArrayList;

public interface CityDao {

    City findById(final int id);

    ArrayList<City> getCities();
}
