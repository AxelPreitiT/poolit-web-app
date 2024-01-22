import CarModel from "@/models/CarModel";
import styles from "./styles.module.scss";
import { FaCaretRight } from "react-icons/fa";
import { useTranslation } from "react-i18next";

interface CarInfoCarsProps {
  car?: CarModel;
}

const CarInfoCard = ({ car }: CarInfoCarsProps) => {
  const { t } = useTranslation();

  if (!car) {
    return null;
  }

  return (
    <div className={styles.carInfoContainer}>
      <div className={styles.carInfoItem}>
        <FaCaretRight className="light-text" />
        <span className="light-text">
          {t("car.brand", { brand: car.brand })}
        </span>
      </div>
      <hr className="light-text" />
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
