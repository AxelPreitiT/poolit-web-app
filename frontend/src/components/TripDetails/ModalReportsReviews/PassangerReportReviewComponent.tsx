import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { Button } from "react-bootstrap";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import LoadingWheel from "../../loading/LoadingWheel.tsx";
import useReviewsReportPassangers from "@/hooks/reportReview/useReviewsPassangers.tsx";
import getStatusPassanger from "@/functions/getStatusPassanger.tsx";
import ReserveStatus from "@/enums/ReserveStatus.ts";
import IMake from "../IMake.ts";
import ImageService from "@/services/ImageService.ts";

export interface PassangerReportReviewComponent {
  data: PassangerModel;
  extraData?: {
    reporting: boolean;
    openModalMake: (make: IMake) => void;
  };
}

const PassangerReportReviewComponent = ({
  data: passanger,
  extraData,
}: PassangerReportReviewComponent) => {
  const { t } = useTranslation();
  const { isLoading, data } = usePublicUserByUri(passanger.userUri);
  const { data: isReviewed, isLoading: isLoadingReview } =
    useReviewsReportPassangers(passanger, extraData?.reporting);

  const statusPassanger = getStatusPassanger(passanger);

  const buttonClassName = isReviewed
    ? styles.userContainerReady
    : extraData?.reporting
    ? styles.userContainerReport
    : styles.userContainerReview;

  return (
    <div className={styles.marginCointainer}>
      {isLoading || !data || isLoadingReview || !extraData ? (
        <LoadingWheel description={t("admin.user.loading")} />
      ) : (
        <div>
          <Button
            onClick={() =>
              extraData.openModalMake({
                user: data,
                isReport: extraData.reporting,
                isDriver: false,
              })
            }
            disabled={
              isReviewed || statusPassanger === ReserveStatus.NOT_STARTED
            }
            className={buttonClassName}
          >
            <CircleImg src={ImageService.getSmallImageUrl(data.imageUri)} size={50} />
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
                      initial_date: getFormattedDateTime(
                        passanger.startDateTime
                      ).date,
                      final_date: getFormattedDateTime(passanger.endDateTime)
                        .date,
                    })}
              </span>
            </div>
          </Button>
          {isReviewed && extraData?.reporting && (
            <div className={styles.aclaration_text}>
              <span>{t("trip_detail.review.user_reported")}</span>
            </div>
          )}
          {isReviewed && !extraData?.reporting && (
            <div className={styles.aclaration_text}>
              <span>{t("trip_detail.review.user_reviewed")}</span>
            </div>
          )}
          {statusPassanger === ReserveStatus.NOT_STARTED && (
            <div className={styles.aclaration_text}>
              <span>{t("trip_detail.review.not_started_review")}</span>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default PassangerReportReviewComponent;
