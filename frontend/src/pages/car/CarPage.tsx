import useCarById from "@/hooks/cars/useCarById";
import useUserCars from "@/hooks/cars/useUserCars";
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
import useCarFeaturesByUri from "@/hooks/cars/useCarFeaturesByUri";
import { Button } from "react-bootstrap";
import { BsPencilSquare } from "react-icons/bs";
import CarFeaturesProp from "@/components/profile/carFeatures/CarFeaturesProp";
import { INITIALPAGE, REVIEWPAGESIZE } from "@/enums/PaginationConstants";
import { useState } from "react";
import EditCarForm from "./EditCarForm";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import ViewableProfileImg from "@/components/profile/img/VieweableProfileImg";

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
    invalidate: invalidateCar,
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
  const [editMode, setEditMode] = useState(false);

  const page = new URLSearchParams(search).get("page");
  const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);

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
      <div className={styles.carInfoContainer}>
        {editMode ? (
          <EditCarForm
            car={car}
            carBrand={carBrand}
            carFeatures={allCarFeatures}
            initialCarFeatures={carFeatures}
            onCancel={() => setEditMode(false)}
            onSuccess={() => {
              setEditMode(false);
              invalidateCar();
            }}
          />
        ) : (
          <div className={styles.viewModeContainer}>
            <div className={styles.headerContainer}>
              <ViewableProfileImg src={car.imageUri} />
              <h3 className="mt-2">{car.infoCar}</h3>
            </div>
            <div className={styles.carInfo}>
              <ProfileProp
                prop={t("car.prop.brand")}
                text={carBrand?.name || t("car_brands.unknown")}
              />
              <ProfileProp
                prop={t("car.prop.seats")}
                text={car.seats.toString()}
              />
              <ProfileProp prop={t("car.prop.plate")} text={car.plate} />
              <CarFeaturesProp
                prop={t("car.prop.features")}
                carFeatures={carFeatures}
              />
              <ProfileStars prop={t("car.prop.rating")} rating={car.rating} />
            </div>
            {userCars &&
              userCars.length > 0 &&
              userCars.some((userCar) => userCar.carId === car.carId) && (
                <div className={styles.editButtonContainer}>
                  <Button
                    className="secondary-btn"
                    onClick={() => setEditMode(true)}
                  >
                    <BsPencilSquare className="light-text h4" />
                    <span className="light-text h4">{t("car.edit")}</span>
                  </Button>
                </div>
              )}
          </div>
        )}
      </div>
      <div className={styles.reviewsContainer}>
        <div className={styles.reviewsList}>
          <div className={styles.title}>
            <h2>{t("car.opinions")}</h2>
          </div>
          <PaginationComponent
            uri={createPaginationUri(
              car.reviewsUri,
              currentPage,
              REVIEWPAGESIZE,
              true
            )}
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
