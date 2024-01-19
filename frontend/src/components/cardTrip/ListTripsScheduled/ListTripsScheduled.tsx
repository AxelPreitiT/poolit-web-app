import PaginationList from "@/components/paginationList/paginationList";
import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";
import SpinnerComponent from "@/components/Spinner/Spinner";
import TripModel from "@/models/TripModel.ts";

export interface ListTripsScheduledProps {
  uri: string;
  empty_component: React.ReactNode;
}

const ListTripsScheduled = ({
  uri,
  empty_component,
}: ListTripsScheduledProps) => {
  const [Trips, setTrips] = useState<TripModel[] | null>(null);

  useEffect(() => {
    tripsService.getTripsByUri(uri).then((response) => {
      setTrips(response);
    });
  });

  return (
    <div>
      {Trips == null ? (
        <SpinnerComponent />
      ) : (
        <PaginationList
          pagination_component={<h3>Poner paginación</h3>}
          empty_component={empty_component}
          data={Trips}
          component_name={CardTripScheduled}
        />
      )}
    </div>
  );
};

export default ListTripsScheduled;
