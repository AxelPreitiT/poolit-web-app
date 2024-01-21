import TripModel from "@/models/TripModel";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip";

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

  return (
    <div className={styles.recommendedTripsListContainer}>
      <h2 className="secondary-text">{t("recommended_trips.title")}</h2>
      <div className={styles.recommendedTripsList}>
        {recommendedTrips.map((trip) => (
          <CardTrip key={trip.tripId} trip={trip} />
        ))}
      </div>
    </div>
  );
};

export default RecommendedTripsList;
