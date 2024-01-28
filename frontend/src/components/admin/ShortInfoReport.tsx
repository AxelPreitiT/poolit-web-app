import PrivateReportModel from "@/models/PrivateReportModel";
import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import { useEffect, useState } from "react";
import UserService from "@/services/UserService.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import { useTranslation } from "react-i18next";
import LoadingWheel from "../loading/LoadingWheel";
import ReportReason from "@/components/reportReason/ReportReason.tsx";


const ShortInfoReport = (report: PrivateReportModel) => {
  const { t } = useTranslation();
  const [UserReporter, setUserReporter] = useState<UserPublicModel | null>(
    null
  );
  const [UserReported, setUserReported] = useState<UserPublicModel | null>(
    null
  );

  useEffect(() => {
    UserService.getUserById(report.reporterUri).then((response) => {
      setUserReporter(response);
    });
    UserService.getUserById(report.reportedUri).then((response) => {
      setUserReported(response);
    });
  });

  if (!report) {
    return null;
  }

  return (
    <div>
      <div className={styles.row_report}>
        <div className={styles.profiles_info}>
          {UserReporter === null ? (
            <LoadingWheel description={t("admin.user.loading")} />
          ) : (
            <div className={styles.info_profile_img}>
              <div>
                <CircleImg src={UserReporter.imageUri} size={70} />
                {/* <img src={"/"} alt="user image" className={styles.image_photo_admin} /> */}
              </div>
              <div className={styles.short_info_profile}>
                <div className={styles.inline_text}>
                  <h4>
                    {t("format.name", {
                      name: UserReporter.username,
                      surname: UserReporter.surname,
                    })}
                  </h4>
                  <span>{report.description}</span>
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
          {UserReported === null ? (
            <LoadingWheel description={t("admin.user.loading")} />
          ) : (
            <div className={styles.info_profile_img_right}>
              <div className={styles.short_info_profile_right}>
                <div className={styles.inline_text}>
                  <h4>
                    {t("format.name", {
                      name: UserReported.username,
                      surname: UserReported.surname,
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
                <CircleImg src={UserReported.imageUri} size={70} />
                {/*<img src={"/"} alt="user image" className={styles.info_profile_img_right} />*/}
              </div>
            </div>
          )}
        </div>
        <div className={styles.trip_short_info}>
          <h4>
            <span className="secondary-color italic-text">
              <h4><ReportReason reason={report.reportOption}/></h4>
            </span>
          </h4>
          <h5>
            {t("format.date", {
              date: report.dateTime,
            })}
          </h5>
        </div>
      </div>
    </div>
  );
};

export default ShortInfoReport;
