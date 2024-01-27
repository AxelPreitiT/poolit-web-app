import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import {publicsPassangerReviewsPath, reservedTripsPath} from "@/AppRouter";
import { useTranslation } from "react-i18next";
import ShortReview from "@/components/review/shorts/ShortReview";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";


export interface PassengerListProp {
  futureReservedTripsUri: string;
  pastReservedTripsUri: string;
  reviewsPassengerUri : string;
  id: number;
}

const PassengerList = ({
  futureReservedTripsUri,
  pastReservedTripsUri,
  reviewsPassengerUri,
  id
}: PassengerListProp) => {
  const { t } = useTranslation();

  const { isLoading: isLoadingFutureReservedTrips, data:futureReservedTrips } = useTripsByUri(futureReservedTripsUri);
  const { isLoading: isLoadingPastReservedTrips, data:pastReservedTrips } = useTripsByUri(pastReservedTripsUri);
  const { isLoading: isLoadingReviewsPassenger, data:reviewsPassenger } = useUserReviewsByUri(reviewsPassengerUri);


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
        data={reviewsPassenger.data}
        component_name={ShortReview}
        link={publicsPassangerReviewsPath.replace(":id", String(id))}
      />)}
      {futureReservedTrips == undefined || isLoadingFutureReservedTrips ? (
          <SpinnerComponent />
      ) : (
        <div>
          <ListProfileContainer
              title={t("profile.lists.reserved_next_title")}
              btn_footer_text={t("profile.lists.reserved_next_more")}
              empty_text={t("profile.lists.reserved_next_empty")}
              empty_icon={"car-front-fill"}
              data={futureReservedTrips.data}
              component_name={CardTripProfile}
              link={reservedTripsPath}
          />
        </div>
      )}
      {pastReservedTrips == undefined || isLoadingPastReservedTrips ? (
          <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.reserved_prev_title")}
          btn_footer_text={t("profile.lists.reserved_prev_more")}
          empty_text={t("profile.lists.reserved_prev_empty")}
          empty_icon={"car-front-fill"}
          data={pastReservedTrips.data}
          component_name={CardTripProfile}
          link={reservedTripsPath}
        />
      )}
    </div>
  );
};

export default PassengerList;
