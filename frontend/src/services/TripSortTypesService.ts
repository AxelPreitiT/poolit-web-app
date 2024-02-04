import TripSortTypesApi from "@/api/TripSortTypesApi";
import Service from "./Service";
import TripSortTypeModel from "@/models/TripSortTypeModel";

class TripSortTypesService extends Service {
  public static getTripSortTypes = async (
    uriTemplate: string
  ): Promise<TripSortTypeModel[]> => {
    return await this.resolveQuery(
      TripSortTypesApi.getTripSortTypes(uriTemplate)
    );
  };
}

export default TripSortTypesService;
