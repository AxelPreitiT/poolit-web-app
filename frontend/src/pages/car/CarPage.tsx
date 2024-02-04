import useCarById from "@/hooks/cars/useCarById";
import useUserCars from "@/hooks/cars/useUserCars";
import { useTranslation } from "react-i18next";
import { useLocation, useParams } from "react-router-dom";
import styles from "./styles.module.scss";
import LoadingScreen from "@/components/loading/LoadingScreen";
import useCarBrandByUri from "@/hooks/cars/useCarBrandByUri";
import useCarReviewsByUri from "@/hooks/cars/useCarReviewsByUri";
import createPaginationUri from "@/functions/CreatePaginationUri";
import EmptyList from "@/components/emptyList/EmptyList";
import ShortReview from "@/components/review/shorts/ShortReview";
import useCarFeaturesByUri from "@/hooks/cars/useCarFeaturesByUri";
import { INITIALPAGE, REVIEWPAGESIZE } from "@/enums/PaginationConstants";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import PaginationComponentExtraData from "@/components/pagination/PaginationComponent/PaginationComponentExtraData.tsx";
import CarInfo from "./CarInfo";

const CarPage = () => {
  const { t } = useTranslation();
  const { search } = useLocation();
  const { carId } = useParams();
  const {
    isLoading: isUserCarsLoading,
    cars: userCars,
    isError: isUserCarsError,
  } = useUserCars();
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
  const {
    isLoading: isCarFeaturesLoading,
    carFeatures,
    isError: isCarFeaturesError,
  } = useCarFeaturesByUri(car?.featuresUri || []);
  const {
    isLoading: isAllCarFeaturesLoading,
    carFeatures: allCarFeatures,
    isError: isAllCarFeaturesError,
  } = useCarFeatures();

  const page = new URLSearchParams(search).get("page");
  const currentPage = page === null ? INITIALPAGE : parseInt(page, 10);

  if (
    isUserCarsLoading ||
    isCarLoading ||
    isCarBrandLoading ||
    isCarFeaturesLoading ||
    isAllCarFeaturesLoading ||
    isCarError ||
    isUserCarsError ||
    isCarBrandError ||
    isCarFeaturesError ||
    isAllCarFeaturesError ||
    !car
  ) {
    return <LoadingScreen description={t("car.loading")} />;
  }

  return (
    <div className={styles.mainContainer}>
      <CarInfo
        car={car}
        userCars={userCars}
        carBrand={carBrand}
        carFeatures={carFeatures}
        allCarFeatures={allCarFeatures}
      />
      <div className={styles.reviewsContainer}>
        <div className={styles.reviewsList}>
          <div className={styles.title}>
            <h2>{t("car.opinions")}</h2>
          </div>
          <PaginationComponentExtraData
            CardComponent={ShortReview}
            uri={createPaginationUri(
              car.reviewsUri,
              currentPage,
              REVIEWPAGESIZE,
              true
            )}
            useFuction={useCarReviewsByUri}
            empty_component={
              <EmptyList text={t("reviews.none")} icon="bi-solid bi-book" />
            }
            itemsName={t("reviews.title")}
          />
        </div>
      </div>
    </div>
  );
};

export default CarPage;
