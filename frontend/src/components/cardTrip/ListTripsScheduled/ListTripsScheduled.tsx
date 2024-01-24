import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";

export interface ListTripsScheduledProps {
  uri: string;
  empty_component: React.ReactNode;
}

const ListTripsScheduled = ({
  uri,
  empty_component,
}: ListTripsScheduledProps) => {


  return (
    <div>
      <PaginationComponent
        uri = {uri}
        useFuction={useTripsByUri}
        empty_component={empty_component}
        component_name={CardTripScheduled}
      />
    </div>
  );
};

export default ListTripsScheduled;
