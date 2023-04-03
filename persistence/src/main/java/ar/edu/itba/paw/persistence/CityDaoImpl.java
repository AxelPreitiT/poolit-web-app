package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.models.City;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CityDaoImpl implements CityDao {

    private final Map<Integer, City> cities = new HashMap<>();

    public CityDaoImpl(){
        addCitiesToMap();
    }

    @Override
    public City findById(final int id) {
        return cities.get(id);
    }

    @Override
    public ArrayList<City> getCities() {
        return new ArrayList<>(cities.values());
    }

    private void addCitiesToMap(){
        cities.put(1, new City("Agronomía", 1));
        cities.put(2, new City("Almagro", 2));
        cities.put(3, new City("Balvanera", 3));
        cities.put(4, new City("Barracas", 4));
        cities.put(5, new City("Belgrano", 5));
        cities.put(6, new City("Boedo", 6));
        cities.put(7, new City("Caballito", 7));
        cities.put(8, new City("Chacarita", 8));
        cities.put(9, new City("Coghlan", 9));
        cities.put(10, new City("Colegiales", 10));
        cities.put(11, new City("Constitución", 11));
        cities.put(12, new City("Flores", 12));
        cities.put(13, new City("Floresta", 13));
        cities.put(14, new City("La Boca", 14));
        cities.put(15, new City("La Paternal", 15));
        cities.put(16, new City("Liniers", 16));
        cities.put(17, new City("Mataderos", 17));
        cities.put(18, new City("Monte Castro", 18));
        cities.put(19, new City("Montserrat", 18));
        cities.put(20, new City("Nueva Pompeya", 20));
        cities.put(21, new City("Núñez", 21));
        cities.put(22, new City("Palermo", 22));
        cities.put(23, new City("Parque Avellaneda", 23));
        cities.put(24, new City("Parque Chacabuco", 24));
        cities.put(25, new City("Parque Chas", 25));
        cities.put(26, new City("Parque Patricios", 26));
        cities.put(27, new City("Puerto Madero", 27));
        cities.put(28, new City("Recoleta", 28));
        cities.put(29, new City("Retiro", 29));
        cities.put(30, new City("Saavedra", 30));
        cities.put(31, new City("San Cristóbal", 31));
        cities.put(32, new City("San Nicolás", 32));
        cities.put(33, new City("San Telmo", 33));
        cities.put(34, new City("Vélez Sársfield", 34));
        cities.put(35, new City("Versalles", 35));
        cities.put(36, new City("Villa Crespo", 36));
        cities.put(37, new City("Villa del Parque", 37));
        cities.put(38, new City("Villa Devoto", 38));
        cities.put(39, new City("Villa Gral. Mitre", 39));
        cities.put(40, new City("Villa Lugano", 40));
        cities.put(41, new City("Villa Luro", 41));
        cities.put(42, new City("Villa Ortúzar", 42));
        cities.put(43, new City("Villa Pueyrredón", 43));
        cities.put(44, new City("Villa Real", 44));
        cities.put(45, new City("Villa Riachuelo", 45));
        cities.put(46, new City("Villa Santa Rita", 46));
        cities.put(47, new City("Villa Soldati", 47));
        cities.put(48, new City("Villa Urquiza", 48));
    }
}
