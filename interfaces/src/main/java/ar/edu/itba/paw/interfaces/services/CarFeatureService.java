package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.FeatureCar;

import java.util.List;
import java.util.Optional;

public interface CarFeatureService {
    List<FeatureCar> getCarFeatures();
    Optional<FeatureCar> findById(final String id);
}
