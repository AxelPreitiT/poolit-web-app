import useCarById from "@/hooks/cars/useCarById";
// import useUserCars from "@/hooks/cars/useUserCars";
import { useTranslation } from "react-i18next";
import { useLocation, useParams } from "react-router-dom";
import styles from "./styles.module.scss";
import LoadingScreen from "@/components/loading/LoadingScreen";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import useCarBrandByUri from "@/hooks/cars/useCarBrandByUri";
import useCarReviewsByUri from "@/hooks/cars/useCarReviewsByUri";
import createPaginationUri from "@/functions/CreatePaginationUri";
import EmptyList from "@/components/emptyList/EmptyList";
import ShortReview from "@/components/review/shorts/ShortReview";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent";
import ProfileImg from "@/components/profile/img/ProfileImg";

const CarPage = () => {
  const { t } = useTranslation();
  const { search } = useLocation();
  const { carId } = useParams();
  // const {
  //   isLoading: isUserCarsLoading,
  //   cars: userCars,
  //   isError: isUserCarsError,
  // } = useUserCars();
  const {
    isLoading: isCarLoading,
    car,
    isError: isCarError,
  } = useCarById(carId);
  const {
    isLoading: isCarBrandLoading,
    carBrand,
    isError: isCarBrandError,
  } = useCarBrandByUri(car?.brandUri);

  const page = new URLSearchParams(search).get("page");
  const currentPage = page == null ? 1 : parseInt(page, 10);

  if (
    // isUserCarsLoading ||
    isCarLoading ||
    isCarBrandLoading ||
    isCarError ||
    // isUserCarsError ||
    isCarBrandError ||
    !car
  ) {
    return <LoadingScreen description={t("car.loading")} />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.carInfoContainer}>
        <div className={styles.viewModeContainer}>
          <div className={styles.headerContainer}>
            <ProfileImg src={car.imageUri} />
            <h3 className="mt-2">{car.infoCar}</h3>
          </div>
          <div className={styles.carInfo}>
            <ProfileProp
              prop="Car brand"
              text={carBrand?.name || t("car_brands.unknown")}
            />
            <ProfileProp prop="Available seats" text={car.seats.toString()} />
            <ProfileProp prop="Car plate" text={car.plate} />
            <ProfileStars prop="Car rating" rating={car.rating} />
          </div>
        </div>
      </div>
      <div className={styles.reviewsContainer}>
        <div className={styles.reviewsList}>
          <div className={styles.title}>
            <h2>Opinions about the car</h2>
          </div>
          <PaginationComponent
            uri={createPaginationUri(car.reviewsUri, currentPage, 10, true)}
            current_page={currentPage}
            useFuction={useCarReviewsByUri}
            empty_component={
              <EmptyList text="No reviews" icon="bi-solid bi-book" />
            }
            component_name={ShortReview}
            itemsName={t("reviews.title")}
          />
        </div>
      </div>
    </div>
  );
};

export default CarPage;
