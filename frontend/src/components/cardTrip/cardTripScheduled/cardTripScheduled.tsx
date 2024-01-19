import styles from "./styles.module.scss";
import CardTrip from "../cardTrip/CardTrip";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import { useTranslation } from "react-i18next";
import TripModel from "@/models/TripModel.ts";

const CardTripScheduled = (trip: TripModel) => {
  const { t } = useTranslation();

  return (
    <div>
      <div className={styles.short_date}>
        <div className={styles.calendar_container}>
          <i className="bi bi-calendar text h1"></i>
          <div className={styles.text_calendar}>
            <h3 className={styles.day_week_style}>PONER DIA</h3>
            {trip.totalTrips > 1 ? (
              <span className={styles.date_text}>
                {t("format.recurrent_date", {
                  initial_date: getFormattedDateTime(trip.startDateTime).date,
                  final_date: getFormattedDateTime(trip.endDateTime).date,
                })}
              </span>
            ) : (
              <span className={styles.date_text}>
                {getFormattedDateTime(trip.startDateTime).date}
              </span>
            )}
          </div>
        </div>
      </div>
      <div className={styles.card_trip_container}>
        <CardTrip trip={trip} />
      </div>
    </div>
  );
};

export default CardTripScheduled;
