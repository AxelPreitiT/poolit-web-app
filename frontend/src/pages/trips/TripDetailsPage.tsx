import styles from "./styles.module.scss";
import { useParams } from "react-router-dom";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusComponent from "@/components/statusTrip/StatusTrip";
import { Trip } from "@/types/Trip";
import TripInfo from "@/components/tripInfo/TripInfo";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const { tripId } = useParams<{ tripId: string }>();

  const trip: Trip = {
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
    startDateString: "10/02/2019",
    endDateString: "10/03/2019",
    travelInfoDateString: "travel info",
    startTimeString: "10:09",
    integerQueryTotalPrice: "10",
    decimalQueryTotalPrice: "05",
    queryIsRecurrent: false,
    car: {
      imageId: "http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80",
    },
  };

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("trip_detail.header")}
          left_component={
            <StatusComponent
              text={t("trip_detail.status.accepted")}
              icon={"bi bi-clock-history"}
              color={"success"}
            />
          }
        />
        <Location
          start_address={trip.originAddress}
          start_city={trip.originCity.name}
          end_address={trip.destinationAddress}
          end_city={trip.destinationCity.name}
        />
        <div className={styles.middle_content}>
          <TripInfo {...trip} />
          <div className={styles.img_container}>
            <img
              src="http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"
              className={styles.img_style}
              alt=""
            />
          </div>
        </div>

        <h1>Trip {tripId}</h1>
      </MainComponent>
      <MainComponent>
        <MainHeader title={t("created_trips.title")} />
        <h1>Trip {tripId}</h1>
      </MainComponent>
    </div>
  );
};

export default TripDetailsPage;
