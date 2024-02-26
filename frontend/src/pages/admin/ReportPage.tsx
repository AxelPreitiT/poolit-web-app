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
import { Link, useNavigate, useParams } from "react-router-dom";
import LoadingScreen from "@/components/loading/LoadingScreen";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { useState } from "react";
import { Button, Modal } from "react-bootstrap";
import ModalReportAccept from "@/components/admin/DecideReportModal/ModalReportAccept.tsx";
import ModalReportReject from "@/components/admin/DecideReportModal/ModalReportReject.tsx";
import { adminPath, publicProfilePath } from "@/AppRouter.tsx";
import UsePrivateUserByUri from "@/hooks/users/usePrivateUserByUri.tsx";
import getFormattedDateTime from "@/functions/DateFormat.ts";

const ReportPage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
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
  } = UsePrivateUserByUri(report?.reporterUri);
  const {
    isLoading: isUserReportedLoading,
    user: userReported,
    isError: isUserReportedError,
  } = UsePrivateUserByUri(report?.reportedUri);
  const {
    isLoading: isTripLoading,
    trip,
    isError: isTripError,
  } = useTripByUri(report?.tripUri);

  const doNotShowJoinBtnSearchParams = new URLSearchParams();
  doNotShowJoinBtnSearchParams.set("join", "false");

  const [showModalReportReject, setModalReportReject] = useState(false);
  const [showModalReportApprove, setModalReportApprove] = useState(false);
  const openModalReportApprove = () => {
    setModalReportApprove(true);
  };
  const closeModalReportApprove = () => {
    setModalReportApprove(false);
  };
  const openModalReportReject = () => {
    setModalReportReject(true);
  };
  const closeModalReportReject = () => {
    setModalReportReject(false);
  };

  const onSuccess = () => {
    navigate(adminPath);
  };

  if (
    isReportLoading ||
    isUserReporterLoading ||
    isUserReportedLoading ||
    isReportError ||
    isUserReportedError ||
    isUserReporterError ||
    isTripError ||
    !report ||
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
                className={
                  styles.users_content_container + " " + styles.users_container
                }
              >
                <div className={styles.user_container}>
                  <Link
                    to={publicProfilePath.replace(
                      ":id",
                      String(userReporter.userId)
                    )}
                  >
                    <CircleImg
                      src={userReporter.imageUri}
                      size={70}
                      className="ms-5"
                    />
                  </Link>
                </div>
                <div className={styles.report_arrow_container}>
                  <div className={styles.report_arrow_content}>
                    <div className={styles.report_arrow_text}>
                      <i
                        className={
                          styles.secondary_color + " bi bi-megaphone-fill h3 "
                        }
                      ></i>
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
                  <Link
                    to={publicProfilePath.replace(
                      ":id",
                      String(userReported.userId)
                    )}
                  >
                    <CircleImg
                      src={userReported.imageUri}
                      size={70}
                      className="me-5"
                    />
                  </Link>
                </div>
              </div>
              <div
                id="users-container"
                className={styles.users_stats_container}
              >
                <div className={styles.user_stats_container}>
                  <div className={styles.item_container}>
                    <Link
                      to={publicProfilePath.replace(
                        ":id",
                        String(userReporter.userId)
                      )}
                    >
                      <h3 className={styles.secondary_color}>
                        {t("format.name", {
                          name: userReporter.username,
                          surname: userReporter.surname,
                        })}
                      </h3>
                    </Link>
                    <h6 className="italic-text">
                      {report.relation === "PASSENGER_2_DRIVER" ||
                      report.relation === "PASSENGER_2_PASSENGER" ? (
                        <span> {t("admin.report.passenger")}</span>
                      ) : (
                        <span> {t("admin.report.driver")}</span>
                      )}
                    </h6>
                  </div>

                  {report.relation === "PASSENGER_2_DRIVER" ||
                  report.relation === "PASSENGER_2_PASSENGER" ? (
                    <div className={styles.item_container}>
                      <strong>{t("admin.report.passengerRating")}</strong>
                      <StarRating
                        rating={userReporter.passengerRating}
                        className="h3 secondary-text"
                      />
                    </div>
                  ) : (
                    <div className={styles.item_container}>
                      <strong>{t("admin.report.driverRating")}</strong>
                      <StarRating
                        rating={userReporter.driverRating}
                        className="h3 secondary-text"
                      />
                    </div>
                  )}
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.published")}</span>
                      <span className={styles.secondary_color}>
                        {userReporter.reportsPublished}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.received")}</span>
                      <span className={styles.secondary_color}>
                        {userReporter.reportsReceived}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.approved")}</span>
                      <span className={styles.secondary_color}>
                        {userReporter.reportsApproved}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span>{t("admin.report.rejected")}</span>
                      <span className={styles.secondary_color}>
                        {" "}
                        {userReporter.reportsRejected}{" "}
                      </span>
                    </strong>
                  </div>
                </div>
                <div className={styles.user_stats_container}>
                  <div className={styles.item_container}>
                    <Link
                      to={publicProfilePath.replace(
                        ":id",
                        String(userReported.userId)
                      )}
                      className={styles.link_container}
                    >
                      <h3 className={styles.secondary_color}>
                        {t("format.name", {
                          name: userReported.username,
                          surname: userReported.surname,
                        })}
                      </h3>
                    </Link>
                    <h6 className="italic-text">
                      {report.relation === "DRIVER_2_PASSENGER" ||
                      report.relation === "PASSENGER_2_PASSENGER" ? (
                        <span> {t("admin.report.passenger")}</span>
                      ) : (
                        <span> {t("admin.report.driver")}</span>
                      )}
                    </h6>
                  </div>
                  {report.relation === "DRIVER_2_PASSENGER" ||
                  report.relation === "PASSENGER_2_PASSENGER" ? (
                    <div className={styles.item_container}>
                      <strong>{t("admin.report.passengerRating")}</strong>
                      <StarRating
                        rating={userReported.passengerRating}
                        className="h3 secondary-text"
                      />
                    </div>
                  ) : (
                    <div className={styles.item_container}>
                      <strong>{t("admin.report.driverRating")}</strong>
                      <StarRating
                        rating={userReported.driverRating}
                        className="h3 secondary-text"
                      />
                    </div>
                  )}
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.published")}</span>
                      <span className={styles.secondary_color}>
                        {" "}
                        {userReported.reportsPublished}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.received")}</span>
                      <span className={styles.secondary_color}>
                        {" "}
                        {userReported.reportsReceived}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.approved")}</span>
                      <span className={styles.secondary_color}>
                        {" "}
                        {userReported.reportsApproved}{" "}
                      </span>
                    </strong>
                  </div>
                  <div
                    className={
                      styles.user_container_item +
                      " " +
                      styles.user_container_row
                    }
                  >
                    <strong>
                      <span> {t("admin.report.rejected")}</span>
                      <span className={styles.secondary_color}>
                        {userReported.reportsRejected}{" "}
                      </span>
                    </strong>
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.report_content_container}>
              <div className={styles.content_container_header}>
                <span className={styles.secondary_color}>
                  {t("admin.report.trip")}
                </span>
                <hr className={styles.secondary_color} />
              </div>
              <div className={styles.content_container_info}>
                {trip === undefined || isTripLoading || isTripError ? (
                  <LoadingWheel description={t("trip.loading")} />
                ) : (
                  <CardTrip
                    trip={trip}
                    className="w-100"
                    searchParams={doNotShowJoinBtnSearchParams}
                  />
                )}
              </div>
              <div className={styles.report_content_container}>
                <div className={styles.content_container_header}>
                  <span className={styles.secondary_color}>
                    {t("admin.report.reason")}
                  </span>
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
                      <span>{getFormattedDateTime(report.dateTime).date}</span>
                      <span>{getFormattedDateTime(report.dateTime).time}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div className={styles.report_content_container}>
                <div className={styles.decision_content_container}>
                  <h5 className={styles.secondary_color}>
                    {t("admin.report.decision")}
                  </h5>
                  <div className={styles.button_container}>
                    <Link to={adminPath}>
                      <Button variant="primary" className="later-btn">
                        <span className="light-text h5">
                          {t("admin.report.laterBtn")}
                        </span>
                      </Button>
                    </Link>
                    <div className={styles.reject_container}>
                      <Button
                        variant="danger"
                        className="danger-btn"
                        onClick={openModalReportReject}
                      >
                        <span className="light-text h5">
                          {t("admin.report.rejectBtn")}
                        </span>
                      </Button>
                    </div>
                    <div className={styles.approve_container}>
                      <Button
                        variant="secondary"
                        className="secondary-btn"
                        onClick={openModalReportApprove}
                      >
                        <span className="light-text h5">
                          {t("admin.report.approveBtn")}
                        </span>
                      </Button>
                    </div>
                  </div>
                </div>
              </div>
              <Modal
                show={showModalReportApprove}
                onHide={closeModalReportApprove}
                aria-labelledby="contained-modal-title-vcenter"
                centered
              >
                <ModalReportAccept
                  onSuccess={onSuccess}
                  closeModal={closeModalReportApprove}
                  report={report}
                />
              </Modal>

              <Modal
                show={showModalReportReject}
                onHide={closeModalReportReject}
                aria-labelledby="contained-modal-title-vcenter"
                centered
              >
                <ModalReportReject
                  closeModal={closeModalReportReject}
                  onSuccess={onSuccess}
                  report={report}
                />
              </Modal>
            </div>
          </div>
        </div>
      </div>
    </MainComponent>
  );
};

export default ReportPage;
