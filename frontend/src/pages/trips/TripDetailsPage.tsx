import styles from "./styles.module.scss";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusComponent from "@/components/statusTrip/StatusTrip";
import TripInfo from "@/components/tripInfo/TripInfo";
import { Button } from "react-bootstrap";
import {useEffect, useState} from "react";
import Dropdown from "react-bootstrap/Dropdown";
import PassangerComponent from "@/components/passanger/Passanger";
import { Passanger as PassangerType } from "@/types/Passanger";
import {useParams, useSearchParams} from "react-router-dom";
import TripModel from "@/models/TripModel.ts";
import tripsService from "@/services/TripsService.ts";
import CreateUri from "@/functions/CreateUri.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import CarService from "@/services/CarService.ts";
import CarModel from "@/models/CarModel.ts";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const id = useParams();
  const [params,] = useSearchParams();

  const [Trip, setTrip] = useState<TripModel | null>(null);
  const [CarTrip, setCarTrip] = useState<CarModel | null>(null);

  const link = CreateUri(id.tripId, params.toString(), '/trips')

  useEffect(() => {
    tripsService.getTripById(link).then((response) => {
      setTrip(response);
    });
    if (Trip != null) {
      CarService.getCarById(Trip.carUri).then((response) => {
        setCarTrip(response);
      });
    }
  });


  const options = ["All", "Accepted", "Waiting", "Rejected"];
  const [selectedOption, setSelectedOption] = useState<string>("All");
  const handleSelect = (eventKey: string | null) => {
    if (eventKey !== null) {
      setSelectedOption(eventKey);
    }
  };

  const passanger_data: PassangerType = {
    userId: 2,
    userImageId: 2,
    name: "Pedro",
    surname: "josesito",
    recurrent: false,
    startDateString: "fecha",
    endDateString: "10/03/1029",
    user: {
      passengerRating: 3.5,
    },
    tripStarted: true,
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
        {Trip == undefined || CarTrip === null ?
          (<SpinnerComponent /> ) :
          (<div>
              <Location
                startAddress={Trip.originAddress}
                startCityUri={Trip.originCityUri}
                endAddress={Trip.destinationAddress}
                endCityUri={Trip.destinationCityUri}
              />
              <div className={styles.middle_content}>
                <TripInfo trip={Trip} car={CarTrip} />
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
                    <StatusComponent
                        text={"Accepted"}
                        icon={"bi bi-clock-history"}
                        color={"success"}
                    />
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
      <MainComponent>
        <MainHeader title={t("trip_detail.passengers")} />
        <div className={styles.dropdown_style}>
          <Dropdown onSelect={handleSelect}>
            <Dropdown.Toggle variant="success" id="dropdown-basic">
              {t("trip_detail.filter_by", {
                status: selectedOption,
              })}
            </Dropdown.Toggle>

            <Dropdown.Menu className={styles.dropdown_menu}>
              {options.map((option, index) => (
                <Dropdown.Item key={index} eventKey={option}>
                  {option}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>
        </div>
        <div className={styles.passangers_container}>
          <PassangerComponent passanger={passanger_data} />
        </div>
      </MainComponent>
    </div>
  );
}

export default TripDetailsPage;
