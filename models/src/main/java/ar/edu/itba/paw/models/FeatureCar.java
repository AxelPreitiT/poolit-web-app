package ar.edu.itba.paw.models;

public enum FeatureCar {
    AIR("features.air"),
    PET_FRIENDLY("features.petFriendly");

    private String code;

    private FeatureCar(String code){
        this.code = code;
    }
    @Override
    public String toString(){
        return code;
    }
}
