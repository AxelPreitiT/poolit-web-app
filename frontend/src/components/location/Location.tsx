import "./MovingCar.css";
import MovingCar from "./MovingCar";
import styles from "./styles.module.scss";
import {useEffect, useState} from "react";
import CityService from "@/services/CityService.ts";

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

    const [cityOrigin, setCityOrigin] = useState<string | null>(null);
    const [cityDestination, setCityDestination] = useState<string | null>(null);

    useEffect(() => {
        CityService.getCityById(startCityUri).then((response) => {
            setCityOrigin(response.name);
        });
        CityService.getCityById(endCityUri).then((response) => {
            setCityDestination(response.name);
        });
    });

    return (
    <div className={styles.location_container}>
      <div className={styles.direction_container_r}>
        <h1>{cityOrigin}</h1>
        <h5>{startAddress}</h5>
      </div>
      <div className={styles.car_container}>
        <i className="bi bi-geo-alt h2"></i>
        <MovingCar />
        <i className="bi bi-geo-alt-fill h2"></i>
      </div>
      <div className={styles.direction_container_l}>
        <h1>{cityDestination}</h1>
        <h5>{endAddress}</h5>
      </div>
    </div>
  );
};

export default Location;
