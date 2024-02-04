import styles from "./styles.module.scss";
import MainHeader from "@/components/utils/MainHeader";
import MainComponent from "@/components/utils/MainComponent";
import TabComponent from "@/components/tab/TabComponent";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import EmptyList from "@/components/emptyList/EmptyList";
import ListTripsScheduled from "@/components/cardTrip/ListTripsScheduled/ListTripsScheduled";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import LoadingWheel from "@/components/loading/LoadingWheel";
import TripModel from "@/models/TripModel.ts";
import useRolePassanger from "@/hooks/passanger/useRolePassanger.tsx";
import { tripDetailsPath } from "@/AppRouter.tsx";

const ReservedPage = () => {
  const { isLoading, currentUser } = useCurrentUser();
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { search, pathname } = useLocation();
  const time = new URLSearchParams(search).get("time");
  const page = new URLSearchParams(search).get("page");
  // TODO: PONER CONSTANTES DE PAGINACION
  const currentPage = page === null ? 1 : parseInt(page, 10);

  // TODO: REVISAR NO LO CAMBIO AHORA POR LSA DUDAS
  const uriFutureTrips =
    isLoading || currentUser === undefined
      ? null
      : createPaginationUri(
          currentUser?.futureReservedTripsUri,
          currentPage,
          2
        );
  const uriPastTrips =
    isLoading || currentUser === undefined
      ? null
      : createPaginationUri(currentUser?.pastReservedTripsUri, currentPage, 2);

  const useExtraData = (
    trip: TripModel
  ): { startDate: string; endDate: string; link: string } => {
    const { isLoading: isLoadingRole, currentPassanger: currentPassanger } =
      useRolePassanger(false, trip?.passengersUriTemplate);

    if (isLoadingRole || currentPassanger === undefined) {
      return { startDate: "", endDate: "", link: "" };
    }
    return {
      startDate: currentPassanger.startDateTime,
      endDate: currentPassanger.endDateTime,
      link: tripDetailsPath.replace(":tripId", trip.tripId.toString()),
    };
  };

  return (
    <MainComponent>
      <MainHeader title={t("reserved_trips.title")} />
      <div className={styles.container_tab}>
        <TabComponent
          left_title={t("reserved_trips.future")}
          left_component={
            uriFutureTrips === null ? (
              <LoadingWheel
                containerClassName={styles.loadingContainer}
                iconClassName={styles.loadingIcon}
                descriptionClassName={styles.loadingDescription}
                description={t("trip.loading")}
              />
            ) : (
              <ListTripsScheduled
                uri={uriFutureTrips}
                useExtraData={useExtraData}
                current_page={currentPage}
                empty_component={
                  <EmptyList
                    text={t("reserved_trips.empty")}
                    second_text={t("reserved_trips.more")}
                    icon={"car-front-fill"}
                  />
                }
              />
            )
          }
          right_title={t("reserved_trips.past")}
          right_component={
            uriPastTrips === null ? (
              <LoadingWheel
                containerClassName={styles.loadingContainer}
                iconClassName={styles.loadingIcon}
                descriptionClassName={styles.loadingDescription}
                description={t("trip.loading")}
              />
            ) : (
              <ListTripsScheduled
                uri={uriPastTrips}
                useExtraData={useExtraData}
                current_page={currentPage}
                empty_component={
                  <EmptyList
                    text={t("reserved_trips.empty")}
                    second_text={t("reserved_trips.more")}
                    icon={"car-front-fill"}
                  />
                }
              />
            )
          }
          active={time === "past" ? "right" : "left"}
          onLeftClick={() => navigate(`${pathname}?time=future`)}
          onRightClick={() => navigate(`${pathname}?time=past`)}
        />
      </div>
    </MainComponent>
  );
};

export default ReservedPage;
