import { useParams } from "react-router-dom";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import StatusTrip from "@/components/statusTrip/ProfileProp";
import MovingCar from "@/components/location/MovingCar";
import Location from "@/components/location/Location";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const { tripId } = useParams<{ tripId: string }>();

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("created_trips.title")}
          left_component={<StatusTrip status="Created" />}
        />
        <Location
          start_address="hola"
          start_city="hola"
          end_address="hola"
          end_city="hola"
        />
        <h1>Trip {tripId}</h1>
      </MainComponent>
      <MainComponent>
        <MainHeader
          title={t("created_trips.title")}
          left_component={<StatusTrip status="Created" />}
        />
        <h1>Trip {tripId}</h1>
      </MainComponent>
    </div>
  );
};

export default TripDetailsPage;
