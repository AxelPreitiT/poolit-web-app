import {parseTemplate} from "url-template";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import {useSearchParams} from "react-router-dom";

const getUriPassangers = ( isDriver:boolean, isPassanger: boolean, passanger?: passangerModel, trip?:tripModel,) => {
    const [params] = useSearchParams();

    if (isDriver) {
        const startDateTime = params.get("startDateTime") || "";
        const endDateTime = params.get("endDateTime") || "";
        return parseTemplate(trip?.passengersUriTemplate as string).expand({
            userId: null,
            startDateTime: startDateTime,
            endDateTime: endDateTime,
            passengerState: null,
        });
    }
    if(isPassanger){
        return passanger?.otherPassengersUri as string;
    }
    return [];
}

export default getUriPassangers;
