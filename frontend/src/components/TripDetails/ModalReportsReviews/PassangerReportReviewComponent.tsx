import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { Button } from "react-bootstrap";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import userPublicModel from "@/models/UserPublicModel.ts";
import LoadingWheel from "../../loading/LoadingWheel.tsx";

export interface PassangerReportReviewComponent {
  passanger: PassangerModel;
  selectPassanger: (user: userPublicModel) => void;
}

const PassangerReportReviewComponent = ({
  passanger,
  selectPassanger,
}: PassangerReportReviewComponent) => {
  const { isLoading, data } = usePublicUserByUri(passanger.userUri);
  const { t } = useTranslation();

  return (
    <div className={styles.marginCointainer}>
      {isLoading || data == undefined ? (
        <LoadingWheel description={t("admin.user.loading")} />
      ) : (
        <Button
          onClick={() => selectPassanger(data)}
          className={styles.userContainer}
        >
          <CircleImg src={data.imageUri} size={50} />
          <div className={styles.infoContainer}>
            <h4>
              {t("format.name", {
                name: data.username,
                surname: data.surname,
              })}
            </h4>
            <span className={styles.spanStyle}>
              <i className="bi bi-calendar light-text"> </i>
              {passanger.startDateTime === passanger.endDateTime
                ? getFormattedDateTime(passanger.startDateTime).date
                : t("format.recurrent_date", {
                    initial_date: getFormattedDateTime(passanger.startDateTime)
                      .date,
                    final_date: getFormattedDateTime(passanger.endDateTime)
                      .date,
                  })}
            </span>
          </div>
        </Button>
      )}
    </div>
  );
};

export default PassangerReportReviewComponent;
