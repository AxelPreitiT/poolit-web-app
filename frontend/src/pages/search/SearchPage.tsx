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
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip";
import useSearchTripsForm from "@/hooks/forms/useSearchTripsForm";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import useDiscovery from "@/hooks/discovery/useDiscovery";
import TripsService from "@/services/TripsService";
import { searchPath, tripDetailsPath } from "@/AppRouter";
import TripsSorter from "@/components/search/TripsSorter/TripsSorter";
import useTripSortTypes from "@/hooks/trips/useTripSortTypes";
import { isObjectEmpty } from "@/utils/object/isEmpty";
import LoadingWheel from "@/components/loading/LoadingWheel";
import LoadingScreen from "@/components/loading/LoadingScreen";
import PaginationModel from "@/models/PaginationModel";
import PageSelector from "./PageSelector";
import NoResultsSearch from "./NoResultsSearch";
import { getIsoDate } from "@/utils/date/isoDate";

const SearchPage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { search } = useLocation();
  const { getDiscoveryOnMount } = useDiscovery();
  const { isLoading: isCitiesLoading, cities } = useAllCities();
  const { isLoading: isCarFeaturesLoading, carFeatures } = useCarFeatures();
  const { isLoading: isTripSortTypesLoading, tripSortTypes } =
    useTripSortTypes();
  const [paginatedTrips, setPaginatedTrips] = useState<
    PaginationModel<TripModel> | undefined
  >(undefined);
  const trips = paginatedTrips?.data || [];
  const initialSearch = parseTripsSearchParams(search);
  const [currentSortTypeId, setCurrentSortTypeId] = useState(
    initialSearch.sortTypeId
  );
  const [currentDescending, setCurrentDescending] = useState(
    initialSearch.descending
  );
  const [currentPage, setCurrentPage] = useState(initialSearch.page);
  const [currentPageSize] = useState(initialSearch.pageSize);
  const [isFetching, setIsFetching] = useState(isObjectEmpty(initialSearch));

  const onSearchSubmit = async (data: SearchTripsFormSchemaType) => {
    setIsFetching(true);
    try {
      const discovery = await getDiscoveryOnMount();
      return await TripsService.searchTrips(discovery.tripsUriTemplate, data, {
        pageOptions: {
          page: currentPage,
          pageSize: currentPageSize,
        },
        sortOptions: {
          sortTypeId: currentSortTypeId,
          descending: currentDescending,
        },
      });
    } finally {
      setIsFetching(false);
    }
  };
  const onSearchSuccess = ({
    result: paginatedTrips,
    data,
  }: {
    result: PaginationModel<TripModel>;
    data: SearchTripsFormSchemaType;
  }) => {
    const searchParams = createTripsSearchParams(data, {
      pageOptions: {
        page: currentPage,
        pageSize: currentPageSize,
      },
      sortOptions: {
        sortTypeId: currentSortTypeId,
        descending: currentDescending,
      },
    });
    navigate(`${searchPath}?${searchParams}`, { replace: true });
    setPaginatedTrips(paginatedTrips);
  };
  const onSearchError = () => {
    setPaginatedTrips(undefined);
  };

  const searchForm = useSearchTripsForm({
    onSubmit: onSearchSubmit,
    onSuccess: onSearchSuccess,
    onError: onSearchError,
    initialSearch,
    submitOnMount: true,
  });
  const { executeSubmit } = searchForm;

  if (isCitiesLoading || isCarFeaturesLoading || isTripSortTypesLoading) {
    return <LoadingScreen />;
  }

  const extraData = (
    trip: TripModel
  ): { startDate: string; endDate: string; link: string } => {
    const startDate = initialSearch.date ? getIsoDate(initialSearch.date) : "";
    const endDate = initialSearch.last_date
      ? getIsoDate(initialSearch.last_date)
      : initialSearch.date
      ? getIsoDate(initialSearch.date)
      : "";
    const link =
      tripDetailsPath.replace(":tripId", trip.tripId.toString()) +
      `?startDateTime=${startDate}&endDateTime=${endDate}`;
    return { startDate: startDate, endDate: endDate, link: link };
  };

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
            onSelect={(sortTypeId, descending) =>
              executeSubmit(() => {
                setCurrentSortTypeId(sortTypeId);
                setCurrentDescending(descending);
              })
            }
          />
        )}
        {isFetching ? (
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingIcon}
            descriptionClassName={styles.loadingDescription}
            description={t("search.loading")}
          />
        ) : paginatedTrips && trips && trips.length > 0 ? (
          <div className={styles.resultsContainer}>
            <div className={styles.tripsList}>
              {trips.map((trip) => (
                <CardTrip
                  key={trip.tripId}
                  trip={trip}
                  className={styles.tripItem}
                  extraData={extraData}
                />
              ))}
            </div>
            <PageSelector
              currentPage={currentPage}
              pageSize={currentPageSize}
              setCurrentPage={(page) =>
                executeSubmit(() => setCurrentPage(page))
              }
              paginatedTrips={paginatedTrips}
            />
          </div>
        ) : (
          <NoResultsSearch />
        )}
      </div>
    </div>
  );
};

export default SearchPage;
