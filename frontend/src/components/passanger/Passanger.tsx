import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import CircleImg from "../img/circleImg/CircleImg";
import { useTranslation } from "react-i18next";
import { Button } from "react-bootstrap";
import StarRating from "../stars/StarsRanking";
import PassangerModel from "@/models/PassangerModel.ts";
import {useEffect, useState} from "react";
import UserService from "@/services/UserService.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";



const PassangerComponent = (passanger: PassangerModel) => {
  const { t } = useTranslation();
  const [UserTrip, setUserTrip] = useState<UserPublicModel | null>(null);


  useEffect(() => {
    UserService.getUserById(passanger.userUri).then((response) => {
      setUserTrip(response);
    });
  });

  return (
    <div className={styles.passanger_container}>
      { UserTrip === null ?
          (<SpinnerComponent /> ) :
          (<div className={styles.left_container}>
            <CircleImg src={UserTrip.imageUri} size={70} />
            <div className={styles.name_container}>
              <h4>
                {t("format.name", {
                  name: UserTrip.username,
                  surname: UserTrip.surname,
                })}
              </h4>
              <span style={{ color: "gray", fontStyle: "italic" }}>
            {t("format.date", {
              date: passanger.startDateTime,
            })}
          </span>
            </div>
          </div>) }
      <div className={styles.right_container}>
        <StarRating rating={0} size="x-large" />
        <div className={styles.info_passanger_style}>
          <div className={styles.btn_container}>
            <Button className={styles.btn_delete}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.passangers.btn_accept")}</span>
              </div>
            </Button>
            <Button className={styles.btn_accept}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.passangers.btn_delete")}</span>
              </div>
            </Button>
          </div>
          <span style={{ color: "gray", fontStyle: "italic" }}>PONER</span>
        </div>
      </div>
    </div>
  );
};

export default PassangerComponent;
