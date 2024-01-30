import useTripsByUri from "@/hooks/trips/useTripsByUri.tsx";
import { useTranslation } from "react-i18next";
import PaginationComponentExtraData from "@/components/pagination/PaginationComponent/PaginationComponentExtraData.tsx";
import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled.tsx";

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
      <PaginationComponentExtraData
          CardComponent={CardTripScheduled}
          uri={uri}
          current_page={current_page}
          useFuction={useTripsByUri}
          empty_component={empty_component}
          itemsName={t("trip.title")}
      />
    </div>
  );
};

export default ListTripsScheduled;
