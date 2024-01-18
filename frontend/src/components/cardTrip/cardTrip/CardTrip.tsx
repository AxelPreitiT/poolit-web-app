import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { tripDetailsPath } from "@/AppRouter";
import { useEffect, useState } from "react";
import CityService from "@/services/CityService.ts";
import CarService from "@/services/CarService.ts";
import CarModel from "@/models/CarModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import getFormattedDateTime from "@/functions/DateFormat.ts";

const CardTrip = ({ trip }: { trip: TripModel }) => {
  const { t } = useTranslation();

  const [cityOrigin, setCityOrigin] = useState<string | null>(null);
  const [cityDestination, setCityDestination] = useState<string | null>(null);
  const [CarTrip, setCarTrip] = useState<CarModel | null>(null);

  useEffect(() => {
    CityService.getCityById(trip.originCityUri).then((response) => {
      setCityOrigin(response.name);
    });
    CityService.getCityById(trip.destinationCityUri).then((response) => {
      setCityDestination(response.name);
    });
  });

  useEffect(() => {
    CarService.getCarById(trip.carUri).then((response) => {
      setCarTrip(response);
    });
  });

  return (
    <Link to={tripDetailsPath} className={styles.link_container}>
      <div className={styles.card_container}>
        <div className={styles.left_container}>
          {CarTrip === null ? (
            <SpinnerComponent />
          ) : (
            <img src={CarTrip.imageUri} className={styles.img_container} />
          )}
        </div>
        <div className={styles.right_container}>
          <div className={styles.address_container}>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>{cityOrigin}</h4>
                <h6>{trip.originAddress}</h6>
              </div>
            </div>
            <div className={styles.vertical_dotted_line}></div>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt-fill secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>{cityDestination}</h4>
                <h6>{trip.destinationAddress}</h6>
              </div>
            </div>
          </div>
          <div className={styles.footer_description}>
            <div className={styles.footer_details}>
              <i className="bi bi-calendar text"></i>
              {trip.totalTrips > 1 ? (
                <span>PONER DIA</span>
              ) : (
                <span>{getFormattedDateTime(trip.startDateTime).date}</span>
              )}
            </div>
            <div className={styles.footer_details}>
              <i className="bi bi-clock text"></i>
              <span>{getFormattedDateTime(trip.startDateTime).time}</span>
            </div>
            <h3>
              {t("format.price", {
                priceInt: trip.pricePerTrip,
                princeFloat: 0,
              })}
            </h3>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default CardTrip;
