import React from "react";
import styles from "./styles.module.scss";

export interface StatusTripProps {
  text: string;
  icon: string;
  color: string;
}

const StatusTrip: React.FC<StatusTripProps> = ({ icon, text, color }) => {
  let componentToRender;

  switch (color) {
    case "secondary":
      componentToRender = (
        <div className={styles.secondary}>
          <i className={icon}></i>
          <h3>{text}</h3>
        </div>
      );
      break;
    case "success":
      componentToRender = (
        <div className={styles.success}>
          <i className={icon}></i>
          <h3>{text}</h3>
        </div>
      );
      break;
    case "danger":
      componentToRender = (
        <div className={styles.danger}>
          <i className={icon}></i>
          <h3>{text}</h3>
        </div>
      );
      break;
    case "primary":
      componentToRender = (
        <div className={styles.primary}>
          <i className={icon}></i>
          <h3>{text}</h3>
        </div>
      );
      break;
  }

  return <div className={styles.status_container}>{componentToRender}</div>;
};

export default StatusTrip;
