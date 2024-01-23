import TripModel from "@/models/TripModel.ts";

interface TripPaginationModel{
    pagUri : String;
    trips: TripModel[];
}

export default TripPaginationModel;