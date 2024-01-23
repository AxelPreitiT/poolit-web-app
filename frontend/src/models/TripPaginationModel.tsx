import TripModel from "@/models/TripModel.ts";

interface TripPaginationModel{
    first : String;
    trips: TripModel[];
}

export default TripPaginationModel;