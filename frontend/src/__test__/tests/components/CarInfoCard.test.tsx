import {describe, expect} from "vitest";
import carModel from "@/models/CarModel.ts";
import {customRender} from "@/__test__/utils.tsx";
import CarInfoCard from "@/components/car/CarInfoCard/CarInfoCard.tsx";
import {screen} from "@testing-library/react";

// const carBrand: carBrandModel ={
//     "id": "VOLVO",
//     "name": "Volvo",
//     "selfUri": "http://localhost:8080/paw-2023a-07/api/car-brands/VOLVO"
// };

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

describe("CarInfoCard",()=>{
    vi.mock('@/components/loading/LoadingWheel',()=>
        ({default: ()=> <h1>Loading mock</h1>})
    )
    // it("Should show the car info",async ()=>{
    //     vi.mock('@/hooks/cars/useCarBrandByUri',()=> {
    //         return {default: ()=>({isLoading: false,carBrand:carBrand})}
    //     })
    //     customRender(<CarInfoCard car={car}/>);
    //     await waitFor(async ()=>expect(await screen.findByText(/Volvo/i)).toBeVisible())
    //     expect(await screen.findByText(/AE062TE/i)).toBeVisible();
    // })
    it("Should show the loading animation",async ()=>{
        vi.mock('@/hooks/cars/useCarBrandByUri',()=> {
            return {default: ()=>({isLoading: true})}
        })
        customRender(<CarInfoCard car={car}/>);
        expect(await screen.queryByText(/Loading mock/i)).toBeVisible();

    })


})