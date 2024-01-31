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
import useAcceptPassangerByUri from "@/hooks/passanger/useAcceptPassangerByUri.tsx";
import useRejectPassangerByUri from "@/hooks/passanger/useRejectPassangerByUri.tsx";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import getStatusPassanger from "@/functions/getStatusPassanger.tsx";
import reserveStatus from "@/enums/ReserveStatus.ts";
import { useNavigate } from "react-router-dom";
import { publicProfilePath } from "@/AppRouter";

interface PassangerComponentProps {
  data: PassangerModel;
  extraData?: boolean;
}

const PassangerComponent = ({
  data: passanger,
  extraData: fullSeats,
}: PassangerComponentProps) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { isLoading, data: UserTrip } = usePublicUserByUri(passanger.userUri);
  const { onSubmit: onSubmitAccept } = useAcceptPassangerByUri();
  const { onSubmit: onSubmitReject } = useRejectPassangerByUri();
  const statusPassanger = getStatusPassanger(passanger);

  const onClickUser = () => {
    navigate(publicProfilePath.replace(":id", passanger.userId));
  };

  return (
    <div className={styles.passanger_container}>
      {isLoading || UserTrip === undefined ? (
        <LoadingWheel description={t("trip_detail.passengers.loading")} />
      ) : (
        <div className={styles.left_container}>
          <div className={styles.passengerRedirect} onClick={onClickUser}>
            <CircleImg src={UserTrip.imageUri} size={70} />
          </div>
          <div className={styles.name_container}>
            <div className={styles.passengerRedirect} onClick={onClickUser}>
              <h4>
                {t("format.name", {
                  name: UserTrip.username,
                  surname: UserTrip.surname,
                })}
              </h4>
            </div>
            <span style={{ color: "gray", fontStyle: "italic" }}>
              {t("format.date", {
                date: getFormattedDateTime(passanger.startDateTime).date,
              })}
            </span>
          </div>
        </div>
      )}
      {isLoading || UserTrip === undefined ? (
        <LoadingWheel description={t("trip_detail.passengers.loading")} />
      ) : (
        <div className={styles.right_container}>
          {UserTrip.passengerRating && UserTrip.passengerRating > 0 ? (
            <StarRating rating={UserTrip.passengerRating} />
          ) : (
            <p className={styles.propValue}>{t("profile.props.no_rating")}</p>
          )}
          <div className={styles.info_passanger_style}>
            <div className={styles.btn_container}>
              <Button
                className={styles.btn_accept}
                disabled={
                  passanger.passengerState != PassangerStatus.PENDING ||
                  fullSeats ||
                  statusPassanger != reserveStatus.NOT_STARTED
                }
                onClick={() => onSubmitAccept(passanger.selfUri)}
              >
                <div className={styles.create_trip_btn}>
                  <span>{t("trip_detail.btn.accept")}</span>
                </div>
              </Button>
              <Button
                className={styles.btn_delete}
                disabled={passanger.passengerState != PassangerStatus.PENDING}
                onClick={() => onSubmitReject(passanger.selfUri)}
              >
                <div className={styles.create_trip_btn}>
                  <span>{t("trip_detail.btn.reject")}</span>
                </div>
              </Button>
            </div>
            {passanger.passengerState === PassangerStatus.ACCEPTED ? (
              <span style={{ color: "gray", fontStyle: "italic" }}>
                {t("trip_detail.passengers.passsanger_accepted")}
              </span>
            ) : passanger.passengerState === PassangerStatus.REJECTED ? (
              <span style={{ color: "gray", fontStyle: "italic" }}>
                {t("trip_detail.passengers.passsanger_rejected")}
              </span>
            ) : fullSeats ? (
              <span style={{ color: "gray", fontStyle: "italic" }}>
                {t("trip_detail.passengers.fullSeats")}
              </span>
            ) : statusPassanger === reserveStatus.STARTED ? (
              <span style={{ color: "gray", fontStyle: "italic" }}>
                {t("trip_detail.status.started_review")}
              </span>
            ) : (
              statusPassanger === reserveStatus.FINISHED && (
                <span style={{ color: "gray", fontStyle: "italic" }}>
                  {t("trip_detail.status.finished_review")}
                </span>
              )
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default PassangerComponent;
