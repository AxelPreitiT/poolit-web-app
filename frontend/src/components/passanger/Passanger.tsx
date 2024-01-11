import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { Passanger } from "@/types/Passanger";
import ProfilePhoto from "@/images/descarga.jpeg";
import CircleImg from "../img/circleImg/CircleImg";
import { useTranslation } from "react-i18next";
import { Button } from "react-bootstrap";
import StarRating from "../stars/StarsRanking";

interface PassangerComponentProp {
  passanger: Passanger;
}

const PassangerComponent = ({
  passanger: passanger,
}: PassangerComponentProp) => {
  const { t } = useTranslation();

  return (
    <div className={styles.passanger_container}>
      <div className={styles.left_container}>
        <CircleImg src={ProfilePhoto} size={70} />
        <div className={styles.name_container}>
          <h4>
            {t("format.name", {
              name: passanger.name,
              surname: passanger.surname,
            })}
          </h4>
          <span style={{ color: "gray", fontStyle: "italic" }}>
            {t("format.date", {
              date: passanger.startDateString,
            })}
          </span>
        </div>
      </div>
      <div className={styles.right_container}>
        <StarRating rating={3.5} size="x-large" />
        <div className={styles.info_passanger_style}>
          <div className={styles.btn_container}>
            <Button className={styles.btn_delete}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.my_trips")}</span>
              </div>
            </Button>
            <Button className={styles.btn_accept}>
              <div className={styles.create_trip_btn}>
                <span>{t("trip_detail.btn.cancel")}</span>
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
