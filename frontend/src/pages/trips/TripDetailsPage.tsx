import styles from "./styles.module.scss";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusTrip from "@/components/statusTrip/StatusTrip";
import TripInfo from "@/components/tripInfo/TripInfo";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import LeftDetails from "@/components/TripDetails/EndContainer/LeftDetails.tsx";
import RightDetails from "@/components/TripDetails/EndContainer/RightDetails.tsx";
import useCarByUri from "@/hooks/cars/useCarByUri.tsx";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import useRolePassanger from "@/hooks/passanger/useRolePassanger.tsx";
import LoadingScreen from "@/components/loading/LoadingScreen";
import PassangersTripComponent from "@/components/TripDetails/PassangerTripComponent/PassangersTripComponent.tsx";
import useTrip from "@/hooks/trips/useTrip.tsx";

const TripDetailsPage = () => {
  const { t } = useTranslation();

  const { currentUser } = useCurrentUser();
  const { isLoading: isLoadingTrip, trip: trip } = useTrip();
  const { isLoading: isLoadingCar, car: car } = useCarByUri(trip?.carUri);
  const { isLoading: isLoadingDriver, user: driver } = usePublicUserByUri(
    trip?.driverUri
  );
  const isDriver = trip?.driverUri === currentUser?.selfUri;



  const {
    isLoading: isLoadingRole,
    currentPassanger: currentPassanger,
    isError: isError,
  } = useRolePassanger(isDriver, trip?.passengersUriTemplate);
  const isPassanger = !isError;


  if (
    isLoadingTrip ||
    trip == undefined ||
    isLoadingCar ||
    car === undefined ||
    isLoadingDriver ||
    driver === undefined
  ) {
    return <LoadingScreen description={t("trip.loading_one")} />;
  }

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("trip_detail.header")}
          left_component={
            !isDriver &&
            isPassanger &&
            !isLoadingRole &&
              currentPassanger != undefined && (
              <StatusTrip status={currentPassanger.passengerState} />
            )
          }
        />
        <div>
          {isDriver && <h1>DRIVER</h1>}
          {isPassanger && <h1>PASSANGER</h1>}
          {!isDriver && !isPassanger && <h1>NOTHING</h1>}
          <Location
            startAddress={trip.originAddress}
            startCityUri={trip.originCityUri}
            endAddress={trip.destinationAddress}
            endCityUri={trip.destinationCityUri}
          />
          <div className={styles.middle_content}>
            <TripInfo
              trip={trip}
              car={car}
              driver={driver}
              isDriver={isDriver}
            />
            <div className={styles.img_container}>
              <img src={car?.imageUri} className={styles.img_style} alt="" />
            </div>
          </div>

          <div className={styles.end_container}>
            <LeftDetails
              trip={trip}
              isPassanger={isPassanger}
              isDriver={isDriver}
              status={trip.tripStatus}
            />
            <RightDetails
              isPassanger={isPassanger}
              isDriver={isDriver}
              trip={trip}
              passanger={currentPassanger}
              status={trip.tripStatus}
              driver={driver}
              car={car}
            />
          </div>
        </div>
      </MainComponent>
      {isDriver && (
        <PassangersTripComponent uri={trip.passengersUriTemplate} fullSeats={0 == parseInt(trip.maxSeats , 10) - parseInt(trip.occupiedSeats, 10)} />
      )}
    </div>
  );
};

export default TripDetailsPage;
