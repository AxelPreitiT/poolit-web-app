import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import { Trip } from "@/types/Trip";

const CardTripScheduled = (Trip: Trip) => {
  const { t } = useTranslation();

  return (
    <div>
      <div className={styles.short_date}>
        <div className={styles.calendar_container}>
          <i className="bi bi-calendar text h1"></i>
          <div className={styles.text_calendar}>
            <h3 className={styles.day_week_style}>{Trip.dayOfWeekString}</h3>
            {Trip.queryIsRecurrent ? (
              <span className={styles.date_text}>
                {`${Trip.startDateString}, ${Trip.endDateString}`}
              </span>
            ) : (
              <span className={styles.date_text}>{Trip.startDateString}</span>
            )}
          </div>
        </div>
      </div>
      <div className={styles.card_trip_container}>
        <h4>Componente de tarjeta</h4>
      </div>
    </div>
  );
};

export default CardTripScheduled;
