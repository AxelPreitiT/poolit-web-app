import Service from "@/services/Service.ts";
import PassangerApi from "@/api/PassangerApi.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import PaginationModel from "@/models/PaginationModel.ts";

class PassangerService extends Service {

    public static getPassangersTrips = async (uri: string): Promise<PassangerModel[]> => {
        return await this.resolveQuery(PassangerApi.getPassangers(uri));
    };

    public static getPassangerRole = async (uri: string): Promise<PassangerModel> => {
        return await this.resolveQuery(PassangerApi.getPassangerRole(uri));
    };

    public static getPassangersTripsByUri = async (uri: string): Promise<PaginationModel<PassangerModel>> => {
        return await this.resolveQuery(PassangerApi.getPassangerByUri(uri));
    };

    public static patchAcceptPassangersByUri = async (uri: string) => {
        return await this.resolveQuery(PassangerApi.patchAcceptPassangerByUri(uri));
    };

    public static getReview = async (uri: string): Promise<boolean> => {
        return await PassangerApi.getReviewPassanger(uri);
    }
}

export default PassangerService;
