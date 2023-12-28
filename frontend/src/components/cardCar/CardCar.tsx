import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";

export interface Car {
  carId: number;
  imageId: number;
  infoCar: string;
  plate: string;
}

const CardCar = (car: Car) => {
  const { t } = useTranslation();

  return (
    <div className={styles.car_card}>
      <img className={styles.img_car} src={ProfilePhoto} alt="Car" />
      <div className={styles.car_desc}>
        <h5>{car.infoCar}</h5>
        <h5>{t("format.plate", { carPlate: car.plate })}</h5>
      </div>
    </div>
  );
};

export default CardCar;
