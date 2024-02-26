import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import CarModel from "@/models/CarModel.ts";
import { useNavigate } from "react-router-dom";
import { carPath } from "@/AppRouter";
import CarImage from "../car/CarImage/CarImage";

const CardCar = (car: CarModel) => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const carInfoPath = carPath.replace(":carId", car.carId.toString());

  return (
    <div className={styles.car_card}>
      <CarImage
        car={car}
        className={styles.carImage}
        onClick={() => navigate(carInfoPath)}
      />
      <div className={styles.car_desc}>
        <h5>{car.infoCar}</h5>
        <h5>{t("format.plate", { carPlate: car.plate })}</h5>
      </div>
    </div>
  );
};

export default CardCar;
