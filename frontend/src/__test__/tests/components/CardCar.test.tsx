import {describe, expect} from "vitest";
import {customRender} from "@/__test__/utils.tsx";
import {screen} from "@testing-library/react";
import CardCar from "@/components/cardCar/CardCar.tsx";
import carModel from "@/models/CarModel.ts";


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
describe("CardCar",()=>{
    vi.mock('@/components/car/CarImage/CarImage',()=>
        ({default: ()=> <h1>Image mock</h1>})
    )
    it("Should show the car info and plate",async ()=>{
        customRender(<CardCar {...car}/>)

        expect(await screen.findByText(/Image mock/i)).toBeVisible();
        expect(await screen.findByText(car.infoCar)).toBeVisible();
        expect(await screen.findByText(/AE062TE/i)).toBeVisible();
    })

})