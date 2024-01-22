import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { reservedTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import ShortReview from "@/components/review/shorts/ShortReview";
import { publicsReviewsPath } from "@/AppRouter";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";


export interface PassengerListProp {
  futureReservedTripsUri: string;
  pastReservedTripsUri: string;
  reviewsPassengerUri : string;
}

const PassengerList = ({
  futureReservedTripsUri,
  pastReservedTripsUri,
  reviewsPassengerUri
}: PassengerListProp) => {
  const { t } = useTranslation();

  const { isLoading: isLoadingFutureReservedTrips, trips:futureReservedTrips } = useTripsByUri(futureReservedTripsUri);
  const { isLoading: isLoadingPastReservedTrips, trips:pastReservedTrips } = useTripsByUri(pastReservedTripsUri);
  const { isLoading: isLoadingReviewsPassenger, reviews:reviewsPassenger } = useUserReviewsByUri(reviewsPassengerUri);


  return (
    <div>
      {reviewsPassenger == undefined || isLoadingReviewsPassenger ? (
          <SpinnerComponent />
      ) : (
      <ListProfileContainer
        title={t("profile.lists.review_as_passanger")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={reviewsPassenger}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />)}
      {futureReservedTrips == undefined || isLoadingFutureReservedTrips ? (
          <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_next_title")}
          btn_footer_text={t("profile.lists.reserved_next_more")}
          empty_text={t("profile.lists.reserved_next_empty")}
          empty_icon={"car-front-fill"}
          data={futureReservedTrips}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
      {pastReservedTrips == undefined || isLoadingPastReservedTrips ? (
          <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_prev_title")}
          btn_footer_text={t("profile.lists.reserved_prev_more")}
          empty_text={t("profile.lists.reserved_prev_empty")}
          empty_icon={"car-front-fill"}
          data={pastReservedTrips}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
    </div>
  );
};

export default PassengerList;
