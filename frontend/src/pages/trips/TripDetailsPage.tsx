import styles from "./styles.module.scss";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusTrip from "@/components/statusTrip/StatusTrip";
import TripInfo from "@/components/tripInfo/TripInfo";
import { Button } from "react-bootstrap";
import {useEffect, useState} from "react";
import {useParams, useSearchParams} from "react-router-dom";
import TripModel from "@/models/TripModel.ts";
import tripsService from "@/services/TripsService.ts";
import CreateUri from "@/functions/CreateUri.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import CarService from "@/services/CarService.ts";
import CarModel from "@/models/CarModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import UserService from "@/services/UserService.ts";
import PassangerService from "@/services/PassangerService.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import getTripRole from "@/functions/GetTripRole.ts";
import PassangersTripComponent from "@/components/TripDetails/PassangerTripComponent/PassangersTripComponent.tsx";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const id = useParams();
  const [params,] = useSearchParams();
  const { isLoading, currentUser } = useCurrentUser();

  const [Trip, setTrip] = useState<TripModel | null>(null);
  const [CarTrip, setCarTrip] = useState<CarModel | null>(null);
  const [DriverTrip, setDriverTrip] = useState<UserPublicModel | null>(null);
  const [PassangersTrip, setPassangersTrip] = useState<PassangerModel[] | null>(null);

  const link = CreateUri(id.tripId, params.toString(), '/trips')

  useEffect(() => {
    tripsService.getTripById(link).then((response) => {
      setTrip(response);
    });
    if (Trip != null) {
      CarService.getCarById(Trip.carUri).then((response) => {
        setCarTrip(response);
      });
      UserService.getUserById(Trip.driverUri).then((response) => {
        setDriverTrip(response);
      });
      PassangerService.getPassangersTrips(Trip.passengersUri).then((response) => {
        setPassangersTrip(response);
      });
    }
  });




  const {isPassanger, isDriver} = isLoading || currentUser == null  || DriverTrip == null || PassangersTrip == null ? {isPassanger:false, isDriver:false} : getTripRole(currentUser, DriverTrip, PassangersTrip);

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("trip_detail.header")}
          left_component={ isPassanger && (<StatusTrip status={"ACCEPT"}/>)}
        />

        {Trip == undefined || CarTrip === null || DriverTrip === null ?
          (<SpinnerComponent /> ) :
          (<div>
              <Location
                startAddress={Trip.originAddress}
                startCityUri={Trip.originCityUri}
                endAddress={Trip.destinationAddress}
                endCityUri={Trip.destinationCityUri}
              />
              <div className={styles.middle_content}>
                <TripInfo trip={Trip} car={CarTrip} driver={DriverTrip} />
                <div className={styles.img_container}>
                  <img
                      src={CarTrip?.imageUri}
                      className={styles.img_style}
                      alt=""
                  />
                </div>
              </div>

              <div className={styles.end_container}>
                <div className={styles.status_trip}>
                  <div className={styles.info_container}>
                    <h3>Income:</h3>
                    <div className={styles.price_container}>
                      <h3>
                        {t("format.price", {
                          priceInt: Trip.pricePerTrip,
                          princeFloat: 0,
                        })}
                      </h3>
                      <span style={{ color: "gray", fontStyle: "italic" }}>{Trip.totalTrips} viajes</span>
                    </div>
                  </div>
                  <div className={styles.info_container}>
                    <h3>Status:</h3>
                    <StatusTrip status={"ACCEPT"} />
                  </div>
                </div>
                <div className={styles.btn_container}>
                  <Button className={styles.btn_trips}>
                    <div className={styles.create_trip_btn}>
                      <i className="bi bi-car-front-fill light-text"></i>
                      <span>{t("trip_detail.btn.my_trips")}</span>
                    </div>
                  </Button>
                  <Button className={styles.btn_cancel}>
                    <div className={styles.create_trip_btn}>
                      <i className="bi bi-x light-text"></i>
                      <span>{t("trip_detail.btn.cancel")}</span>
                    </div>
                  </Button>
                </div>
              </div>
            </div>)}
      </MainComponent>
      {isDriver && PassangersTrip != null  &&
          <PassangersTripComponent passangers={PassangersTrip}/>
      }
    </div>
  );
}

export default TripDetailsPage;
