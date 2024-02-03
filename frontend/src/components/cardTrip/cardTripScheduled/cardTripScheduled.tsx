import styles from "./styles.module.scss";
import CardTrip from "../cardTrip/CardTrip";
import { useTranslation } from "react-i18next";
import TripModel from "@/models/TripModel.ts";
import { getDayString } from "@/utils/date/dayString.ts";
import tripModel from "@/models/TripModel.ts";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import { tripDetailsPath } from "@/AppRouter";

interface CardTripScheduledProps {
  data: TripModel;
  useExtraData: (trip: tripModel) => {
    startDate: string;
    endDate: string;
    link: string;
  } | null;
}

const CardTripScheduled = ({
  data: trip,
  useExtraData,
}: CardTripScheduledProps) => {
  const { t } = useTranslation();
  const extraData = useExtraData(trip);
  console.log("extraData", extraData);
  const { startDate: start, endDate: end } = extraData || {
    startDate: trip.startDateTime,
    endDate: trip.endDateTime,
  };

  const getExtraData = () => {
    return (
      extraData || {
        startDate: trip.startDateTime,
        endDate: trip.endDateTime,
        link: tripDetailsPath.replace(":tripId", trip.tripId.toString()),
      }
    );
  };

  return (
    <div>
      <div className={styles.short_date}>
        <div className={styles.calendar_container}>
          <i className="bi bi-calendar text h1"></i>
          <div className={styles.text_calendar}>
            {start && (
              <h3 className={styles.day_week_style}>
                {t(`day.full.${getDayString(new Date(start)).toLowerCase()}`, {
                  plural: "s",
                })}
              </h3>
            )}
            {start != end ? (
              <span className={styles.date_text}>
                {t("format.recurrent_date", {
                  initial_date: getFormattedDateTime(start).date,
                  final_date: getFormattedDateTime(end).date,
                })}
              </span>
            ) : (
              <span className={styles.date_text}>
                {getFormattedDateTime(start).date}
              </span>
            )}
          </div>
        </div>
      </div>
      <div className={styles.card_trip_container}>
        <CardTrip extraData={getExtraData} trip={trip} className="w-100" />
      </div>
    </div>
  );
};

export default CardTripScheduled;
