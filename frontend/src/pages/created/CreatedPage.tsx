import styles from "./styles.module.scss";
import MainHeader from "@/components/utils/MainHeader";
import MainComponent from "@/components/utils/MainComponent";
import TabComponent from "@/components/tab/TabComponent";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import CardTripScheduled from "@/components/cardTrip/cardTripScheduled/cardTripScheduled";
import { Trip } from "@/types/Trip";
import PaginationList from "@/components/paginationList/paginationList";
import EmptyList from "@/components/emptyList/EmptyList";

const CreatedPage = () => {
  const { t } = useTranslation();
  const { search } = useLocation();
  const time = new URLSearchParams(search).get("time");

  const dataFuture: Trip[] = [
    {
      tripId: 1,
      originCity: {
        name: "Balvanera",
      },
      originAddress: "Av independencia 2135",
      destinationCity: {
        name: "Parque Patricios",
      },
      destinationAddress: "Iguazu 341",
      dayOfWeekString: "Miercoles",
      startDateString: "Date string",
      endDateString: "Date string",
      travelInfoDateString: "travel info",
      startTimeString: "time",
      integerQueryTotalPrice: "10",
      decimalQueryTotalPrice: "00",
      queryIsRecurrent: false,
      car: {
        imageId: "imagen",
      },
    },
  ];

  const dataPast: Trip[] = [];

  return (
    <MainComponent>
      <MainHeader title={t("created_trips.title")} />
      <div className={styles.container_tab}>
        <TabComponent
          right_component={
            <PaginationList
              pagination_component={<h3>hola</h3>}
              empty_component={
                <EmptyList
                  text={t("created_trips.empty")}
                  second_text={t("created_trips.more")}
                  icon={"car-front-fill"}
                />
              }
              data={dataFuture}
              component_name={CardTripScheduled}
            />
          }
          left_component={
            <PaginationList
              pagination_component={<h3>hola</h3>}
              empty_component={
                <EmptyList
                  text={t("created_trips.empty")}
                  second_text={t("created_trips.more")}
                  icon={"car-front-fill"}
                />
              }
              data={dataPast}
              component_name={CardTripScheduled}
            />
          }
          right_title={t("created_trips.future")}
          left_title={t("created_trips.past")}
          active={time == "past" ? "left" : "right"}
        />
      </div>
    </MainComponent>
  );
};

export default CreatedPage;
