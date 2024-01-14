import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { publicsReviewsPath, reservedTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";

export interface PassengerListProp {
  futureReservedTripsUri: string;
  pastReservedTripsUri: string;
}

const PassengerList = ({
  futureReservedTripsUri,
  pastReservedTripsUri,
}: PassengerListProp) => {
  const { t } = useTranslation();

  const [trip, setTrip] = useState<TripModel[] | null>(null);

  useEffect(() => {
    tripsService.getTripsByUser(futureReservedTripsUri).then((response) => {
      setTrip(response);
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

  return (
    <div>
      <ListProfileContainer
        title={t("review_as_passanger")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={data2}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />
      {trip == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_next_title")}
          btn_footer_text={t("profile.lists.reserved_next_more")}
          empty_text={t("profile.lists.reserved_next_empty")}
          empty_icon={"car-front-fill"}
          data={trip}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
      {trip == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_prev_title")}
          btn_footer_text={t("profile.lists.reserved_prev_more")}
          empty_text={t("profile.lists.reserved_prev_empty")}
          empty_icon={"car-front-fill"}
          data={trip}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
      <h4>{pastReservedTripsUri}</h4>
    </div>
  );
};

export default PassengerList;
