import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { Button } from "react-bootstrap";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import LoadingWheel from "../../loading/LoadingWheel.tsx";
import useReviewsReportPassangers from "@/hooks/reportReview/useReviewsPassangers.tsx";

export interface PassangerReportReviewComponent {
  data: PassangerModel;
  extraData?: boolean;
}

const PassangerReportReviewComponent = ({
  data: passanger,
  extraData: reporting,
}: PassangerReportReviewComponent) => {
  const { t } = useTranslation();
  const { isLoading, data } = usePublicUserByUri(passanger.userUri);
  const {data:isReviewed, isLoading:isLoadingReview} = useReviewsReportPassangers(passanger, reporting)

  const buttonStyle = {
    backgroundColor: isReviewed ? "green" : "orange",
  };

  return (
    <div className={styles.marginCointainer}>
      {isLoading || data == undefined || isLoadingReview ? (
        <LoadingWheel description={t("admin.user.loading")} />
      ) : (
          <div>
        <Button
          onClick={() => console.log("hola")}
          style={buttonStyle}
          disabled={isReviewed}
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
       {isReviewed && !reporting &&
           <div className={styles.aclaration_text}>
              <span>Pasajero reseñado</span>
           </div>}
        {isReviewed && reporting &&
            <div className={styles.aclaration_text}>
              <span>Pasajero reportado</span>
            </div>}
          </div>
        )}
    </div>
  );
};

export default PassangerReportReviewComponent;
