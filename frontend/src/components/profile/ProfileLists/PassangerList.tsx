import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { publicsPassangerReviewsPath, reservedTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import ShortReview from "@/components/review/shorts/ShortReview";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";
import LoadingWheel from "@/components/loading/LoadingWheel";
import styles from "./styles.module.scss";

export interface PassengerListProp {
  futureReservedTripsUri: string;
  pastReservedTripsUri: string;
  reviewsPassengerUri: string;
  id: number;
}

const PassengerList = ({
  futureReservedTripsUri,
  pastReservedTripsUri,
  reviewsPassengerUri,
  id,
}: PassengerListProp) => {
  const { t } = useTranslation();

  const { isLoading: isLoadingFutureReservedTrips, data: futureReservedTrips } =
    useTripsByUri(futureReservedTripsUri);
  const { isLoading: isLoadingPastReservedTrips, data: pastReservedTrips } =
    useTripsByUri(pastReservedTripsUri);
  const { isLoading: isLoadingReviewsPassenger, data: reviewsPassenger } =
    useUserReviewsByUri(reviewsPassengerUri);

  return (
    <div>
      {reviewsPassenger === undefined || isLoadingReviewsPassenger ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.reviews")}
        />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.review_as_passanger")}
          btn_footer_text={t("profile.lists.review_more")}
          empty_text={t("profile.lists.review_empty")}
          empty_icon={"book"}
          data={reviewsPassenger.data}
          component_name={ShortReview}
          link={publicsPassangerReviewsPath.replace(":id", id.toString())}
        />
      )}
      {futureReservedTrips === undefined || isLoadingFutureReservedTrips ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.trips")}
        />
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
      {pastReservedTrips === undefined || isLoadingPastReservedTrips ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.trips")}
        />
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
