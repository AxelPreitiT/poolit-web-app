import { FaCarSide } from "react-icons/fa";
import styles from "./styles.module.scss";
import TripsSearch from "@/components/search/TripsSearch/TripsSearch";
import useAllCities from "@/hooks/cities/useAllCities";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import TripModel from "@/models/TripModel";
import { useState } from "react";
import {
  createTripsSearchParams,
  parseTripsSearchParams,
} from "@/functions/tripsSearchParams";
import PaginationList from "@/components/paginationList/paginationList";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip";
import useSearchTripsForm from "@/hooks/forms/useSearchTripsForm";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import useDiscovery from "@/hooks/discovery/useDiscovery";
import TripsService from "@/services/TripsService";
import { searchPath } from "@/AppRouter";
import TripsSorter from "@/components/search/TripsSorter/TripsSorter";
import useTripSortTypes from "@/hooks/trips/useTripSortTypes";
import { isObjectEmpty } from "@/utils/object/isEmpty";
import LoadingWheel from "@/components/loading/LoadingWheel";
import LoadingScreen from "@/components/loading/LoadingScreen";

const SearchPage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { search } = useLocation();
  const { getDiscoveryOnMount } = useDiscovery();
  const { isLoading: isCitiesLoading, cities } = useAllCities();
  const { isLoading: isCarFeaturesLoading, carFeatures } = useCarFeatures();
  const { isLoading: isTripSortTypesLoading, tripSortTypes } =
    useTripSortTypes();
  const initialSearch = parseTripsSearchParams(search);
  const [trips, setTrips] = useState<TripModel[]>([]);
  const [currentSortTypeId, setCurrentSortTypeId] = useState(
    initialSearch.sortTypeId
  );
  const [currentDescending, setCurrentDescending] = useState(
    initialSearch.descending
  );
  const [isFetching, setIsFetching] = useState(isObjectEmpty(initialSearch));

  const onSearchSubmit = async (data: SearchTripsFormSchemaType) => {
    setIsFetching(true);
    try {
      const discovery = await getDiscoveryOnMount();
      return await TripsService.searchTrips(discovery.tripsUriTemplate, data, {
        sortTypeId: currentSortTypeId,
        descending: currentDescending,
      });
    } finally {
      setIsFetching(false);
    }
  };
  const onSearchSuccess = ({
    trips,
    data,
  }: {
    trips: TripModel[];
    data: SearchTripsFormSchemaType;
  }) => {
    const searchParams = createTripsSearchParams(data, {
      sortTypeId: currentSortTypeId,
      descending: currentDescending,
    });
    navigate(`${searchPath}?${searchParams}`);
    setTrips(trips || []);
  };
  const onSearchError = () => {
    setTrips([]);
  };

  const searchForm = useSearchTripsForm({
    onSubmit: onSearchSubmit,
    onSuccess: onSearchSuccess,
    onError: onSearchError,
    initialSearch,
  });
  const { executeSubmit, trigger } = searchForm;

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

  if (isCitiesLoading || isCarFeaturesLoading || isTripSortTypesLoading) {
    return <LoadingScreen />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.searchContainer}>
        <TripsSearch
          searchForm={searchForm}
          cities={cities}
          carFeatures={carFeatures}
          showSpinnerOnSubmit={false}
        />
      </div>
      <div className={styles.contentContainer}>
        {trips && trips.length > 0 && (
          <TripsSorter
            className={styles.sorter}
            sortTypes={tripSortTypes}
            currentSortTypeId={initialSearch.sortTypeId}
            currentDescending={initialSearch.descending}
            onSelect={(sortTypeId, descending) => {
              trigger().then((isValid) => {
                if (isValid) {
                  setCurrentSortTypeId(sortTypeId);
                  setCurrentDescending(descending);
                }
              });
              executeSubmit();
            }}
          />
        )}
        {isFetching ? (
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingIcon}
            descriptionClassName={styles.loadingDescription}
            description={t("search.loading")}
          />
        ) : (
          <PaginationList
            pagination_component={<h3>Poner paginación</h3>}
            empty_component={<NoResults />}
            data={trips.map((trip) => ({ trip }))}
            Item={CardTrip}
            listClassName={styles.tripsList}
            itemClassName={styles.tripItem}
          />
        )}
      </div>
    </div>
  );
};

export default SearchPage;
