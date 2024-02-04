import TripModel from "@/models/TripModel";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip";
import {tripDetailsPath} from "@/AppRouter.tsx";

interface RecommendedTripsListProps {
  recommendedTrips: TripModel[];
}

const RecommendedTripsList = ({
  recommendedTrips,
}: RecommendedTripsListProps) => {
  const { t } = useTranslation();

  if (!recommendedTrips || recommendedTrips.length === 0) {
    return null;
  }
    const extraData = (trip: TripModel):{startDate:string, endDate:string, link: string}=>{
        const now = new Date();
        const dateString = now.toISOString().split("T")[0]
        const startDate = dateString
        const endDate = dateString
        const link = tripDetailsPath.replace(":tripId", trip.tripId.toString()) + `?startDateTime=${startDate}&endDateTime=${endDate}`;
        return {startDate:startDate, endDate:endDate, link: link}
    }
  return (
    <div className={styles.recommendedTripsListContainer}>
      <h2 className="secondary-text">{t("recommended_trips.title")}</h2>
      <div className={styles.recommendedTripsList}>
        {recommendedTrips.map((trip) => (
          <CardTrip key={trip.tripId} trip={trip} extraData={extraData}/>
        ))}
      </div>
    </div>
  );
};

export default RecommendedTripsList;
