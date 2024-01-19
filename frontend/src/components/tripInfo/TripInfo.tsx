import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg";
import StarRating from "@/components/stars/StarsRanking";
import TripModel from "@/models/TripModel.ts";
import CarModel from "@/models/CarModel.ts";

interface TripInfoProps {
    trip: TripModel;
    car: CarModel;
}

const TripInfo = ({trip, car} : TripInfoProps) => {
  return (
    <div className={styles.info_trip}>
      <div className={styles.show_row}>
        <i className="bi bi-clock light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">{trip.startDateTime}</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-calendar light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER DIA</span>
          <span className={styles.subtitle_info}>{trip.startDateTime}</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <CircleImg
          src={car.imageUri}
          size={40}
        />
        <div className={styles.info_details}>
          <span className="light-text detail">PONER CAR</span>
          <StarRating rating={3.5} color={"#ffffff"} size="10" />
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-people light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER ASIENTOS</span>
        </div>
      </div>
      <hr className="white" />
      <div className={styles.show_row}>
        <CircleImg
          src="http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"
          size={40}
        />
        <div className={styles.info_details}>
          <span className="light-text detail">PONER USER</span>
          <StarRating rating={3.5} color={"#ffffff"} size="10" />
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-envelope-fill light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER EMAIL</span>
        </div>
      </div>
      <div className={styles.show_row}>
        <i className="bi bi-telephone-fill light-text"></i>
        <div className={styles.info_details}>
          <span className="light-text detail">PONER CELU</span>
        </div>
      </div>
    </div>
  );
};

export default TripInfo;
