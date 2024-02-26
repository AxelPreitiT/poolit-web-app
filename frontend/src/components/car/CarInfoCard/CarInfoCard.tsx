import CarModel from "@/models/CarModel";
import styles from "./styles.module.scss";
import { FaCaretRight } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import useCarBrandByUri from "@/hooks/cars/useCarBrandByUri";
import LoadingWheel from "@/components/loading/LoadingWheel";

interface CarInfoCarsProps {
  car?: CarModel;
}

const CarInfoCard = ({ car }: CarInfoCarsProps) => {
  const { t } = useTranslation();
  const { isLoading: isCarBrandLoading, carBrand } = useCarBrandByUri(
    car?.brandUri
  );

  if (!car) {
    return null;
  }

  if (isCarBrandLoading) {
    return (
      <LoadingWheel
        descriptionClassName={styles.loadingDescription}
        containerClassName={styles.loadingContainer}
        description={t("car_brands.searching_one")}
      />
    );
  }

  return (
    <div className={styles.carInfoContainer}>
      {carBrand && (
        <>
          <div className={styles.carInfoItem}>
            <FaCaretRight className="light-text" />
            <span className="light-text">
              {t("car.brand", {
                brand: carBrand.name || t("car_brands.unknown"),
              })}
            </span>
          </div>
          <hr className="light-text" />
        </>
      )}
      <div className={styles.carInfoItem}>
        <FaCaretRight className="light-text" />
        <span className="light-text">
          {t("car.license_plate", {
            license_plate: car.plate,
          })}
        </span>
      </div>
    </div>
  );
};

export default CarInfoCard;
