import styles from "./styles.module.scss";
import CardTrip from "../cardTrip/CardTrip";
import { useTranslation } from "react-i18next";
import TripModel from "@/models/TripModel.ts";
import {getDayString} from "@/utils/date/dayString.ts";
import tripModel from "@/models/TripModel.ts";
import {tripDetailsPath} from "@/AppRouter.tsx";

interface CardTripScheduledProps {
    data: TripModel;
    extraData?:(trip: tripModel)=>{startDate: string, endDate: string, link: string};
}

const CardTripScheduled =  ({data: trip, extraData: extraData}: CardTripScheduledProps) => {
  const { t } = useTranslation();
  const { startDate: start, endDate: end} = extraData ? extraData(trip) : { startDate: '', endDate: ''};
  const date = new Date(start);
  if(extraData == undefined){
    extraData = (trip: TripModel)=>{
      return {startDate:trip.startDateTime, endDate:trip.endDateTime, link:tripDetailsPath.replace(":tripId", trip.tripId.toString())}
    }
  }

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
              {start != end ? (
              <span className={styles.date_text}>
                {t("format.recurrent_date", {
                  initial_date: start,
                  final_date: end,
                })}
              </span>
            ) : (
              <span className={styles.date_text}>
                {start}
              </span>
            )}
          </div>
        </div>
      </div>
      <div className={styles.card_trip_container}>
        <CardTrip extraData={extraData} trip={trip}/>
      </div>
    </div>
  );
};

export default CardTripScheduled;
