import carModel from "@/models/CarModel.ts";
import BaseMock from "@/__test__/mocks/BaseMock.ts";
import carReviewModel from "@/models/CarReviewModel.ts";

const carList: carModel[] = [
    {
        "brandUri": "http://localhost:8080/paw-2023a-07/api/car-brands/UNKNOWN",
        "carId": 7,
        "featuresUri": [],
        "imageUri": "http://localhost:8080/paw-2023a-07/api/cars/7/image",
        "infoCar": "Honda Fit azul",
        "plate": "AE062TP",
        "rating": 0.0,
        "reviewsUri": "http://localhost:8080/paw-2023a-07/api/cars/7/reviews",
        "seats": 4,
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cars/7"
    },
    {
        "brandUri": "http://localhost:8080/paw-2023a-07/api/car-brands/UNKNOWN",
        "carId": 26,
        "featuresUri": [],
        "imageUri": "http://localhost:8080/paw-2023a-07/api/cars/26/image",
        "infoCar": "Honda Fit azul",
        "plate": "AE062TE",
        "rating": 0.0,
        "reviewsUri": "http://localhost:8080/paw-2023a-07/api/cars/26/reviews",
        "seats": 4,
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cars/26"
    }
]

const car: carModel = {
    "brandUri": "http://localhost:8080/paw-2023a-07/api/car-brands/UNKNOWN",
    "carId": 26,
    "featuresUri": [],
    "imageUri": "http://localhost:8080/paw-2023a-07/api/cars/26/image",
    "infoCar": "Honda Fit azul",
    "plate": "AE062TE",
    "rating": 0.0,
    "reviewsUri": "http://localhost:8080/paw-2023a-07/api/cars/26/reviews",
    "seats": 4,
    "selfUri": "http://localhost:8080/paw-2023a-07/api/cars/26"
}

const carReviewList: carReviewModel[] = [
    {
        "carUri": "http://localhost:8080/paw-2023a-07/api/cars/40",
        "comment": "Me encanto el color del auto",
        "id": 13,
        "option": "CLEAN",
        "rating": 3,
        "reviewDateTime": "2024-01-08T23:37:16.998",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cars/40/reviews/13",
        "tripUri": "http://localhost:8080/paw-2023a-07/api/trips/139"
    }
]
const carReview: carReviewModel = {
    "carUri": "http://localhost:8080/paw-2023a-07/api/cars/40",
    "comment": "Me encanto el color del auto",
    "id": 13,
    "option": "CLEAN",
    "rating": 3,
    "reviewDateTime": "2024-01-08T23:37:16.998",
    "selfUri": "http://localhost:8080/paw-2023a-07/api/cars/40/reviews/13",
    "tripUri": "http://localhost:8080/paw-2023a-07/api/trips/139"
}

class CarMock extends BaseMock{
    public static getCars(){
        return this.get("/cars",()=>
            this.jsonResponse(carList,{status:this.OK_STATUS})
        )
    }
    public static getCar(){
        return this.get("/cars/:carId", ()=>
            this.jsonResponse(car,{status:this.OK_STATUS})
        )
    }
    public static getCarReviews(){
        return this.get("/cars/:carId/reviews",()=>
            this.jsonResponse(carReviewList,{status:this.OK_STATUS})
        )
    }

    public static getCarReview(){
        return this.get("/cars/:carId/reviews/:reviewId",()=>
            this.jsonResponse(carReview,{status:this.OK_STATUS})
        )
    }
    public static getEmptyCarImage(){
        return this.get("/cars/:carId/image",()=>
            this.plainResponse({status:this.NO_CONTENT_STATUS})
        )
    }
}

export default CarMock;