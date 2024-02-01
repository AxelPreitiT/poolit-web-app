import PrivateReportModel from "@/models/PrivateReportModel";
import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { useTranslation } from "react-i18next";
import LoadingWheel from "../loading/LoadingWheel";
import ReportReason from "@/components/reportReason/ReportReason.tsx";
import extractPathAfterApi from "@/functions/extractPathAfterApi.ts";
import { Link } from "react-router-dom";
import UsePrivateUserByUri from "@/hooks/users/usePrivateUserByUri.tsx";
import getFormattedDateTime from "@/functions/DateFormat.ts";

const ShortInfoReport = (report: PrivateReportModel) => {
  const { t } = useTranslation();
  const {
    isLoading: isLoadingUserReporter,
    user: userReporter,
    isError: isErrorUserReporter,
  } = UsePrivateUserByUri(report.reporterUri);
  const {
    isLoading: isLoadingUserReported,
    user: userReported,
    isError: isErrorUserReported,
  } = UsePrivateUserByUri(report.reportedUri);

  if (
    isLoadingUserReporter ||
    isLoadingUserReported ||
    isErrorUserReporter ||
    isErrorUserReported
  ) {
    return <LoadingWheel description={t("admin.report.loading")} />;
  }

  return (
    <Link
        to={extractPathAfterApi(report.selfUri)}
        className={styles.text_black}
    >
      <div>
        <div className={styles.row_report}>
          <div className={styles.profiles_info}>
            {!userReporter ? (
              <LoadingWheel description={t("admin.user.loading")} />
            ) : (
              <div className={styles.info_profile_img}>
                <div>
                  <CircleImg src={userReporter.imageUri} size={70} />

                </div>
                <div className={styles.short_info_profile}>
                  <div className={styles.inline_text}>
                    <h4>
                      {t("format.name", {
                        name: userReporter.username,
                        surname: userReporter.surname,
                      })}
                    </h4>
                  </div>
                  <h6 className="italic-text">
                    {report.relation == "PASSENGER_2_DRIVER" ||
                    report.relation == "PASSENGER_2_PASSENGER" ? (
                      <span> {t("admin.report.passenger")}</span>
                    ) : (
                      <span> {t("admin.report.driver")}</span>
                    )}
                  </h6>
                </div>
              </div>
            )}
            <div className={styles.secondary_color}>
              <i className="bi bi-megaphone-fill secondary-color h1 "></i>
            </div>
            {!userReported ? (
              <LoadingWheel description={t("admin.user.loading")} />
            ) : (
              <div className={styles.info_profile_img_right}>
                <div className={styles.short_info_profile_right}>
                  <div className={styles.inline_text}>
                    <h4>
                      {t("format.name", {
                        name: userReported.username,
                        surname: userReported.surname,
                      })}
                    </h4>
                  </div>
                  <h6 className="italic-text">
                    {report.relation == "DRIVER_2_PASSENGER" ||
                    report.relation == "PASSENGER_2_PASSENGER" ? (
                      <span> {t("admin.report.passenger")}</span>
                    ) : (
                      <span> {t("admin.report.driver")}</span>
                    )}
                  </h6>
                </div>
                <div>
                  <CircleImg src={userReported.imageUri} size={70} />
                  {/*<img src={"/"} alt="user image" className={styles.info_profile_img_right} />*/}
                </div>
              </div>
            )}
          </div>
          <div className={styles.trip_short_info}>
            <h4>
              <span className="secondary-color italic-text">
                <h4 className={styles.reason_container}>
                  <span className={styles.reason_title}> {t("admin.reason.title")}</span>
                  <span><ReportReason reason={report.reportOption} /></span>
                </h4>
              </span>
            </h4>
            <h5>
              <span>{t("admin.date")}</span>
              <span>{getFormattedDateTime(report.dateTime).date}</span>
            </h5>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default ShortInfoReport;
