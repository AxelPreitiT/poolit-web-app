import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg";
import StarRating from "@/components/stars/StarsRanking";
import TripModel from "@/models/TripModel.ts";
import CarModel from "@/models/CarModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import { Link } from "react-router-dom";
import { carPath, publicProfilePath } from "@/AppRouter.tsx";
import { getDayString } from "@/utils/date/dayString.ts";
import userPublicModel from "@/models/UserPublicModel.ts";
import useDriverByUri from "@/hooks/driver/useDriver.tsx";
import passangerModel from "@/models/PassangerModel.ts";
import passangerStatus from "@/enums/PassangerStatus.ts";
import LoadingScreen from "@/components/loading/LoadingScreen.tsx";
import useOccupiedSeats from "@/hooks/trips/useOccupiedSeats.tsx";

interface TripInfoProps {
  trip: TripModel;
  car: CarModel;
  driver: UserPublicModel;
  startDateTime: string;
  endDateTime: string;
  isDriver: boolean;
  currentPassanger: passangerModel | undefined;
}

const DriverDataComponent = ({ driver }: { driver: userPublicModel }) => {
  const { isLoading, data: driverData } = useDriverByUri(driver.selfUri);

  return (
    !isLoading &&
    driverData && (
      <div className={styles.driver_info}>
        <div className={styles.show_row}>
          <i className="bi bi-envelope-fill light-text"></i>
          <div className={styles.info_details}>
            <span className="light-text detail">{driverData.email}</span>
          </div>
        </div>
        <div className={styles.show_row}>
          <i className="bi bi-telephone-fill light-text"></i>
          <div className={styles.info_details}>
            <span className="light-text detail">{driverData.phone}</span>
          </div>
        </div>
      </div>
    )
  );
};

const TripInfo = ({
  trip,
  car,
  driver,
  isDriver,
  startDateTime,
  endDateTime,
  currentPassanger,
}: TripInfoProps) => {
  const { t } = useTranslation();
  const date = new Date(trip.startDateTime);
  const { isLoading: isLoadingSeats, data: occupiedSeats } = useOccupiedSeats(
    startDateTime,
    endDateTime,
    trip.passengersUriTemplate
  );

  if (isLoadingSeats || occupiedSeats == undefined) {
    return <LoadingScreen description={t("trip.loading_one")} />;
  }

  const availableSeats =
    parseInt(trip.maxSeats, 10) - occupiedSeats.occupiedSeats;

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
              plural: "s",
            })}
          </span>
          <span className={styles.subtitle_info}>
            {startDateTime === endDateTime
              ? getFormattedDateTime(startDateTime).date
              : t("format.recurrent_date", {
                  initial_date: getFormattedDateTime(startDateTime).date,
                  final_date: getFormattedDateTime(endDateTime).date,
                })}
          </span>
        </div>
      </div>
      <div className={styles.show_row}>
        <Link to={carPath.replace(":carId", String(car.carId))}>
          <CircleImg src={car.imageUri} size={40} />
        </Link>
        <div className={styles.info_details}>
          <Link to={carPath.replace(":carId", String(car.carId))}>
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
          <Link
            to={publicProfilePath.replace(":id", String(driver.userId))}
            style={{ textDecoration: "underline" }}
          >
            <span className={styles.link_trip_details}>
              {t("format.name", {
                name: driver.username,
                surname: driver.surname,
              })}
            </span>
          </Link>
          <StarRating rating={driver.driverRating} className="light-text h6" />
        </div>
      </div>
      {(isDriver ||
        currentPassanger?.passengerState === passangerStatus.ACCEPTED) && (
        <DriverDataComponent driver={driver} />
      )}
    </div>
  );
};

export default TripInfo;
