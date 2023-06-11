package ar.edu.itba.paw.models;

public enum FeatureCar {
    AIR("createCar.airConditioning"),
    PET_FRIENDLY("createCar.petFriendly"),
    MUSIC("createCar.music"),
    TRUNK_SPACE("createCar.trunkSpace");

    private String code;

    private FeatureCar(String code){
        this.code = code;
    }
    @Override
    public String toString(){
        return code;
    }
}
