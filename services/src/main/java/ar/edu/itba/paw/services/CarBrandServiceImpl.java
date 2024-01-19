package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.CarBrandService;
import ar.edu.itba.paw.models.CarBrand;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    @Override
    public List<CarBrand> getCarBrands() {
        return Arrays.stream(CarBrand.values()).collect(Collectors.toList());
    }

    @Override
    public Optional<CarBrand> findById(String id) {
        try{
            return Optional.of(CarBrand.valueOf(id));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
