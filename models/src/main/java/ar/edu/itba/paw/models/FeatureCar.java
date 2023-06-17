package ar.edu.itba.paw.models;

public enum FeatureCar {
    AIR("createCar.airConditioning"),
    PET_FRIENDLY("createCar.petFriendly"),
    MUSIC("createCar.music"),
    TRUNK_SPACE("createCar.trunkSpace");

    private final String code;

    private FeatureCar(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
