import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg";
import StarRating from "@/components/stars/StarsRanking";
import TripModel from "@/models/TripModel.ts";
import CarModel from "@/models/CarModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import {Link} from "react-router-dom";
import {publicCarPath, publicProfilePath} from "@/AppRouter.tsx";
import {getDayString} from "@/utils/date/dayString.ts";

interface TripInfoProps {
  trip: TripModel;
  car: CarModel;
  driver: UserPublicModel;
  isDriver: boolean;
}

const TripInfo = ({trip, car, driver, isDriver} : TripInfoProps) => {
    const availableSeats = parseInt(trip.maxSeats , 10) - parseInt(trip.occupiedSeats, 10);
    const { t } = useTranslation();
    const date = new Date(trip.startDateTime)

  return (
    <div className={styles.info_trip}>
      <div className={styles.show_row}>
        <i className="bi bi-clock light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">
            {getFormattedDateTime(trip.startDateTime).time}
          </span>
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-calendar light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">
              {t(`day.full.${getDayString(date).toLowerCase()}`, {
              plural: "s",})}
          </span>
          <span className={styles.subtitle_info}>{getFormattedDateTime(trip.startDateTime).date}</span>
        </div>
      </div>
      <div className={styles.show_row}>
          <Link to={publicCarPath.replace(":id", String(car.carId))}>
              <CircleImg
                  src={car.imageUri}
                  size={40}
              />
          </Link>
        <div className={styles.info_details}>
            <Link to={publicCarPath.replace(":id", String(car.carId))}>
                <span className={styles.link_trip_details}>{car.infoCar}</span>
            </Link>
            <StarRating rating={car.rating} className="light-text h6" />
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-people light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">
            {t("trip_detail.seats", { number: availableSeats })}
          </span>
        </div>
      </div>
      <hr className="white" />
      <div className={styles.show_row}>
        <Link to={publicProfilePath.replace(":id", String(driver.userId))}>
          <CircleImg src={driver.imageUri} size={40} />
        </Link>
        <div className={styles.info_details}>
            <Link to={publicProfilePath.replace(":id", String(driver.userId))} style={{ textDecoration: 'underline' }}>
              <span className={styles.link_trip_details}>
                  {t("format.name", {
                  name: driver.username,
                  surname: driver.surname,
              })}</span>
            </Link>
            <StarRating rating={0} className="light-text h6" />
        </div>
      </div>
      {isDriver && (
        <div className={styles.show_row}>
          <i className="bi bi-envelope-fill light-text"></i>
          <div className={styles.info_details}>
            <span className="light-text detail">PONER EMAIL</span>
          </div>
        </div>
      )}
      {isDriver && (
        <div className={styles.show_row}>
          <i className="bi bi-telephone-fill light-text"></i>
          <div className={styles.info_details}>
            <span className="light-text detail">PONER CELU</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default TripInfo;
