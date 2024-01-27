import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import CircleImg from "../img/circleImg/CircleImg";
import { useTranslation } from "react-i18next";
import { Button } from "react-bootstrap";
import StarRating from "../stars/StarsRanking";
import PassangerModel from "@/models/PassangerModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import PassangerStatus from "@/enums/PassangerStatus.ts";
import useAcceptPassangerByUri from "@/hooks/passanger/useAcceptPassangerByUri.tsx";


const PassangerComponent = (passanger: PassangerModel) => {
  const { t } = useTranslation();
  const {isLoading, data:UserTrip} =  usePublicUserByUri(passanger.userUri);
  const {onSubmit } = useAcceptPassangerByUri();


  return (
    <div className={styles.passanger_container}>
      {isLoading ||  UserTrip === undefined ?
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
              <h4>{passanger.selfUri}</h4>
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
            <Button className={styles.btn_delete} disabled={passanger.passengerState != PassangerStatus.PENDING} onClick={() => onSubmit(passanger.selfUri)}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.accept")}</span>
              </div>
            </Button>
            <Button className={styles.btn_accept} disabled={passanger.passengerState != PassangerStatus.PENDING}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.reject")}</span>
              </div>
            </Button>
          </div>
          {passanger.passengerState == PassangerStatus.ACCEPTED &&
          <span style={{ color: "gray", fontStyle: "italic" }}>{t('trip_detail.passengers.passsanger_accepted')}</span>}
          {passanger.passengerState == PassangerStatus.REJECTED &&
          <span style={{ color: "gray", fontStyle: "italic" }}>{t('trip_detail.passengers.passsanger_rejected')}</span>}
        </div>
      </div>
    </div>
  );
};

export default PassangerComponent;
