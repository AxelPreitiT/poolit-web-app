import {describe, MockInstance} from "vitest";
import * as dayStringFunctions from "@/utils/date/dayString.ts";
import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled.tsx";
import tripModel from "@/models/TripModel.ts";
import {customRender} from "@/__test__/utils.tsx";
import {screen} from "@testing-library/react";


const trip: tripModel = {
    carReviewsUriTemplate: "http://localhost:8080/paw-2023a-07/api/cars/1/reviews?forTrip=144&madeBy={userId}",
    carUri: "http://localhost:8080/paw-2023a-07/api/cars/1",
    deleted: false,
    destinationAddress: "Agronomia",
    destinationCityUri: "http://localhost:8080/paw-2023a-07/api/cities/1",
    driverReportsUriTemplate: "http://localhost:8080/paw-2023a-07/api/reports?forTrip=144&forUser=1&madeBy={userId}",
    driverReviewsUriTemplate: "http://localhost:8080/paw-2023a-07/api/driver-reviews?forTrip=144&madeBy={userId}",
    driverUri: "http://localhost:8080/paw-2023a-07/api/users/40",
    endDateTime: "2024-02-03T18:00",
    maxSeats: "4",
    originAddress: "Av Callao 1348",
    originCityUri: "http://localhost:8080/paw-2023a-07/api/cities/28",
    passengersUriTemplate: "http://localhost:8080/paw-2023a-07/api/trips/144/passengers{/userId}{?startDateTime,endDateTime,passengerState}",
    pricePerTrip: 1000.0,
    selfUri: "http://localhost:8080/paw-2023a-07/api/trips/144",
    startDateTime: "2024-02-03T18:00",
    tripId: 144,
    tripStatus: "NOT_STARTED"
};
const tripRecurrent: tripModel = {
    carReviewsUriTemplate: "http://localhost:8080/paw-2023a-07/api/cars/1/reviews?forTrip=144&madeBy={userId}",
    carUri: "http://localhost:8080/paw-2023a-07/api/cars/1",
    deleted: false,
    destinationAddress: "Agronomia",
    destinationCityUri: "http://localhost:8080/paw-2023a-07/api/cities/1",
    driverReportsUriTemplate: "http://localhost:8080/paw-2023a-07/api/reports?forTrip=144&forUser=1&madeBy={userId}",
    driverReviewsUriTemplate: "http://localhost:8080/paw-2023a-07/api/driver-reviews?forTrip=144&madeBy={userId}",
    driverUri: "http://localhost:8080/paw-2023a-07/api/users/40",
    endDateTime: "2024-02-03T18:00",
    maxSeats: "4",
    originAddress: "Av Callao 1348",
    originCityUri: "http://localhost:8080/paw-2023a-07/api/cities/28",
    passengersUriTemplate: "http://localhost:8080/paw-2023a-07/api/trips/144/passengers{/userId}{?startDateTime,endDateTime,passengerState}",
    pricePerTrip: 1000.0,
    selfUri: "http://localhost:8080/paw-2023a-07/api/trips/144",
    startDateTime: "2024-02-10T18:00",
    tripId: 144,
    tripStatus: "NOT_STARTED"
};

describe("CardTripScheduled",()=>{
    vi.mock('@/components/cardTrip/cardTrip/CardTrip',()=>
        ({default: ()=> <h1>Card Trip Mock</h1>})
    )

    vi.mock('@/functions/DateFormat.ts',()=>
        ({default: ()=> ({date:"DATE",time:"TIME"})})
    )

    it("Should show the trip single day",async ()=>{
        const dayStringSpy:MockInstance<[Date],any> = vi.spyOn(dayStringFunctions,'getDayString');
        dayStringSpy.mockReturnValue("Day String Mock");

        customRender(<CardTripScheduled data={trip} useExtraData={()=>null}/>)

        expect(await screen.findByText(/Day String Mock/i)).toBeVisible();
        expect(await screen.findByText(/DATE/i)).toBeVisible();
        expect(await screen.findByText(/Card Trip Mock/i)).toBeVisible();
    })

    it("Should show the trip start and end",async ()=>{
        const dayStringSpy:MockInstance<[Date],any> = vi.spyOn(dayStringFunctions,'getDayString');
        dayStringSpy.mockReturnValue("Day String Mock");

        customRender(<CardTripScheduled data={tripRecurrent} useExtraData={()=>null}/>)

        expect(await screen.findByText(/Day String Mock/i)).toBeVisible();
        expect(await screen.findByText(/DATE - DATE/i)).toBeVisible();
        expect(await screen.findByText(/Card Trip Mock/i)).toBeVisible();
    })
})