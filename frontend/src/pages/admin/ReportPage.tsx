import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import styles from "@/pages/admin/styles.module.scss";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import UserPublicModel from "@/models/UserPublicModel.ts";
import UserService from "@/services/UserService.ts";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import StarRating from "@/components/stars/StarsRanking.tsx";
import CardTrip from "@/components/cardTrip/cardTrip/CardTrip.tsx";
import ReportReason from "@/components/reportReason/ReportReason.tsx";
import useTripByUri from "@/hooks/trips/useTripByUri.tsx";
import useReportById from "@/hooks/admin/useReportById.tsx";
import { useParams } from "react-router-dom";
import LoadingScreen from "@/components/loading/LoadingScreen";
import LoadingWheel from "@/components/loading/LoadingWheel";

//TODO rating y cantidad de reportes en los usuarios. Como traducir reportOptions. Como obtener el Report.

const ReportPage = () => {
  const { t } = useTranslation();
  const params = useParams();
  const { isLoading: isReportLoading, report: report } = useReportById(
    params.id
  );
  const [UserReporter, setUserReporter] = useState<UserPublicModel | null>(
    null
  );
  const [UserReported, setUserReported] = useState<UserPublicModel | null>(
    null
  );
  const {
    isLoading: isTripLoading,
    trip,
    isError: isTripError,
  } = useTripByUri(report?.tripUri);

  useEffect(() => {
    if (!report) {
      return;
    }
    UserService.getUserById(report.reporterUri).then((response) => {
      setUserReporter(response);
    });
    UserService.getUserById(report.reportedUri).then((response) => {
      setUserReported(response);
    });
  });

  return (
    <MainComponent>
      <MainHeader title={t("admin.report.detail")} />
      <div className={styles.container_tab}>
        <div>
          {report == undefined ||
          UserReporter == undefined ||
          UserReported == undefined ||
          isReportLoading ? (
            <LoadingScreen description={t("admin.report.loading_one")} />
          ) : (
            <div className={styles.report_content_container2}>
              <div className={styles.report_users_content_container}>
                <div
                  id="users-image-container"
                  className={styles.users_container}
                >
                  <div className={styles.user_container}>
                    <a href={UserReporter.selfUri}>
                      <CircleImg src={UserReporter.imageUri} size={70} />
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
                    <a href={UserReported.selfUri}>
                      <CircleImg src={UserReported.imageUri} size={70} />
                    </a>
                  </div>
                </div>
                <div id="users-container" className={styles.users_container}>
                  <div className={styles.user_container}>
                    <div className={styles.user_container_item}>
                      <a href={UserReporter.selfUri}>
                        <h3 className={styles.secondary_color}>
                          {t("format.name", {
                            name: UserReporter.username,
                            surname: UserReporter.surname,
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
                        rating={UserReporter.passengerRating}
                        className="h3"
                      />
                    ) : (
                      <StarRating
                        rating={UserReporter.driverRating}
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
                      <a href={UserReported.selfUri}>
                        <h3 className={styles.secondary_color}>
                          {t("format.name", {
                            name: UserReported.username,
                            surname: UserReported.surname,
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
                        rating={UserReported.passengerRating}
                        className="h3"
                      />
                    ) : (
                      <StarRating
                        rating={UserReported.driverRating}
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
                      <CircleImg src={UserReporter.imageUri} size={70} />

                      <span className="italic-text">
                        {UserReporter.username}
                      </span>
                    </div>
                    <div className={styles.bubble}>
                      <div className={styles.report_info}>
                        <h4 className={styles.secondary_color}>
                          <ReportReason reason={report.reportOption}/>
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

                <span>Poner botones</span>
              </div>
            </div>
          )}
        </div>
      </div>
    </MainComponent>
  );
};

export default ReportPage;
