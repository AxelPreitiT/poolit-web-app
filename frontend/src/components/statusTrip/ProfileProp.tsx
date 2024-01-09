import styles from "./styles.module.scss";

export interface StatusTripProps {
  status: string;
}

const StatusTrip = ({ status }: StatusTripProps) => {
  return (
    <div className={styles.waiting}>
      <i className="bi bi-geo-alt h3"></i>
      <h3>{status}</h3>
    </div>
  );
};

export default StatusTrip;
