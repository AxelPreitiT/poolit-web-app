package ar.edu.itba.paw.webapp.config.converters;

import ar.edu.itba.paw.models.FeatureCar;

import javax.ws.rs.ext.ParamConverter;

public class FeatureCarConverter implements ParamConverter<FeatureCar> {

    @Override
    public FeatureCar fromString(String value) {
        if(value == null){
            return null;
        }
        try {
            return FeatureCar.valueOf(value);
        }catch (Exception e){
            throw new ConversionException("conversions.featureCar");
        }
    }

    @Override
    public String toString(FeatureCar value) {
        return value.toString();
    }
}
