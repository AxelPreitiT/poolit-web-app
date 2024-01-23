import Service from "@/services/Service.ts";
import PassangerApi from "@/api/PassangerApi.ts";
import PassangerModel from "@/models/PassangerModel.ts";

class PassangerService extends Service {

    public static getPassangersTrips = async (uri: string): Promise<PassangerModel[]> => {
        return await this.resolveQuery(PassangerApi.getPassangerByUri(uri));
    };

    public static getPassangerRole = async (uri: string): Promise<PassangerModel> => {
        return await this.resolveQuery(PassangerApi.getPassangerRole(uri));
    };
}

export default PassangerService;
