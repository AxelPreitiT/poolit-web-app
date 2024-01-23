import PaginationList from "@/components/paginationList/paginationList";
import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled";
import SpinnerComponent from "@/components/Spinner/Spinner";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";

export interface ListTripsScheduledProps {
  uri: string;
  empty_component: React.ReactNode;
}

const ListTripsScheduled = ({
  uri,
  empty_component,
}: ListTripsScheduledProps) => {

  const { isLoading: isLoadingTrips, trips } = useTripsByUri(uri);

  return (
    <div>
      {trips == undefined || isLoadingTrips ? (
        <SpinnerComponent />
      ) : (
        <PaginationList
          pagination_component={<h3>Poner paginación</h3>}
          empty_component={empty_component}
          data={trips.trips}
          component_name={CardTripScheduled}
        />
      )}
    </div>
  );
};

export default ListTripsScheduled;
