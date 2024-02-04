import React from "react";
import styles from "./styles.module.scss";
import {useTranslation} from "react-i18next";
import Status from "@/enums/Status.ts";

export interface StatusTripProps {
  status: string;
}

const StatusTrip: React.FC<StatusTripProps> = ({status }) => {
  let componentToRender;
  const { t } = useTranslation();

  switch (status) {
      //TODO: falta status pending (debería usar el estado de passenger: Accepted, Rejected, Pending, Unconfirmed)
    case Status.WAITING:
      componentToRender = (
        <div className={styles.secondary}>
          <i className={"bi bi-clock-history"}></i>
          <h3>{t("trip_detail.status.waiting")}</h3>
        </div>
      );
      break;
    case Status.ACCEPTED:
      componentToRender = (
        <div className={styles.success}>
            <i className={"bi bi-check-lg"}></i>
            <h3>{t("trip_detail.status.accepted")}</h3>
        </div>
      );
      break;
      case Status.REJECTED:
          componentToRender = (
              <div className={styles.danger}>
                  <i className={"bi bi-x"}></i>
                  <h3>{t("trip_detail.status.rejected")}</h3>
              </div>
          );
          break;
    case Status.CANCEL:
      componentToRender = (
        <div className={styles.danger}>
            <i className={"bi bi-x"}></i>
            <h3>{t("trip_detail.status.cancelled")}</h3>
        </div>
      );
      break;
    case Status.FINISHED:
      componentToRender = (
        <div className={styles.success}>
            <i className={"bi bi-check-lg"}></i>
            <h3>{t("trip_detail.status.finished")}</h3>
        </div>
      );
      break;
      case Status.NOT_STARTED:
      componentToRender = (
          <div className={styles.secondary}>
              <i className={"bi bi-clock-history"}></i>
              <h3>{t("trip_detail.status.not_started")}</h3>
          </div>
      );
      break;
      case Status.IN_PROGRESS:
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
