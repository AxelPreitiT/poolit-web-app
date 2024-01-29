import styles from "./styles.module.scss";
import CardTrip from "../cardTrip/CardTrip";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import { useTranslation } from "react-i18next";
import TripModel from "@/models/TripModel.ts";
import {getDayString} from "@/utils/date/dayString.ts";

interface CardTripScheduledProps {
    data: TripModel;
    extraData?: string;
}

const CardTripScheduledStatus = ({data: trip , extraData: status} : CardTripScheduledProps) => {
    const { t } = useTranslation();
    const date = new Date(trip.startDateTime);

    return (
        <div>
            <div className={styles.short_date}>
                <div className={styles.calendar_container}>
                    <i className="bi bi-calendar text h1"></i>
                    <div className={styles.text_calendar}>
                        <h3 className={styles.day_week_style}>
                            {t(`day.full.${getDayString(date).toLowerCase()}`, {
                                plural: "s",})}
                        </h3>
                        <h1>{status}</h1>
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

export default CardTripScheduledStatus;
