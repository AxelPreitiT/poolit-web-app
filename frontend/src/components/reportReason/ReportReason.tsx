import React from "react";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";

export interface ReportReasonProps {
  reason: string;
}

const ReportReason: React.FC<ReportReasonProps> = ({ reason }) => {
  const { t } = useTranslation();

  return (
    <span className={styles.status_container}>
      {t(`admin.reason.${reason}`)}
    </span>
  );
};

export default ReportReason;
