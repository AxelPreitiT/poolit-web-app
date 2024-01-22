import styles from "./styles.module.scss";
import MainHeader from "@/components/utils/MainHeader";
import MainComponent from "@/components/utils/MainComponent";
import TabComponent from "@/components/tab/TabComponent";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import EmptyList from "@/components/emptyList/EmptyList";
import ListTripsScheduled from "@/components/cardTrip/ListTripsScheduled/ListTripsScheduled";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import SpinnerComponent from "@/components/Spinner/Spinner";

const ReservedPage = () => {
  const { isLoading, currentUser } = useCurrentUser();
  const { t } = useTranslation();
  const { search } = useLocation();
  const time = new URLSearchParams(search).get("time");

  return (
    <MainComponent>
      <MainHeader title={t("reserved_trips.title")} />
      <div className={styles.container_tab}>
        <TabComponent
          right_title={t("reserved_trips.future")}
          right_component={
            isLoading || currentUser === undefined ? (
              <SpinnerComponent />
            ) : (
              <ListTripsScheduled
                uri={currentUser.futureReservedTripsUri}
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
          left_title={t("reserved_trips.past")}
          left_component={
            isLoading || currentUser === undefined ? (
              <SpinnerComponent />
            ) : (
              <ListTripsScheduled
                uri={currentUser.pastReservedTripsUri}
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
          active={time == "past" ? "left" : "right"}
        />
      </div>
    </MainComponent>
  );
};

export default ReservedPage;
