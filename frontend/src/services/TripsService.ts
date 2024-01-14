import Service from "@/services/Service.ts";
import TripsApi from "@/api/TripsApi.ts";

class TripsService extends Service {

    public static getTripById = async (uri : string): Promise<TripModel> => {
        return await this.resolveQuery(TripsApi.getTripById(uri));
    };

}

export default TripsService;
