import "./MovingCar.css";
import MovingCar from "./MovingCar";
import styles from "./styles.module.scss";

export interface LocationProps {
  start_city: string;
  start_address: string;
  end_city: string;
  end_address: string;
}

const Location = ({
  start_city,
  start_address,
  end_address,
  end_city,
}: LocationProps) => {
  return (
    <div className={styles.location_container}>
      <div className={styles.direction_container_r}>
        <h1>{start_city}</h1>
        <h5>{start_address}</h5>
      </div>
      <div className={styles.car_container}>
        <i className="bi bi-geo-alt h2"></i>
        <MovingCar />
        <i className="bi bi-geo-alt-fill h2"></i>
      </div>
      <div className={styles.direction_container_l}>
        <h1>{end_city}</h1>
        <h5>{end_address}</h5>
      </div>
    </div>
  );
};

export default Location;
