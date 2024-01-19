package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandController {
    List<CarBrand> getCarBrands();

    Optional<CarBrand> findById()
}
