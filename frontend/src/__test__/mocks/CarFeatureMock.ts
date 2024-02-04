import carFeatureModel from "@/models/CarFeatureModel.ts";
import BaseMock from "@/__test__/mocks/BaseMock.ts";

const carFeatureList: carFeatureModel[]= [
        {
            "id": "AIR",
            "name": "Aire acondicionado",
            "selfUri": "http://localhost:8080/paw-2023a-07/api/car-features/AIR"
        },
        {
            "id": "PET_FRIENDLY",
            "name": "Amigable con mascotas",
            "selfUri": "http://localhost:8080/paw-2023a-07/api/car-features/PET_FRIENDLY"
        },
        {
            "id": "MUSIC",
            "name": "Musica",
            "selfUri": "http://localhost:8080/paw-2023a-07/api/car-features/MUSIC"
        },
        {
            "id": "TRUNK_SPACE",
            "name": "Espacio en el baul",
            "selfUri": "http://localhost:8080/paw-2023a-07/api/car-features/TRUNK_SPACE"
        }
    ];

const carFeature: carFeatureModel = {
    "id": "TRUNK_SPACE",
    "name": "Espacio en el baul",
    "selfUri": "http://localhost:8080/paw-2023a-07/api/car-features/TRUNK_SPACE"
};

class CarFeatureMock extends BaseMock{
    public static getAllFeatures(){
        return this.get("/car-features",()=>
            this.jsonResponse(carFeatureList,{status:this.OK_STATUS})
        )
    }
    public static getFeature(){
        return this.get("/car-features/:featureId",()=>
            this.jsonResponse(carFeature,{status:this.OK_STATUS})
        )
    }
}
export default CarFeatureMock;