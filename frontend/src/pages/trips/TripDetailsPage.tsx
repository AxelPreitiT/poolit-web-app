import styles from "./styles.module.scss";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusTrip from "@/components/statusTrip/StatusTrip";
import TripInfo from "@/components/tripInfo/TripInfo";
import {useParams, useSearchParams} from "react-router-dom";
import CreateUri from "@/functions/CreateUri.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import getTripRole from "@/functions/GetTripRole.ts";
import PassangersTripComponent from "@/components/TripDetails/PassangerTripComponent/PassangersTripComponent.tsx";
import LeftDetails from "@/components/TripDetails/EndContainer/LeftDetails.tsx";
import RightDetails from "@/components/TripDetails/EndContainer/RightDetails.tsx";
import Status from "@/enums/Status.ts";
import useTripByUri from "@/hooks/trips/useTripByUri.tsx";
import useCarByUri from "@/hooks/cars/useCarByUri.tsx";
import usePublicUser from "@/hooks/users/usePublicUser.tsx";
import usePassangers from "@/hooks/passanger/usePassangers.tsx";


const TripDetailsPage = () => {
  const { t } = useTranslation();
  const id = useParams();
  const [params,] = useSearchParams();
  const { isLoading, currentUser } = useCurrentUser();

  const link = CreateUri(id.tripId, params.toString(), '/trips')

  const {isLoading:isLoadingTrip, trip:trip} = useTripByUri(link);
  const {isLoading:isLoadingCar, car:car} = useCarByUri(trip?.carUri);
  const {isLoading:isLoadingDriver, driver:driver} = usePublicUser(trip?.driverUri)
  const {isLoading:isLoadingPassangers, passangers:passangers} = usePassangers(trip?.passengersUriTemplate)


  const {isPassanger, isDriver} = isLoadingPassangers || isLoadingDriver || isLoadingCar ||  isLoadingTrip || isLoading || currentUser == null  || driver == undefined || passangers == undefined ? {isPassanger:false, isDriver:false} : getTripRole(currentUser, driver, passangers);

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("trip_detail.header")}
          left_component={ isPassanger && (<StatusTrip status={"ACCEPT"}/>)}
        />

        {trip == undefined || car === undefined || driver === undefined ?
          (<SpinnerComponent /> ) :
          (<div>
              <Location
                startAddress={trip.originAddress}
                startCityUri={trip.originCityUri}
                endAddress={trip.destinationAddress}
                endCityUri={trip.destinationCityUri}
              />
              <div className={styles.middle_content}>
                <TripInfo trip={trip} car={car} driver={driver} isDriver={isDriver}/>
                <div className={styles.img_container}>
                  <img
                      src={car?.imageUri}
                      className={styles.img_style}
                      alt=""
                  />
                </div>
              </div>

              <div className={styles.end_container}>
                <LeftDetails trip={trip} isPassanger={isPassanger} isDriver={isDriver} status={Status.FINISHED}/>
                <RightDetails isPassanger={isPassanger} isDriver={isDriver} status={Status.FINISHED}/>
              </div>
            </div>)}
      </MainComponent>
      { passangers == undefined ?
          (<SpinnerComponent />) :
          (<PassangersTripComponent passangers={passangers}/> )
      }
    </div>
  );
}

export default TripDetailsPage;
