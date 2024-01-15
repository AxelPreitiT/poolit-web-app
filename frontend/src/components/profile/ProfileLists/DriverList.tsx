import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { createdTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";
import ShortReview from "@/components/review/shorts/ShortReview";
import { publicsReviewsPath } from "@/AppRouter";
import CardCar from "@/components/cardCar/CardCar";

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

  const data2 = [
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
  ];
  const data3 = [
    {
      carId: 1,
      imageId: 1,
      infoCar: "Mondeo blanco",
      plate: "AAA111",
    },
  ];

  return (
    <div>
      <ListProfileContainer
        title={t("profile.lists.review_as_driver")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={data2}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />
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
      <ListProfileContainer
        title={t("profile.lists.cars")}
        btn_footer_text={t("profile.lists.cars_create")}
        empty_text={t("profile.lists.cars_empty")}
        empty_icon={"car-front-fill"}
        data={data3}
        component_name={CardCar}
        link={createdTripsPath}
      />
    </div>
  );
};

export default DriverList;
