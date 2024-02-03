import styles from "./styles.module.scss";
import MainHeader from "@/components/utils/MainHeader";
import MainComponent from "@/components/utils/MainComponent";
import TabComponent from "@/components/tab/TabComponent";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import EmptyList from "@/components/emptyList/EmptyList";
import ListTripsScheduled from "@/components/cardTrip/ListTripsScheduled/ListTripsScheduled";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { INITIALPAGE, TRIPSPAGESIZE } from "@/enums/PaginationConstants.ts";
import TripModel from "@/models/TripModel.ts";
import { tripDetailsPath } from "@/AppRouter.tsx";

const CreatedPage = () => {
  const { isLoading, currentUser } = useCurrentUser();
  const { t } = useTranslation();
  const { search } = useLocation();
  const time = new URLSearchParams(search).get("time");

  const page = new URLSearchParams(search).get("page");
  const currentPage = page === null ? INITIALPAGE : parseInt(page, 10);
  //extradata={(trip) => {trip.strar, trip.end}} //driver
  //extradata={(trip) => {useCurrentPassanger(tripUri); pasanger}} //passenger
  //extradata={(trip) => {useLocale();..; return{queryStart, queryEnd}} //buscador

  const useExtraData = (
    trip: TripModel
  ): { startDate: string; endDate: string; link: string } => {
    return {
      startDate: trip.startDateTime,
      endDate: trip.endDateTime,
      link: tripDetailsPath.replace(":tripId", trip.tripId.toString()),
    };
  };

  return (
    <MainComponent>
      <MainHeader title={t("created_trips.title")} />
      <div className={styles.container_tab}>
        <TabComponent
          left_title={t("created_trips.future")}
          left_component={
            isLoading || currentUser === undefined ? (
              <LoadingWheel
                containerClassName={styles.loadingContainer}
                iconClassName={styles.loadingIcon}
                descriptionClassName={styles.loadingDescription}
                description={t("trip.loading")}
              />
            ) : (
              <ListTripsScheduled
                uri={createPaginationUri(
                  currentUser.futureCreatedTripsUri,
                  currentPage,
                  TRIPSPAGESIZE
                )}
                current_page={currentPage}
                useExtraData={useExtraData}
                empty_component={
                  <EmptyList
                    text={t("created_trips.empty")}
                    second_text={t("created_trips.more")}
                    icon={"car-front-fill"}
                  />
                }
              />
            )
          }
          right_title={t("created_trips.past")}
          right_component={
            isLoading || currentUser === undefined ? (
              <LoadingWheel
                containerClassName={styles.loadingContainer}
                iconClassName={styles.loadingIcon}
                descriptionClassName={styles.loadingDescription}
                description={t("trip.loading")}
              />
            ) : (
              <ListTripsScheduled
                uri={createPaginationUri(
                  currentUser?.pastCreatedTripsUri,
                  currentPage,
                  TRIPSPAGESIZE
                )}
                current_page={currentPage}
                useExtraData={useExtraData}
                empty_component={
                  <EmptyList
                    text={t("created_trips.empty")}
                    second_text={t("created_trips.more")}
                    icon={"car-front-fill"}
                  />
                }
              />
            )
          }
          active={time === "past" ? "left" : "right"}
        />
      </div>
    </MainComponent>
  );
};

export default CreatedPage;
