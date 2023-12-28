import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";

export type Trip = {
  tripId: number;
  originCity: {
    name: string;
  };
  originAddress: string;
  destinationCity: {
    name: string;
  };
  destinationAddress: string;
  dayOfWeekString: string;
  startDateString: string;
  endDateString: string;
  travelInfoDateString: string;
  startTimeString: string;
  integerQueryTotalPrice: string;
  decimalQueryTotalPrice: string;
  queryIsRecurrent: boolean;
  car: {
    imageId: string;
  };
};

const CardTrip = (Trip: Trip) => {
  const { t } = useTranslation();

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
            <h3>{Trip.originCity.name}</h3>
            <span className="text">{Trip.originAddress}</span>
          </div>
          <div className={styles.route_info_text}>
            <h3>{Trip.destinationCity.name}</h3>
            <span style={{ textAlign: "right" }}>
              {Trip.destinationAddress}
            </span>
          </div>
        </div>
        <div className={styles.extra_info_container}>
          <div className={styles.calendar_container}>
            <i className="bi bi-calendar text"></i>
            {Trip.queryIsRecurrent ? (
              <div className={styles.format_date}>
                <span className="text">{Trip.dayOfWeekString}</span>
                <span className={styles.date_text}>
                  {`${Trip.startDateString}, ${Trip.endDateString}`}
                </span>
              </div>
            ) : (
              <div className={styles.format_date}>
                <span className="text">{Trip.dayOfWeekString}</span>
                <span className={styles.date_text}>{Trip.startDateString}</span>
              </div>
            )}
          </div>
          <div>
            <i className="bi bi-clock"></i>
            <span>{Trip.startTimeString}</span>
          </div>
          <div>
            <h2 className={styles.price_format}>
              {t("format.price", {
                priceInt: Trip.integerQueryTotalPrice,
                princeFloat: Trip.decimalQueryTotalPrice,
              })}
            </h2>
          </div>
        </div>
      </div>
      <div className={styles.img_container}>
        <img
          className={styles.car_container}
          src={ProfilePhoto}
          alt="Car Image"
        />
      </div>
    </div>
  );
};

export default CardTrip;
