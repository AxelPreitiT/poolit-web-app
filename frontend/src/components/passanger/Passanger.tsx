import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import CircleImg from "../img/circleImg/CircleImg";
import { useTranslation } from "react-i18next";
import { Button } from "react-bootstrap";
import StarRating from "../stars/StarsRanking";
import PassangerModel from "@/models/PassangerModel.ts";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import PassangerStatus from "@/enums/PassangerStatus.ts";
import LoadingWheel from "../loading/LoadingWheel";

const PassangerComponent = (passanger: PassangerModel) => {
  const { t } = useTranslation();
  const { isLoading, data: UserTrip } = usePublicUserByUri(passanger.userUri);
  //const {onSubmit } = useAcceptPassangerByUri("http://localhost:8080/paw-2023a-07/api/145/passengers/2");

  return (
    <div className={styles.passanger_container}>
      {isLoading || UserTrip === undefined ? (
        <LoadingWheel description={t("trip_detail.passengers.loading")} />
      ) : (
        <div className={styles.left_container}>
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
        </div>
      )}
      <div className={styles.right_container}>
        <StarRating rating={0} className="h3" />
        <div className={styles.info_passanger_style}>
          <div className={styles.btn_container}>
            <Button
              className={styles.btn_delete}
              disabled={passanger.passengerState != PassangerStatus.PENDING}
            >
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.accept")}</span>
              </div>
            </Button>
            <Button
              className={styles.btn_accept}
              disabled={passanger.passengerState != PassangerStatus.PENDING}
            >
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.reject")}</span>
              </div>
            </Button>
          </div>
          {passanger.passengerState == PassangerStatus.ACCEPTED && (
            <span style={{ color: "gray", fontStyle: "italic" }}>
              {t("trip_detail.passengers.passsanger_accepted")}
            </span>
          )}
          {passanger.passengerState == PassangerStatus.REJECTED && (
            <span style={{ color: "gray", fontStyle: "italic" }}>
              {t("trip_detail.passengers.passsanger_rejected")}
            </span>
          )}
        </div>
      </div>
    </div>
  );
};

export default PassangerComponent;
