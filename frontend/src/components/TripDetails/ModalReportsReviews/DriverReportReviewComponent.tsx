import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import userPublicModel from "@/models/UserPublicModel.ts";
import tripModel from "@/models/TripModel.ts";
import useReviewsReportsDriver from "@/hooks/reportReview/useReviewsReportsDriver.tsx";
import IMake from "../IMake";
import ImageService from "@/services/ImageService.ts";

export interface DriverReportReviewComponentProps {
  driver: userPublicModel;
  reporting: boolean;
  trip: tripModel;
  openModalMake: (make: IMake) => void;
}

const DriverReportReviewComponent = ({
  driver,
  reporting,
  trip,
  openModalMake,
}: DriverReportReviewComponentProps) => {
  const { t } = useTranslation();

  const { data: isReviewed, isLoading: isLoadingReview } =
    useReviewsReportsDriver(reporting, trip);

  const buttonClassName = isReviewed
    ? styles.userContainerReady
    : reporting
    ? styles.userContainerReport
    : styles.userContainerReview;

  return (
    !isLoadingReview && (
      <div className={styles.marginCointainer}>
        <Button
          onClick={() =>
            openModalMake({ user: driver, isReport: reporting, isDriver: true })
          }
          disabled={isReviewed}
          className={buttonClassName}
        >
          <CircleImg src={ImageService.getSmallImageUrl(driver.imageUri)} size={50} />
          <div className={styles.infoContainer}>
            <h4>
              {t("format.name", {
                name: driver.username,
                surname: driver.surname,
              })}
            </h4>
          </div>
        </Button>
        {isReviewed && reporting && (
          <div className={styles.aclaration_text}>
            <span>{t("trip_detail.review.driver_reported")}</span>
          </div>
        )}
        {isReviewed && !reporting && (
          <div className={styles.aclaration_text}>
            <span>{t("trip_detail.review.driver_reviewed")}</span>
          </div>
        )}
      </div>
    )
  );
};

export default DriverReportReviewComponent;
