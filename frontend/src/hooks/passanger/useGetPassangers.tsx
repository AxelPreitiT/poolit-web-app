import {useQuery} from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import {parseTemplate} from "url-template";

const useGetPassangers = ( isDriver:boolean, isPassanger: boolean, passanger?:passangerModel,  trip?:tripModel,) => {

    const query = useQuery({
        queryKey: ["allPassangers"],
        queryFn: async () => {
            if (isDriver) {
                const uri = parseTemplate(trip?.passengersUriTemplate as string).expand({});
                return await PassangerService.getPassangersTrips(uri);
            }
            if(isPassanger){
                return await PassangerService.getPassangersTrips(passanger?.otherPassengersUri as string);
            }
            return [];
        },
        retry: false,
        enabled: !!trip  && !!passanger,
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