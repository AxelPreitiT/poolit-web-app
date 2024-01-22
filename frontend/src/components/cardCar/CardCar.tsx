import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import CarModel from "@/models/CarModel.ts";
import CarImage from "../car/CarImage/CarImage";

const CardCar = (car: CarModel) => {
  const { t } = useTranslation();

  return (
    <div className={styles.car_card}>
      <CarImage className={styles.car_img} car={car} />
      <div className={styles.car_desc}>
        <h5>{car.infoCar}</h5>
        <h5>{t("format.plate", { carPlate: car.plate })}</h5>
      </div>
    </div>
  );
};

export default CardCar;
