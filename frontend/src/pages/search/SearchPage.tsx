import { FaCarSide } from "react-icons/fa";
import styles from "./styles.module.scss";
import TripsSearch from "@/components/search/TripsSearch";
import useAllCities from "@/hooks/cities/useAllCities";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import TripModel from "@/models/TripModel";
import { useState } from "react";
import { parseTripsSearchParams } from "@/functions/tripsSearchParams";
import PaginationList from "@/components/paginationList/paginationList";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip";

const SearchPage = () => {
  const { t } = useTranslation();
  const location = useLocation();
  const { isLoading: isCitiesLoading, cities } = useAllCities();
  const { isLoading: isCarFeaturesLoading, carFeatures } = useCarFeatures();
  const { search } = location;
  const initialSearch = parseTripsSearchParams(search);

  const [trips, setTrips] = useState<TripModel[]>([]);
  const onSearchSuccess = (trips: TripModel[]) => {
    setTrips(trips || []);
  };
  const onSearchError = () => {
    setTrips([]);
  };

  const NoResults = () => (
    <div className={styles.noResultsContainer}>
      <div className={styles.noResultsHeader}>
        <FaCarSide className="secondary-text" />
        <span>{t("search.no_results.title")}</span>
      </div>
      <div className={styles.noResultsBody}>
        <span>{t("search.no_results.body")}</span>
      </div>
    </div>
  );

  // TODO: Add loading screen
  if (isCitiesLoading || isCarFeaturesLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.searchContainer}>
        <TripsSearch
          onSearchError={onSearchError}
          onSearchSuccess={onSearchSuccess}
          initialSearch={initialSearch}
          cities={cities}
          carFeatures={carFeatures}
        />
      </div>
      <div className={styles.contentContainer}>
        <PaginationList
          pagination_component={<h3>Poner paginación</h3>}
          empty_component={<NoResults />}
          data={trips.map((trip) => ({ trip }))}
          component_name={CardTrip}
        />
      </div>
    </div>
  );
};

export default SearchPage;
