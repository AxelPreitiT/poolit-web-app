import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg";
import StarRating from "@/components/stars/StarsRanking";
import TripModel from "@/models/TripModel.ts";
import CarModel from "@/models/CarModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import {useTranslation} from "react-i18next";

interface TripInfoProps {
    trip: TripModel;
    car: CarModel;
    driver: UserPublicModel;
}

const TripInfo = ({trip, car, driver} : TripInfoProps) => {
    const availableSeats = parseInt(trip.maxSeats , 10) - parseInt(trip.occupiedSeats, 10);
    const { t } = useTranslation();

    return (
    <div className={styles.info_trip}>
      <div className={styles.show_row}>
        <i className="bi bi-clock light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">{trip.startDateTime}</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-calendar light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER DIA</span>
          <span className={styles.subtitle_info}>{trip.startDateTime}</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <CircleImg
          src={car.imageUri}
          size={40}
        />
        <div className={styles.info_details}>
          <span className="light-text detail">{car.infoCar}</span>
          <StarRating rating={car.rating} color={"#ffffff"} size="10" />
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-people light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">{availableSeats}</span>
        </div>
      </div>
      <hr className="white" />
      <div className={styles.show_row}>
        <CircleImg
          src={driver.imageUri}
          size={40}
        />
        <div className={styles.info_details}>
          <span className="light-text detail">
              {t("format.name", {
              name: driver.username,
              surname: driver.surname,
          })}</span>
          <StarRating rating={0} color={"#ffffff"} size="10" />
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-envelope-fill light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER EMAIL</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-telephone-fill light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER CELU</span>
        </div>
      </div>
    </div>
  );
};

export default TripInfo;
