import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import CityService from "@/services/CityService.ts";
import CarService from "@/services/CarService.ts";
import CarModel from "@/models/CarModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import TripModel from "@/models/TripModel.ts";

const CardTripProfile = (Trip: TripModel) => {
  const { t } = useTranslation();

  const [cityOrigin, setCityOrigin] = useState<string | null>(null);
  const [cityDestination, setCityDestination] = useState<string | null>(null);
  const [CarTrip, setCarTrip] = useState<CarModel | null>(null);

  useEffect(() => {
    CityService.getCityById(Trip.originCityUri).then((response) => {
      setCityOrigin(response.name);
    });
    CityService.getCityById(Trip.destinationCityUri).then((response) => {
      setCityDestination(response.name);
    });
  });

  useEffect(() => {
    CarService.getCarById(Trip.carUri).then((response) => {
      setCarTrip(response);
    });
  });

  return (
    <div className={styles.card_info}>
      <div className={styles.data_container}>
        <div className={styles.route_container}>
          <i className="bi bi-geo-alt"></i>
          <div className={styles.horizontal_dotted_line}></div>
          <i className="bi bi-geo-alt-fill"></i>
        </div>
        <div className={styles.address_container}>
          <div className={styles.route_info_text}>
            <h3>{cityOrigin}</h3>
            <span className="text">{Trip.originAddress}</span>
          </div>
          <div className={styles.route_info_text}>
            <h3>{cityDestination}</h3>
            <span style={{ textAlign: "right" }}>
              {Trip.destinationAddress}
            </span>
          </div>
        </div>
        <div className={styles.extra_info_container}>
          <div className={styles.calendar_container}>
            <i className="bi bi-calendar text"></i>
            {Trip.totalTrips == 1 ? (
              <div className={styles.format_date}>
                <span className="text">PONER DIA</span>
                <span className={styles.date_text}>
                  {getFormattedDateTime(Trip.startDateTime).date}
                </span>
              </div>
            ) : (
              <div className={styles.format_date}>
                <span className="text">PONER DIA</span>
                <span className={styles.date_text}>
                  {t("format.recurrent_date", {
                    initial_date: getFormattedDateTime(Trip.startDateTime).date,
                    final_date: getFormattedDateTime(Trip.endDateTime).date,
                  })}
                </span>
              </div>
            )}
          </div>
          <div className={styles.calendar_container}>
            <i className="bi bi-clock"></i>
            <span>{getFormattedDateTime(Trip.startDateTime).time}</span>
          </div>
          <div>
            <h2 className={styles.price_format}>
              {t("format.price", {
                priceInt: Trip.pricePerTrip,
                princeFloat: 0,
              })}
            </h2>
          </div>
        </div>
      </div>
      <div className={styles.img_container}>
        {CarTrip === null ? (
          <SpinnerComponent />
        ) : (
          <img className={styles.car_container} src={CarTrip.imageUri} />
        )}
      </div>
    </div>
  );
};

export default CardTripProfile;
