import Service from "@/services/Service.ts";
import TripsApi from "@/api/TripsApi.ts";

class TripsService extends Service {

    public static getTripById = async (uri : string): Promise<TripModel> => {
        return await this.resolveQuery(TripsApi.getTripById(uri));
    };

    public static getTripsByUser = async (uri : string): Promise<TripModel[]> => {
        return await this.resolveQuery(TripsApi.getTripsByUser(uri));
    };
}

export default TripsService;
