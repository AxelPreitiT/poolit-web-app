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
import {useSearchParams} from "react-router-dom";
import getFormattedDateTime from "@/functions/DateFormat.ts";
// import {useSearchParams} from "react-router-dom";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const [params] = useSearchParams();
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
  const tripTime = getFormattedDateTime(trip.startDateTime).time;
  const startDateTime = isDriver?trip.startDateTime:(currentPassanger?.startDateTime || ((params.get("startDateTime")!=undefined)?`${params.get("startDateTime")}T${tripTime}`:trip.startDateTime));
  const endDateTime = isDriver?trip.endDateTime:(currentPassanger?.endDateTime || ((params.get("endDateTime")!=undefined)?`${params.get("endDateTime")}T${tripTime}`:((params.get("startDateTime")!=undefined)?`${params.get("startDateTime")}T${tripTime}`:trip.startDateTime)));
  console.log(`In details, start is ${startDateTime} and end is ${endDateTime}`);

  console.log("trips passanger uri template is "+ trip.passengersUriTemplate);
  console.log("ya sali")
  //const {isLoading: isLoadingSeats, data:occupiedSeats} = {false, {occupiedSeats:1}:occupiedSeatsModel}
  //const occupiedSeats:occupiedSeatsModel = {occupiedSeats:1};

// // Supongamos que tienes las cadenas de fecha y hora
//   const fecha = '2024-02-01'; // Formato: 'YYYY-MM-DD'
//   const hora = '12:30:00';    // Formato: 'HH:mm:ss'
//
// // Combina las cadenas de fecha y hora en un formato compatible con Date
//   const fechaHoraString = `${fecha}T${hora}`;

// Crea un objeto Date a partir de la cadena combinada
//   const fechaYHora = new Date(fechaHoraString);

// Puedes imprimir la fecha y hora resultante
//   console.log(fechaYHora);

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
              startDateTime={startDateTime}
              endDateTime={endDateTime}
              currentPassanger={currentPassanger}
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
              startDateTime={startDateTime}
              endDateTime={endDateTime}
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
              startDateTime = {startDateTime}
              endDateTime = {endDateTime}
            />
          </div>
        </div>
      </MainComponent>
      {isDriver && (
        <PassangersTripComponent uri={trip.passengersUriTemplate} maxSeats={ parseInt(trip.maxSeats, 10)} startDateTime={startDateTime} endDateTime={endDateTime} />
      )}
    </div>
  );
};

export default TripDetailsPage;
