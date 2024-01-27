import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import {
  createCarsPath,
  createdTripsPath,
  publicsDriverReviewsPath,
} from "@/AppRouter";
import { useTranslation } from "react-i18next";
import ShortReview from "@/components/review/shorts/ShortReview";
import CardCar from "@/components/cardCar/CardCar";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";
import useUserCars from "@/hooks/cars/useUserCars.tsx";

export interface DriverListProp {
  futureCreatedTripsUri: string;
  pastCreatedTripsUri: string;
  reviewsDriverUri: string;
}

const DriverList = ({
  futureCreatedTripsUri,
  pastCreatedTripsUri,
  reviewsDriverUri,
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
      {reviewsDriver == undefined || isLoadingReviewsDriver ? (
        <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.review_as_driver")}
          btn_footer_text={t("profile.lists.review_more")}
          empty_text={t("profile.lists.review_empty")}
          empty_icon={"book"}
          data={reviewsDriver.data}
          component_name={ShortReview}
          link={publicsDriverReviewsPath.replace(":id", String(5))}
        />
      )}
      {futureCreatedTrips == undefined || isLoadingFutureCreatedTrips ? (
        <SpinnerComponent />
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
      {pastCreatedTrips == undefined || isLoadingPastCreatedTrips ? (
        <SpinnerComponent />
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
      {cars == undefined || isLoadingUserCars ? (
        <SpinnerComponent />
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
