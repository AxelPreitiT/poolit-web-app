import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button, Modal} from "react-bootstrap";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import PassangerModel from "@/models/PassangerModel.ts";
import LoadingWheel from "../../loading/LoadingWheel.tsx";
import useReviewsReportPassangers from "@/hooks/reportReview/useReviewsPassangers.tsx";
import {useState} from "react";
import ModalMakeReportReview from "@/components/TripDetails/ModalReportsReviews/ModalMakeReportReview.tsx";
import ReportForm from "@/components/TripDetails/ModalsForms/ReportForm.tsx";
import ReviewForm from "@/components/TripDetails/ModalsForms/ReviewForm.tsx";

export interface PassangerReportReviewComponent {
  data: PassangerModel;
  extraData?: {
    reporting: boolean;
    closeModal: () => void;
  };
}

const PassangerReportReviewComponent = ({
  data: passanger,
  extraData: extraData,
}: PassangerReportReviewComponent) => {
  const { t } = useTranslation();
  const { isLoading, data } = usePublicUserByUri(passanger.userUri);
  const {data:isReviewed, isLoading:isLoadingReview} = useReviewsReportPassangers(passanger, extraData?.reporting)

  const buttonStyle = {
    backgroundColor: isReviewed ? "green" : "orange",
  };


  const [showModalMakeReview, setModalMakeReview] = useState(false);

  const openModalMakeReview = () => {
    setModalMakeReview(true);
    };

  const closeModalMakeReview = () => {setModalMakeReview(false);};


  const [showModalMakeReport, setModalMakeReport] = useState(false);
  const openModalMakeReport = () => {
    setModalMakeReport(true);
    if (extraData && extraData.closeModal && typeof extraData.closeModal === 'function') {
      extraData.closeModal();
    }
  };
  const closeModalMakeReport = () => {setModalMakeReport(false);};

  return (
    <div className={styles.marginCointainer}>
      {isLoading || data == undefined || isLoadingReview ? (
        <LoadingWheel description={t("admin.user.loading")} />
      ) : (
          <div>
        <Button
          onClick={() => extraData?.reporting ? openModalMakeReport() : openModalMakeReview()}
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
       {isReviewed && !extraData?.reporting &&
           <div className={styles.aclaration_text}>
              <span>Pasajero reseñado</span>
           </div>}
        {isReviewed && extraData?.reporting &&
            <div className={styles.aclaration_text}>
              <span>Pasajero reportado</span>
            </div>}

            <Modal show={showModalMakeReport} onHide={closeModalMakeReport} aria-labelledby="contained-modal-title-vcenter" centered>
              <ModalMakeReportReview closeModal={closeModalMakeReport} user={data} reportForm={<ReportForm/>}/>
            </Modal>

            <Modal show={showModalMakeReview} onHide={closeModalMakeReview} aria-labelledby="contained-modal-title-vcenter" centered>
              <ModalMakeReportReview closeModal={closeModalMakeReview} user={data} reportForm={<ReviewForm/>}/>
            </Modal>

          </div>)}
    </div>
  );
};

export default PassangerReportReviewComponent;
