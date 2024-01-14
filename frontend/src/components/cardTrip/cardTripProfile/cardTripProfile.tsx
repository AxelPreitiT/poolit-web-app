import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";

const CardTripProfile = (Trip: TripModel) => {
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
            <h3>{Trip.originCityUri}</h3>
            <span className="text">{Trip.originAddress}</span>
          </div>
          <div className={styles.route_info_text}>
            <h3>{Trip.destinationCityUri}</h3>
            <span style={{ textAlign: "right" }}>
              {Trip.destinationAddress}
            </span>
          </div>
        </div>
        <div className={styles.extra_info_container}>
          <div className={styles.calendar_container}>
            <i className="bi bi-calendar text"></i>
            {Trip.totalTrips==1 ? (
              <div className={styles.format_date}>
                <span className="text">"PONER DIA DE LA SEMANA"</span>
                <span className={styles.date_text}>
                  {`${Trip.startDateTime}, ${Trip.endDateTime}`}
                </span>
              </div>
            ) : (
              <div className={styles.format_date}>
                <span className="text">"PONER DIA DE LA SEMANA"</span>
                <span className={styles.date_text}>{Trip.startDateTime}</span>
              </div>
            )}
          </div>
          <div>
            <i className="bi bi-clock"></i>
            <span>{Trip.startDateTime}</span>
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
        <img className={styles.car_container} src={ProfilePhoto} />
      </div>
    </div>
  );
};

export default CardTripProfile;
