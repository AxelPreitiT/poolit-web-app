import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import styles from "@/pages/admin/styles.module.scss";
import { useTranslation } from "react-i18next";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import StarRating from "@/components/stars/StarsRanking.tsx";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip.tsx";
import ReportReason from "@/components/reportReason/ReportReason.tsx";
import useTripByUri from "@/hooks/trips/useTripByUri.tsx";
import useReportById from "@/hooks/admin/useReportById.tsx";
import { useParams } from "react-router-dom";
import LoadingScreen from "@/components/loading/LoadingScreen";
import LoadingWheel from "@/components/loading/LoadingWheel";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri";
import {useState} from "react";
import {Button, Modal} from "react-bootstrap";
import ModalReportAccept from "@/components/admin/DecideReportModal/ModalReportAccept.tsx";
import ReportApproveForm from "@/components/admin/DecideReportModal/ReportApproveForm.tsx";
import ModalReportReject from "@/components/admin/DecideReportModal/ModalReportReject.tsx";
import ReportRejectForm from "@/components/admin/DecideReportModal/ReportRejectForm.tsx";

//TODO rating y cantidad de reportes en los usuarios. Como traducir reportOptions. Como obtener el Report.

const ReportPage = () => {
  const { t } = useTranslation();
  const params = useParams();
  const {
    isLoading: isReportLoading,
    report: report,
    isError: isReportError,
  } = useReportById(params.id);
  const {
    isLoading: isUserReporterLoading,
    user: userReporter,
    isError: isUserReporterError,
  } = usePublicUserByUri(report?.reportedUri);
  const {
    isLoading: isUserReportedLoading,
    user: userReported,
    isError: isUserReportedError,
  } = usePublicUserByUri(report?.reportedUri);
  const {
    isLoading: isTripLoading,
    trip,
    isError: isTripError,
  } = useTripByUri(report?.tripUri);

  const [showModalReportReject, setModalReportReject] = useState(false);
  const [showModalReportApprove, setModalReportApprove] = useState(false);
  const openModalReportApprove = () => {setModalReportApprove(true);};
  const closeModalReportApprove = () => {setModalReportApprove(false);};
  const openModalReportReject = () => {setModalReportReject(true);};
  const closeModalReportReject = () => {setModalReportReject(false);};

  if (
    isReportLoading ||
    isUserReporterLoading ||
    isUserReportedLoading ||
    isTripLoading ||
    isReportError ||
    isUserReportedError ||
    isUserReporterError ||
    isTripError ||
    !report ||
    !trip ||
    !userReporter ||
    !userReported
  ) {
    return <LoadingScreen description={t("admin.report.loading")} />;
  }

  return (
    <MainComponent>
      <MainHeader title={t("admin.report.detail")} />
      <div className={styles.container_tab}>
        <div>
          <div className={styles.report_content_container2}>
            <div className={styles.report_users_content_container}>
              <div
                id="users-image-container"
                className={styles.users_container}
              >
                <div className={styles.user_container}>
                  <a href={userReporter.selfUri}>
                    <CircleImg src={userReporter.imageUri} size={70} />
                  </a>
                </div>
                <div className={styles.report_arrow_container}>
                  <div className={styles.report_arrow_content}>
                    <div className={styles.report_arrow_text}>
                      <i className="bi bi-megaphone-fill secondary-color h3"></i>
                      <h3 className={styles.secondary_color}>
                        <span>{t("admin.report.reported")}</span>
                      </h3>
                    </div>
                    <div
                      className={styles.report_arrow_draw}
                      id="report-arrow-draw"
                    >
                      <div
                        className={styles.report_arrow_line}
                        id="report-arrow-line"
                      ></div>
                      <div
                        className={styles.report_arrow_head}
                        id="report-arrow-head"
                      ></div>
                    </div>
                  </div>
                </div>
                <div className={styles.user_container}>
                  <a href={userReported.selfUri}>
                    <CircleImg src={userReported.imageUri} size={70} />
                  </a>
                </div>
              </div>
              <div id="users-container" className={styles.users_container}>
                <div className={styles.user_container}>
                  <div className={styles.user_container_item}>
                    <a href={userReporter.selfUri}>
                      <h3 className={styles.secondary_color}>
                        {t("format.name", {
                          name: userReporter.username,
                          surname: userReporter.surname,
                        })}
                      </h3>
                    </a>
                    <h6 className="italic-text">
                      {report.relation == "PASSENGER_2_DRIVER" ||
                      report.relation == "PASSENGER_2_PASSENGER" ? (
                        <span> {t("admin.report.passenger")}</span>
                      ) : (
                        <span> {t("admin.report.driver")}</span>
                      )}
                    </h6>
                  </div>

                  {report.relation == "PASSENGER_2_DRIVER" ||
                  report.relation == "PASSENGER_2_PASSENGER" ? (
                    <StarRating
                      rating={userReporter.passengerRating}
                      className="h3"
                    />
                  ) : (
                    <StarRating
                      rating={userReporter.driverRating}
                      className="h3"
                    />
                  )}
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.published")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.received")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.approved")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.rejected")}</span>
                    </strong>
                  </div>
                </div>
                <div className={styles.user_container}>
                  <div className={styles.user_container_item}>
                    <a href={userReported.selfUri}>
                      <h3 className={styles.secondary_color}>
                        {t("format.name", {
                          name: userReported.username,
                          surname: userReported.surname,
                        })}
                      </h3>
                    </a>
                    <h6 className="italic-text">
                      {report.relation == "DRIVER_2_PASSENGER" ||
                      report.relation == "PASSENGER_2_PASSENGER" ? (
                        <span> {t("admin.report.passenger")}</span>
                      ) : (
                        <span> {t("admin.report.driver")}</span>
                      )}
                    </h6>
                  </div>
                  {report.relation == "DriverToPassanger" ||
                  report.relation == "PassangerToPassanger" ? (
                    <StarRating
                      rating={userReported.passengerRating}
                      className="h3"
                    />
                  ) : (
                    <StarRating
                      rating={userReported.driverRating}
                      className="h3"
                    />
                  )}
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.published")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.received")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.approved")}</span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item + styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.rejected")}</span>
                    </strong>
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.report_content_container}>
              <div className={styles.content_container_header}>
                <h3 className={styles.secondary_color}>
                  <span> {t("admin.report.trip")}</span>
                </h3>
                <hr className={styles.secondary_color} />
              </div>
              <div className={styles.content_container_info}>
                {trip == undefined || isTripLoading || isTripError ? (
                  <LoadingWheel description={t("trip.loading")} />
                ) : (
                  <CardTrip trip={trip} />
                )}
              </div>
              <div className={styles.report_content_container}>
                <div className={styles.content_container_header}>
                  <h3 className={styles.secondary_color}>
                    <span> {t("admin.report.reason")}</span>
                  </h3>
                  <hr className={styles.secondary_color} />
                </div>
                <div className={styles.report_content_container_bubble}>
                  <div className={styles.reporter_image}>
                    <CircleImg src={userReporter.imageUri} size={70} />

                    <span className="italic-text">{userReporter.username}</span>
                  </div>
                  <div className={styles.bubble}>
                    <div className={styles.report_info}>
                      <h4 className={styles.secondary_color}>
                        <ReportReason reason={report.reportOption} />
                      </h4>
                      <span className="text">{report.description}</span>
                    </div>
                    <div className={styles.report_date}>
                      <span className="italic-text">
                        {t("format.date", {
                          date: report.dateTime,
                        })}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              <div className={styles.report_content_container}>
                <div className={styles.decision_content_container}>
                  <h6 className={styles.secondary_color}>{t('admin.report.decision')}</h6>
                  <div className={styles.button_container}>
                    //TODO como hacer href
                    <a href="/admin/">
                      <Button variant="primary" className="later-btn">
                        <span className="light-text h5">{t('admin.report.laterBtn')}</span>
                      </Button>
                    </a>
                    <div className={styles.reject_container}>
                      <Button variant="danger" className="danger-btn" onClick={openModalReportReject}>
                        <span className="light-text h5">{t('admin.report.rejectBtn')}</span>
                      </Button>
                    </div>
                    <div className={styles.approve_container}>
                      <Button variant="secondary" className="secondary-btn" onClick={openModalReportApprove}>
                        <span className="light-text h5">{t('admin.report.approveBtn')}</span>
                      </Button>
                    </div>
                  </div>
                </div>
              </div>
              <Modal show={showModalReportApprove} onHide={closeModalReportApprove} aria-labelledby="contained-modal-title-vcenter" centered>
                <ModalReportAccept closeModal={closeModalReportApprove} reportProcessForm={<ReportApproveForm/>}/>
              </Modal>

              <Modal show={showModalReportReject} onHide={closeModalReportReject} aria-labelledby="contained-modal-title-vcenter" centered>
                <ModalReportReject closeModal={closeModalReportReject} reportProcessForm={<ReportRejectForm/>}/>
              </Modal>
            </div>
          </div>
        </div>
      </div>
    </MainComponent>
  );
};

export default ReportPage;
