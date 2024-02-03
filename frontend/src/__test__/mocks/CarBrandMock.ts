import carBrandModel from "@/models/CarBrandModel.ts";
import BaseMock from "@/__test__/mocks/BaseMock.ts";

const carBrandList: carBrandModel[] = [
    {
        "id": "VOLKSWAGEN",
        "name": "Volkswagen",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/VOLKSWAGEN"
    },
    {
        "id": "FORD",
        "name": "Ford",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/FORD"
    },
    {
        "id": "CHEVROLET",
        "name": "Chevrolet",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/CHEVROLET"
    },
    {
        "id": "RENAULT",
        "name": "Renault",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/RENAULT"
    },
    {
        "id": "FIAT",
        "name": "Fiat",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/FIAT"
    },
    {
        "id": "PEUGEOT",
        "name": "Peugeot",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/PEUGEOT"
    },
    {
        "id": "TOYOTA",
        "name": "Toyota",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/TOYOTA"
    },
    {
        "id": "HONDA",
        "name": "Honda",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/HONDA"
    },
    {
        "id": "CITROEN",
        "name": "Citroen",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/CITROEN"
    },
    {
        "id": "HYUNDAI",
        "name": "Hyundai",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/HYUNDAI"
    },
    {
        "id": "MERCEDES_BENZ",
        "name": "Mercedes Benz",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/MERCEDES_BENZ"
    },
    {
        "id": "BMW",
        "name": "BMW",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/BMW"
    },
    {
        "id": "AUDI",
        "name": "Audi",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/AUDI"
    },
    {
        "id": "KIA",
        "name": "Kia",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/KIA"
    },
    {
        "id": "MAZDA",
        "name": "Mazda",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/MAZDA"
    },
    {
        "id": "NISSAN",
        "name": "Nissan",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/NISSAN"
    },
    {
        "id": "SUZUKI",
        "name": "Suzuki",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/SUZUKI"
    },
    {
        "id": "JEEP",
        "name": "Jeep",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/JEEP"
    },
    {
        "id": "LAND_ROVER",
        "name": "Land Rover",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/LAND_ROVER"
    },
    {
        "id": "VOLVO",
        "name": "Volvo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/VOLVO"
    },
    {
        "id": "UNKNOWN",
        "name": "",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/UNKNOWN"
    }
];

const carBrand: carBrandModel ={
    "id": "VOLVO",
    "name": "Volvo",
    "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/VOLVO"
};

class CarBrandMock extends BaseMock{
    public static getAllBrands(){
        return this.get('/car-brands',()=>
            this.jsonResponse(carBrandList,{status:this.OK_STATUS})
        )
    }

    public static getBrand(){
        return this.get("/car-brands/:brandId",()=>
            this.jsonResponse(carBrand,{status:this.OK_STATUS})
        )
    }
}

export default CarBrandMock