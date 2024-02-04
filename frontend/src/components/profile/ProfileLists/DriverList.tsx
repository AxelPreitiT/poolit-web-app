import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import {
  createCarsPath,
  createdTripsPath,
  publicsDriverReviewsPath,
} from "@/AppRouter";
import { useTranslation } from "react-i18next";
import CardCar from "@/components/cardCar/CardCar";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";
import useUserCars from "@/hooks/cars/useUserCars.tsx";
import LoadingWheel from "@/components/loading/LoadingWheel";
import styles from "./styles.module.scss";
import ShortReviewProfile from "@/components/review/shorts/ShortReviewProfile.tsx";

export interface DriverListProp {
  futureCreatedTripsUri: string;
  pastCreatedTripsUri: string;
  reviewsDriverUri: string;
  id: number;
}

const DriverList = ({
  futureCreatedTripsUri,
  pastCreatedTripsUri,
  reviewsDriverUri,
  id,
}: DriverListProp) => {
  const { t } = useTranslation();

  const { isLoading: isLoadingFutureCreatedTrips, data: futureCreatedTrips } =
    useTripsByUri(futureCreatedTripsUri);
  const { isLoading: isLoadingPastCreatedTrips, data: pastCreatedTrips } =
    useTripsByUri(pastCreatedTripsUri);
  const { isLoading: isLoadingReviewsDriver, data: reviewsDriver } =
    useUserReviewsByUri(reviewsDriverUri);
  const { isLoading: isLoadingUserCars, cars } = useUserCars();

  return (
    <div>
      {reviewsDriver === undefined || isLoadingReviewsDriver ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.reviews")}
        />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.review_as_driver")}
          btn_footer_text={t("profile.lists.review_more")}
          empty_text={t("profile.lists.review_empty")}
          empty_icon={"book"}
          data={reviewsDriver.data}
          component_name={ShortReviewProfile}
          link={publicsDriverReviewsPath.replace(":id", id.toString())}
        />
      )}
      {futureCreatedTrips === undefined || isLoadingFutureCreatedTrips ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.trips")}
        />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_next_title")}
          btn_footer_text={t("profile.lists.created_next_more")}
          empty_text={t("profile.lists.created_next_empty")}
          empty_icon={"car-front-fill"}
          data={futureCreatedTrips.data}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {pastCreatedTrips === undefined || isLoadingPastCreatedTrips ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.trips")}
        />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_prev_title")}
          btn_footer_text={t("profile.lists.created_prev_more")}
          empty_text={t("profile.lists.created_prev_empty")}
          empty_icon={"car-front-fill"}
          data={pastCreatedTrips.data}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {cars === undefined || isLoadingUserCars ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.cars")}
        />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.cars")}
          btn_footer_text={t("profile.lists.cars_create")}
          empty_text={t("profile.lists.cars_empty")}
          empty_icon={"car-front-fill"}
          data={cars}
          component_name={CardCar}
          link={createCarsPath}
        />
      )}
    </div>
  );
};

export default DriverList;
