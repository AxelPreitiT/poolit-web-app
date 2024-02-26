package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.CarFeatureService;
import ar.edu.itba.paw.models.FeatureCar;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarFeatureServiceImpl implements CarFeatureService {
    @Override
    public List<FeatureCar> getCarFeatures() {
        return Arrays.stream(FeatureCar.values()).collect(Collectors.toList());
    }

    @Override
    public Optional<FeatureCar> findById(String id) {
        try {
            return Optional.of(FeatureCar.valueOf(id));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
