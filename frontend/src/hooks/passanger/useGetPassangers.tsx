import {useQuery} from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import {parseTemplate} from "url-template";

const useGetPassangers = ( isDriver:boolean, isPassanger: boolean, params:URLSearchParams, passanger?:passangerModel,  trip?:tripModel,) => {
    const query = useQuery({
        queryKey: ["allPassangers"],
        queryFn: async () => {
            if (isDriver) {
                const startDateTime = params.get("startDateTime") || "";
                const endDateTime = params.get("endDateTime") || "";
                const uri = parseTemplate(trip?.passengersUriTemplate as string).expand({
                    userId: null,
                    startDateTime: startDateTime,
                    endDateTime: endDateTime,
                    passengerState: null,
                });
                return await PassangerService.getPassangersTrips(uri);
            }
            if(isPassanger){
                return await PassangerService.getPassangersTrips(passanger?.otherPassengersUri as string);
            }
            return [];
        },
        retry: false,
        enabled: !!trip,
    });

    const { isError, data, isLoading, isPending } = query;


    return {
        ...query,
        isError,
        isLoading: isLoading || isPending,
        passangers: data,
    };
};

export default useGetPassangers;
