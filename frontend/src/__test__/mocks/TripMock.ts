import BaseMock from "@/__test__/mocks/BaseMock.ts";
import tripModel from "@/models/TripModel.ts";
import passangerModel from "@/models/PassangerModel.ts";
import occupiedSeatsModel from "@/models/occupiedSeatsModel.ts";

const tripList:tripModel[] = [
        {
            "carReviewsUriTemplate": "http://localhost:8080/paw-2023a-07/api/cars/1/reviews?forTrip=144&madeBy={userId}",
            "carUri": "http://localhost:8080/paw-2023a-07/api/cars/1",
            "deleted": false,
            "destinationAddress": "Agronomia",
            "destinationCityUri": "http://localhost:8080/paw-2023a-07/api/cities/1",
            "driverReportsUriTemplate": "http://localhost:8080/paw-2023a-07/api/reports?forTrip=144&forUser=1&madeBy={userId}",
            "driverReviewsUriTemplate": "http://localhost:8080/paw-2023a-07/api/driver-reviews?forTrip=144&madeBy={userId}",
            "driverUri": "http://localhost:8080/paw-2023a-07/api/users/1",
            "endDateTime": "2024-02-03T18:00",
            "maxSeats": "4",
            "originAddress": "Av Callao 1348",
            "originCityUri": "http://localhost:8080/paw-2023a-07/api/cities/28",
            "passengersUriTemplate": "http://localhost:8080/paw-2023a-07/api/trips/144/passengers{/userId}{?startDateTime,endDateTime,passengerState}",
            "pricePerTrip": 1000.0,
            "selfUri": "http://localhost:8080/paw-2023a-07/api/trips/144",
            "startDateTime": "2024-02-03T18:00",
            "tripId": 144,
            "tripStatus": "NOT_STARTED"
        },
        {
            "carReviewsUriTemplate": "http://localhost:8080/paw-2023a-07/api/cars/15/reviews?forTrip=143&madeBy={userId}",
            "carUri": "http://localhost:8080/paw-2023a-07/api/cars/15",
            "deleted": false,
            "destinationAddress": "Barracas",
            "destinationCityUri": "http://localhost:8080/paw-2023a-07/api/cities/4",
            "driverReportsUriTemplate": "http://localhost:8080/paw-2023a-07/api/reports?forTrip=143&forUser=1&madeBy={userId}",
            "driverReviewsUriTemplate": "http://localhost:8080/paw-2023a-07/api/driver-reviews?forTrip=143&madeBy={userId}",
            "driverUri": "http://localhost:8080/paw-2023a-07/api/users/1",
            "endDateTime": "2024-02-03T18:00",
            "maxSeats": "4",
            "originAddress": "Av Callao 1348",
            "originCityUri": "http://localhost:8080/paw-2023a-07/api/cities/28",
            "passengersUriTemplate": "http://localhost:8080/paw-2023a-07/api/trips/143/passengers{/userId}{?startDateTime,endDateTime,passengerState}",
            "pricePerTrip": 1300.0,
            "selfUri": "http://localhost:8080/paw-2023a-07/api/trips/143",
            "startDateTime": "2024-02-03T18:00",
            "tripId": 143,
            "tripStatus": "NOT_STARTED"
        }
    ];

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

const passengerList: passangerModel[] = [
    {
        "endDateTime": "2023-12-28T22:00",
        "otherPassengersUriTemplate": "http://localhost:8080/paw-2023a-07/api/trips/139/passengers?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00&passengerState=ACCEPTED{&excluding*}",
        "passengerReportsForTripUriTemplate": "http://localhost:8080/paw-2023a-07/api/reports?forTrip=139&forUser=1&madeBy={userId}",
        "passengerReviewsForTripUriTemplate": "http://localhost:8080/paw-2023a-07/api/passenger-reviews?forTrip=139&forUser=1&madeBy={userId}",
        "passengerState": "ACCEPTED",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/trips/139/passengers/1",
        "startDateTime": "2023-12-28T22:00",
        "tripUri": "http://localhost:8080/paw-2023a-07/api/trips/139?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00",
        "userId": "1",
        "userUri": "http://localhost:8080/paw-2023a-07/api/users/1"
    }
]
const passenger: passangerModel = {
    "endDateTime": "2023-12-28T22:00",
    "otherPassengersUriTemplate": "http://localhost:8080/paw-2023a-07/api/trips/139/passengers?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00&passengerState=ACCEPTED{&excluding*}",
    "passengerReportsForTripUriTemplate": "http://localhost:8080/paw-2023a-07/api/reports?forTrip=139&forUser=1&madeBy={userId}",
    "passengerReviewsForTripUriTemplate": "http://localhost:8080/paw-2023a-07/api/passenger-reviews?forTrip=139&forUser=1&madeBy={userId}",
    "passengerState": "ACCEPTED",
    "selfUri": "http://localhost:8080/paw-2023a-07/api/trips/139/passengers/1",
    "startDateTime": "2023-12-28T22:00",
    "tripUri": "http://localhost:8080/paw-2023a-07/api/trips/139?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00",
    "userId": "1",
    "userUri": "http://localhost:8080/paw-2023a-07/api/users/1"
}

const occupiedSeats: occupiedSeatsModel = {
    occupiedSeats: 10
}

class TripMock extends BaseMock{
    public static mockEmptyTrips(){
        return this.get("/trips",()=>
            this.plainResponse({status: this.NO_CONTENT_STATUS})
        )
    }

    public static mockOccupiedSeats(){
        return this.get("/trips/:tripId/passengers",()=>
            this.jsonResponse(occupiedSeats,{status:this.OK_STATUS})
        )
    }

    public static mockTripList(){
        return this.get("/trips",()=>
            this.jsonResponse(tripList,{status:this.OK_STATUS})
        )
    }

    public static mockSingleTrip(){
        return this.get('/trips/:id', ()=>
            this.jsonResponse(trip,{status:this.OK_STATUS})
        )
    }
    public static mockPassengerList(){
        return this.get("/trips/:tripId/passengers",()=>
            this.jsonResponse(passengerList,{status:this.OK_STATUS})
        )
    }
    public static mockPassenger(){
        return this.get("/trips/:tripId/passengers/:userId",()=>
            this.jsonResponse(passenger,{status:this.OK_STATUS})
        )
    }
}

export default TripMock;