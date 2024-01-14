import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { createdTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";

export interface DriverListProp {
  futureCreatedTripsUri: string;
  pastCreatedTripsUri: string;
}

const DriverList = ({
  futureCreatedTripsUri,
  pastCreatedTripsUri,
}: DriverListProp) => {
  const { t } = useTranslation();

  const [FutureCreatedTrips, setFutureCreatedTrips] = useState<
    TripModel[] | null
  >(null);
  const [PastCreatedTrips, setPastCreatedTrips] = useState<TripModel[] | null>(
    null
  );

  useEffect(() => {
    tripsService.getTripsByUser(futureCreatedTripsUri).then((response) => {
      setFutureCreatedTrips(response);
    });
  });

  useEffect(() => {
    tripsService.getTripsByUser(pastCreatedTripsUri).then((response) => {
      setPastCreatedTrips(response);
    });
  });


  return (
    <div>
      {FutureCreatedTrips == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_next_title")}
          btn_footer_text={t("profile.lists.created_next_more")}
          empty_text={t("profile.lists.created_next_empty")}
          empty_icon={"car-front-fill"}
          data={FutureCreatedTrips}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {PastCreatedTrips == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_prev_title")}
          btn_footer_text={t("profile.lists.created_prev_more")}
          empty_text={t("profile.lists.created_prev_empty")}
          empty_icon={"car-front-fill"}
          data={PastCreatedTrips}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
    </div>
  );
};

export default DriverList;
