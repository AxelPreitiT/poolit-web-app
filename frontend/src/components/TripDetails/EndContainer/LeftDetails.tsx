import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StatusTrip from "@/components/statusTrip/StatusTrip.tsx";
import {useTranslation} from "react-i18next";
import TripModel from "@/models/TripModel.ts";
import useGetEarning from "@/hooks/trips/useGetAmount.tsx";

interface LeftDetailsProps {
  trip : TripModel;
  isPassanger:boolean;
  isDriver:boolean;
  status: string;
}

const EarningComponent = () => {
  const { isLoading: isLoadingEarning, earning } = useGetEarning();
  const { t } = useTranslation();

  return (
      !isLoadingEarning &&
          <h3>{t("format.price", {
            priceInt: earning?.tripEarnings,
          })}</h3>
  );
}

const LeftDetails = ({trip, isPassanger, isDriver, status}: LeftDetailsProps) => {
  const { t } = useTranslation();

  return (
      <div className={styles.status_trip}>
        {isDriver  ?
          (<div className={styles.info_container}>
            <h3>{t("trip_detail.income")}</h3>
            <div className={styles.price_container}>
              <EarningComponent />
              {trip.totalTrips > 1 ?
                  (<span style={{ color: "gray", fontStyle: "italic" }}>{t("trip_detail.recurrent_trip", {number: trip.totalTrips})}</span>) :
                  (<span style={{ color: "gray", fontStyle: "italic" }}>{t("trip_detail.single_trip")}</span>)}
            </div>
          </div>) :
          (<div className={styles.info_container}>
            <h3>{t("trip_detail.price")}</h3>
            <div className={styles.price_container}>
              <h3>
                {t("format.price", {
                  priceInt: trip.pricePerTrip,
                })}
              </h3>
              {trip.totalTrips > 1 ?
                  (<span style={{ color: "gray", fontStyle: "italic" }}>{t("trip_detail.recurrent_trip", {number: trip.totalTrips})}</span>) :
                  (<span style={{ color: "gray", fontStyle: "italic" }}>{t("trip_detail.single_trip")}</span>)}
            </div>
          </div>)}
          {(isPassanger || isDriver) &&
              <div className={styles.info_container}>
                  <h3>{t("trip_detail.status.title")}</h3>
                  <StatusTrip status={status}/>
              </div>
          }
      </div>
  )
};

export default LeftDetails;
