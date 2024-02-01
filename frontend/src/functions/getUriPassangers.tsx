import {parseTemplate} from "url-template";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
// import {useSearchParams} from "react-router-dom";
import passangerStatus from "@/enums/PassangerStatus.ts";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";

const getUriPassangers = ( isDriver:boolean, trip: tripModel, passanger?: passangerModel) => {
    // const [params] = useSearchParams();

    if (isDriver) {
        const startDateTime = trip.startDateTime;
        const endDateTime = trip.endDateTime;
        return parseTemplate(trip.passengersUriTemplate as string).expand({
            userId: null,
            startDateTime: startDateTime,
            endDateTime: endDateTime,
            passengerState: passangerStatus.ACCEPTED,
        });
    }
    const { currentUser } = useCurrentUser();
    return parseTemplate(passanger?.otherPassengersUriTemplate as string).expand({
        excluding: currentUser?.userId as number,
    });
}

export default getUriPassangers;
