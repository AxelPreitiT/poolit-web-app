import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled";
import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";
import { useTranslation } from "react-i18next";

export interface ListTripsScheduledProps {
  uri: string;
  empty_component: React.ReactNode;
  current_page: number;
}

const ListTripsScheduled = ({
  uri,
  empty_component,
  current_page,
}: ListTripsScheduledProps) => {
  const { t } = useTranslation();

  return (
    <div>
      <PaginationComponent
        uri={uri}
        current_page={current_page}
        useFuction={useTripsByUri}
        empty_component={empty_component}
        component_name={CardTripScheduled}
        itemsName={t("trip.title")}
      />
    </div>
  );
};

export default ListTripsScheduled;
