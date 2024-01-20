import React from "react";
import styles from "./styles.module.scss";
import {useTranslation} from "react-i18next";

export interface StatusTripProps {
  status: string;
}

const StatusTrip: React.FC<StatusTripProps> = ({status }) => {
  let componentToRender;
  const { t } = useTranslation();

  switch (status) {
    case "WAITING":
      componentToRender = (
        <div className={styles.secondary}>
          <i className={"bi bi-clock-history"}></i>
          <h3>{t("trip_detail.status.waiting")}</h3>
        </div>
      );
      break;
    case "ACCEPT":
      componentToRender = (
        <div className={styles.success}>
            <i className={"bi bi-check-lg"}></i>
            <h3>{t("trip_detail.status.accepted")}</h3>
        </div>
      );
      break;
    case "CANCEL":
      componentToRender = (
        <div className={styles.danger}>
            <i className={"bi bi-x"}></i>
            <h3>{t("trip_detail.status.cancelled")}</h3>
        </div>
      );
      break;
    case "FINISHED":
      componentToRender = (
        <div className={styles.success}>
            <i className={"bi bi-check-lg"}></i>
            <h3>{t("trip_detail.status.finished")}</h3>
        </div>
      );
      break;
      case "NOT_STARTED":
      componentToRender = (
          <div className={styles.secondary}>
              <i className={"bi bi-clock-history"}></i>
              <h3>{t("trip_detail.status.not_started")}</h3>
          </div>
      );
      break;
      case "IN_PROGRESS":
      componentToRender = (
          <div className={styles.secondary}>
              <i className={"bi bi-clock-history"}></i>
              <h3>{t("trip_detail.status.in_progress")}</h3>
          </div>
      );
      break;
  }

  return <div className={styles.status_container}>{componentToRender}</div>;
};

export default StatusTrip;
