import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StatusTrip from "@/components/statusTrip/StatusTrip.tsx";
import {useTranslation} from "react-i18next";
import TripModel from "@/models/TripModel.ts";

interface LeftDetailsProps {
  trip : TripModel;
  isPassanger:boolean;
  isDriver:boolean;
}

const LeftDetails = ({trip, isPassanger, isDriver}: LeftDetailsProps) => {
  const { t } = useTranslation();

  const status = "WAITING"
  return (
      <div className={styles.status_trip}>
        {isDriver ?
          (<div className={styles.info_container}>
            <h3>Income:</h3>
            <div className={styles.price_container}>
              <h3>PONER PRECIO CONDUC</h3>
              <span style={{ color: "gray", fontStyle: "italic" }}>{trip.totalTrips} viajes</span>
            </div>
          </div>) :
          (<div className={styles.info_container}>
            <h3>Price:</h3>
            <div className={styles.price_container}>
              <h3>
                {t("format.price", {
                  priceInt: trip.pricePerTrip,
                  princeFloat: 0,
                })}
              </h3>
              <span style={{ color: "gray", fontStyle: "italic" }}>{trip.totalTrips} viajes</span>
            </div>
          </div>)}
          {isPassanger || isDriver &&
              <div className={styles.info_container}>
                  <h3>Status:</h3>
                  <StatusTrip status={status} />
              </div>
          }
      </div>
  )
};

export default LeftDetails;
