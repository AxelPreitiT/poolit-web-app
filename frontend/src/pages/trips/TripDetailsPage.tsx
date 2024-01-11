import styles from "./styles.module.scss";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import StatusComponent from "@/components/statusTrip/StatusTrip";
import { Trip } from "@/types/Trip";
import TripInfo from "@/components/tripInfo/TripInfo";
import { Button } from "react-bootstrap";
import { useState } from "react";
import Dropdown from "react-bootstrap/Dropdown";
import PassangerComponent from "@/components/passanger/Passanger";
import { Passanger as PassangerType } from "@/types/Passanger";

const TripDetailsPage = () => {
  const { t } = useTranslation();

  const options = ["All", "Accepted", "Waiting", "Rejected"];
  const [selectedOption, setSelectedOption] = useState<string>("All");

  const handleSelect = (eventKey: string | null) => {
    if (eventKey !== null) {
      setSelectedOption(eventKey);
    }
  };

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

        <div className={styles.end_container}>
          <div className={styles.status_trip}>
            <div className={styles.info_container}>
              <h3>Income:</h3>
              <div className={styles.price_container}>
                <h3>
                  {t("format.price", {
                    priceInt: trip.integerQueryTotalPrice,
                    princeFloat: trip.decimalQueryTotalPrice,
                  })}
                </h3>
                <span style={{ color: "gray", fontStyle: "italic" }}>
                  4 viajes
                </span>
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
};

export default TripDetailsPage;
