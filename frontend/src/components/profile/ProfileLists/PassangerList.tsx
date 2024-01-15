import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { reservedTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";
import ShortReview from "@/components/review/shorts/ShortReview";
import { publicsReviewsPath } from "@/AppRouter";
import reviewsService from "@/services/ReviewsService.ts";


export interface PassengerListProp {
  futureReservedTripsUri: string;
  pastReservedTripsUri: string;
  selfUri : string;
}

const PassengerList = ({
  futureReservedTripsUri,
  pastReservedTripsUri,
   selfUri
}: PassengerListProp) => {
  const { t } = useTranslation();

  const [FutureReservedTrips, setFutureReservedTrips] = useState<
    TripModel[] | null
  >(null);
  const [PastReservedTrips, setPastReservedTrips] = useState<
    TripModel[] | null
  >(null);
  const [Reviews, setReviews] = useState<ReviewModel[] | null>(null);

  useEffect(() => {
    tripsService.getTripsByUser(futureReservedTripsUri).then((response) => {
      setFutureReservedTrips(response);
    });
  });

  useEffect(() => {
    tripsService.getTripsByUser(pastReservedTripsUri).then((response) => {
      setPastReservedTrips(response);
    });
  });

  useEffect(() => {
    reviewsService.getReviewsAsPassangerByUserId(selfUri).then((response) => {
      setReviews(response);
    });
  });


  return (
    <div>
      {Reviews == null ? (
          <h1>holaaa</h1>
      ) : (
      <ListProfileContainer
        title={t("profile.lists.review_as_passanger")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={Reviews}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />)}
      {FutureReservedTrips == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_next_title")}
          btn_footer_text={t("profile.lists.reserved_next_more")}
          empty_text={t("profile.lists.reserved_next_empty")}
          empty_icon={"car-front-fill"}
          data={FutureReservedTrips}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
      {PastReservedTrips == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_prev_title")}
          btn_footer_text={t("profile.lists.reserved_prev_more")}
          empty_text={t("profile.lists.reserved_prev_empty")}
          empty_icon={"car-front-fill"}
          data={PastReservedTrips}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
    </div>
  );
};

export default PassengerList;
