import styles from "./styles.module.scss";
import { useParams } from "react-router-dom";
import MainComponent from "@/components/utils/MainComponent";
import MainHeader from "@/components/utils/MainHeader";
import { useTranslation } from "react-i18next";
import Location from "@/components/location/Location";
import CircleImg from "@/components/img/circleImg/CircleImg";
import StarRating from "@/components/stars/StarsRanking";
import StatusComponent from "@/components/statusTrip/StatusTrip";

const TripDetailsPage = () => {
  const { t } = useTranslation();
  const { tripId } = useParams<{ tripId: string }>();

  return (
    <div>
      <MainComponent>
        <MainHeader
          title={t("created_trips.title")}
          left_component={
            <StatusComponent
              text={t("trip_detail.status.accepted")}
              icon={"bi bi-clock-history"}
              color={"success"}
            />
          }
        />
        <Location
          start_address="hola"
          start_city="hola"
          end_address="hola"
          end_city="hola"
        />
        <div className={styles.middle_content}>
          <div className={styles.info_trip}>
            <div className={styles.show_row}>
              <i className="bi bi-clock light-text"></i>
              <div className={styles.info_details}>
                <span className="light-text detail">16:00</span>
              </div>
            </div>
            <div className={styles.show_row}>
              <i className="bi bi-calendar light-text"></i>
              <div className={styles.info_details}>
                <span className="light-text detail">FRIDAY</span>
                <span className={styles.subtitle_info}>10/9/2014</span>
              </div>
            </div>
            <div className={styles.show_row}>
              <CircleImg
                src="http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"
                size={40}
              />
              <div className={styles.info_details}>
                <span className="light-text detail">FRIDAY</span>
                <StarRating rating={3.5} color={"#ffffff"} size="10" />
              </div>
            </div>
            <div className={styles.show_row}>
              <i className="bi bi-people light-text"></i>
              <div className={styles.info_details}>
                <span className="light-text detail">16:00</span>
              </div>
            </div>
            <hr className="white" />
            <div className={styles.show_row}>
              <CircleImg
                src="http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"
                size={40}
              />
              <div className={styles.info_details}>
                <span className="light-text detail">FRIDAY</span>
                <StarRating rating={3.5} color={"#ffffff"} size="10" />
              </div>
            </div>
            <div className={styles.show_row}>
              <i className="bi bi-envelope-fill light-text"></i>
              <div className={styles.info_details}>
                <span className="light-text detail">16:00</span>
              </div>
            </div>
            <div className={styles.show_row}>
              <i className="bi bi-telephone-fill light-text"></i>
              <div className={styles.info_details}>
                <span className="light-text detail">16:00</span>
              </div>
            </div>
          </div>
          <div className={styles.img_container}>
            <img
              src="http://pawserver.it.itba.edu.ar/paw-2023a-07/image/80"
              className={styles.img_style}
              alt=""
            />
          </div>
        </div>

        <h1>Trip {tripId}</h1>
      </MainComponent>
      <MainComponent>
        <MainHeader title={t("created_trips.title")} />
        <h1>Trip {tripId}</h1>
      </MainComponent>
    </div>
  );
};

export default TripDetailsPage;
