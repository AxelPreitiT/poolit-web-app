import "./MovingCar.css";
import MovingCar from "./MovingCar";
import styles from "./styles.module.scss";
import useCityByUri from "@/hooks/cities/useCityByUri";
import LoadingWheel from "../loading/LoadingWheel";
import { useTranslation } from "react-i18next";

export interface LocationProps {
  startCityUri: string;
  startAddress: string;
  endAddress: string;
  endCityUri: string;
}

const Location = ({
  startCityUri,
  startAddress,
  endAddress,
  endCityUri,
}: LocationProps) => {
  const { t } = useTranslation();
  const {
    isLoading: isOriginCityLoading,
    city: originCity,
    isError: isOriginCityError,
  } = useCityByUri(startCityUri);
  const {
    isLoading: isDestinationCityLoading,
    city: destinationCity,
    isError: isDestinationCityError,
  } = useCityByUri(endCityUri);

  if (
    isOriginCityLoading ||
    isDestinationCityLoading ||
    isOriginCityError ||
    isDestinationCityError
  ) {
    return <LoadingWheel description={t("location.loading")} />;
  }

  return (
    <div className={styles.location_container}>
      <div className={styles.direction_container_r}>
        <h1>{originCity?.name}</h1>
        <h5>{startAddress}</h5>
      </div>
      <div className={styles.car_container}>
        <i className="bi bi-geo-alt h2"></i>
        <MovingCar />
        <i className="bi bi-geo-alt-fill h2"></i>
      </div>
      <div className={styles.direction_container_l}>
        <h1>{destinationCity?.name}</h1>
        <h5>{endAddress}</h5>
      </div>
    </div>
  );
};

export default Location;
