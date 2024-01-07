import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import { Trip } from "@/types/Trip";

const CardTripScheduled = (Trip: Trip) => {
  const { t } = useTranslation();

  return (
    <div className="trip-card-row">
      <div className="trip-car-row-body">
        <h4>hola</h4>
      </div>
    </div>
  );
};

export default CardTripScheduled;
