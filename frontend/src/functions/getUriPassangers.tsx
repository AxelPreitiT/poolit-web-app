import {parseTemplate} from "url-template";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import {useSearchParams} from "react-router-dom";
import passangerStatus from "@/enums/PassangerStatus.ts";

const getUriPassangers = ( isDriver:boolean, passanger?: passangerModel, trip?:tripModel,) => {
    const [params] = useSearchParams();

    if (isDriver) {
        const startDateTime = params.get("startDateTime") || "";
        const endDateTime = params.get("endDateTime") || "";
        return parseTemplate(trip?.passengersUriTemplate as string).expand({
            userId: null,
            startDateTime: startDateTime,
            endDateTime: endDateTime,
            passengerState: passangerStatus.ACCEPTED,
        });
    }
    return passanger?.otherPassengersUri as string;
}

export default getUriPassangers;
