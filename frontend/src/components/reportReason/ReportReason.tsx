import React from "react";
import styles from "./styles.module.scss";
import {useTranslation} from "react-i18next";
import Reason from "@/enums/ReportReason";

export interface ReportReasonProps {
  reason: string;
}

const ReportReason: React.FC<ReportReasonProps> = ({reason }) => {
  let componentToRender;
  const { t } = useTranslation();

  switch (reason) {
    case Reason.CANNOT_DRIVE:
      componentToRender = (
        <div className={styles.secondary}>
          <h3>{t("admin.reason.cannot_drive")}</h3>
        </div>
      );
      break;
    case Reason.DANGEROUS_DRIVING:
      componentToRender = (
        <div className={styles.secondary}>
            <h3>{t("admin.reason.dangerous_driving")}</h3>
        </div>
      );
      break;
    case Reason.DID_NOT_PAY:
      componentToRender = (
        <div className={styles.secondary}>
            <h3>{t("admin.reason.did_not_pay")}</h3>
        </div>
      );
      break;
    case Reason.HARASSMENT:
      componentToRender = (
        <div className={styles.secondary}>
            <h3>{t("admin.reason.harassment")}</h3>
        </div>
      );
      break;
      case Reason.IDENTITY_FRAUD:
      componentToRender = (
          <div className={styles.secondary}>
              <h3>{t("admin.reason.identity_fraud")}</h3>
          </div>
      );
      break;
      case Reason.MISCONDUCT:
      componentToRender = (
          <div className={styles.secondary}>
              <h3>{t("admin.reason.misconduct")}</h3>
          </div>
      );
      break;
      case Reason.WRECK_CAR:
      componentToRender = (
          <div className={styles.secondary}>
              <h3>{t("admin.reason.wreck_car")}</h3>
          </div>
      );
      break;
      case Reason.OTHER:
      componentToRender = (
          <div className={styles.secondary}>
              <h3>{t("admin.reason.other")}</h3>
          </div>
      );
      break;
  }

  return <div className={styles.status_container}>{componentToRender}</div>;
};

export default ReportReason;
