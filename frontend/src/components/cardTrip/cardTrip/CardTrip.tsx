import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Trip } from "@/types/Trip";
import { Link } from "react-router-dom";
import { homePath } from "@/AppRouter";

const CardTrip = ({ Trip }: { Trip: Trip }) => {
  const { t } = useTranslation();

  return (
    <Link to={homePath} className={styles.link_container}>
      <div className={styles.card_container}>
        <div className={styles.left_container}>
          <img
            src={"http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"}
            className={styles.img_container}
          />
        </div>
        <div className={styles.right_container}>
          <div className={styles.address_container}>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>Agronomía</h4>
                <h6>Lavarden 315</h6>
              </div>
            </div>
            <div className={styles.vertical_dotted_line}></div>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt-fill secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>Agronomía</h4>
                <h6>Independencia 2135</h6>
              </div>
            </div>
          </div>
          <div className={styles.footer_description}>
            <div className={styles.footer_details}>
              <i className="bi bi-calendar text"></i>
              {Trip.queryIsRecurrent ? (
                <span>{Trip.dayOfWeekString}</span>
              ) : (
                <span>{Trip.startDateString}</span>
              )}
            </div>
            <div className={styles.footer_details}>
              <i className="bi bi-clock text"></i>
              <span>{Trip.startTimeString}</span>
            </div>
            <h3>
              {t("format.price", {
                priceInt: Trip.integerQueryTotalPrice,
                princeFloat: Trip.decimalQueryTotalPrice,
              })}
            </h3>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default CardTrip;
