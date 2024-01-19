import Service from "@/services/Service.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import PassangerApi from "@/api/PassangerApi.ts";

class PassangerService extends Service {

    public static getPassangersTrips = async (uri: string): Promise<UserPublicModel[]> => {
        return await this.resolveQuery(PassangerApi.getPassangerByUri(uri));
    };
}

export default PassangerService;
