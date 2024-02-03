import { parseTemplate } from "url-template";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import passangerStatus from "@/enums/PassangerStatus.ts";
import UserPrivateModel from "@/models/UserPrivateModel";

const getUriPassangers = (
  isDriver: boolean,
  trip: tripModel,
  passanger?: passangerModel,
  currentUser?: UserPrivateModel
) => {
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
  return parseTemplate(passanger?.otherPassengersUriTemplate as string).expand({
    excluding: currentUser?.userId as number,
  });
};

export default getUriPassangers;
