import { useTranslation } from "react-i18next";
import EditCarForm from "./EditCarForm";
import { useState } from "react";
import styles from "./styles.module.scss";
import CarModel from "@/models/CarModel";
import CarBrandModel from "@/models/CarBrandModel";
import CarFeatureModel from "@/models/CarFeatureModel";
import useCarById from "@/hooks/cars/useCarById";
import ViewableProfileImg from "@/components/profile/img/VieweableProfileImg";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import CarFeaturesProp from "@/components/profile/carFeatures/CarFeaturesProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import { Button } from "react-bootstrap";
import { BsPencilSquare } from "react-icons/bs";

interface CarInfoProps {
  car: CarModel;
  userCars?: CarModel[];
  carBrand?: CarBrandModel;
  allCarFeatures?: CarFeatureModel[];
  carFeatures: CarFeatureModel[];
}

const CarInfo = ({
  car,
  userCars,
  carBrand,
  allCarFeatures,
  carFeatures,
}: CarInfoProps) => {
  const { t } = useTranslation();
  const [editMode, setEditMode] = useState(false);
  const { invalidate: invalidateCar } = useCarById(car.carId.toString());

  return (
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
  );
};

export default CarInfo;
