import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { createdTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import ShortReview from "@/components/review/shorts/ShortReview";
import { publicsReviewsPath } from "@/AppRouter";
import CardCar from "@/components/cardCar/CardCar";
import CarService from "@/services/CarService.ts";
import CarModel from "@/models/CarModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import UserPrivateModel from "@/models/UserPrivateModel";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";

export interface DriverListProp {
  futureCreatedTripsUri: string;
  pastCreatedTripsUri: string;
  reviewsDriverUri : string;
  currentUser: UserPrivateModel;
}

const DriverList = ({
  futureCreatedTripsUri,
  pastCreatedTripsUri,
  reviewsDriverUri,
    currentUser
}: DriverListProp) => {
  const { t } = useTranslation();

  const { isLoading: isLoadingFutureCreatedTrips, trips:futureCreatedTrips } = useTripsByUri(futureCreatedTripsUri);
  const { isLoading: isLoadingPastCreatedTrips, trips:pastCreatedTrips } = useTripsByUri(pastCreatedTripsUri);
  const { isLoading: isLoadingReviewsDriver, reviews:reviewsDriver } = useUserReviewsByUri(reviewsDriverUri);


  const [Cars, setCars] = useState<CarModel[] | null>(null);


  useEffect(() => {
    CarService.getCarsByUser(currentUser).then((response) => {
      setCars(response);
    });
  });

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
          data={reviewsDriver}
          component_name={ShortReview}
          link={publicsReviewsPath.replace(":id", String(5))}
        />
      )}
      {futureCreatedTrips == undefined ||  isLoadingFutureCreatedTrips? (
        <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_next_title")}
          btn_footer_text={t("profile.lists.created_next_more")}
          empty_text={t("profile.lists.created_next_empty")}
          empty_icon={"car-front-fill"}
          data={futureCreatedTrips}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {pastCreatedTrips == undefined ||  isLoadingPastCreatedTrips? (
        <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_prev_title")}
          btn_footer_text={t("profile.lists.created_prev_more")}
          empty_text={t("profile.lists.created_prev_empty")}
          empty_icon={"car-front-fill"}
          data={pastCreatedTrips}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {Cars == null ? (
        <SpinnerComponent />
      ) : (
        <ListProfileContainer
          title={t("profile.lists.cars")}
          btn_footer_text={t("profile.lists.cars_create")}
          empty_text={t("profile.lists.cars_empty")}
          empty_icon={"car-front-fill"}
          data={Cars}
          component_name={CardCar}
          link={createdTripsPath}
        />
      )}
    </div>
  );
};

export default DriverList;
