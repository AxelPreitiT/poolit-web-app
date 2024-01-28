import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import extractPathAfterApi from "@/functions/extractPathAfterApi";
import TripModel from "@/models/TripModel.ts";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { getDayString } from "@/utils/date/dayString.ts";
import useCityByUri from "@/hooks/cities/useCityByUri";
import useCarByUri from "@/hooks/cars/useCarByUri";

const CardTrip = ({
  trip,
  className,
}: {
  trip: TripModel;
  className?: string;
}) => {
  const { t } = useTranslation();
  const date = new Date(trip.startDateTime);

  const {
    isLoading: isOriginCityLoading,
    city: originCity,
    isError: isOriginCityError,
  } = useCityByUri(trip.originCityUri);
  const {
    isLoading: isDestinationCityLoading,
    city: destinationCity,
    isError: isDestinationCityError,
  } = useCityByUri(trip.destinationCityUri);
  const {
    isLoading: isCarLoading,
    car,
    isError: isCarError,
  } = useCarByUri(trip.carUri);

  if (
    isOriginCityLoading ||
    isDestinationCityLoading ||
    isCarLoading ||
    isOriginCityError ||
    isDestinationCityError ||
    isCarError
  ) {
    return <LoadingWheel description={t("trip.loading_one")} />;
  }

  return (
    <Link
      to={extractPathAfterApi(trip.selfUri)}
      className={styles.link_container + " " + className}
    >
      <div className={styles.card_container}>
        <div className={styles.left_container}>
          {!car ? (
            <LoadingWheel
              description={t("car.loading")}
              containerClassName={styles.loadingContainer}
            />
          ) : (
            <div className={styles.img_container}>
              <img src={car.imageUri} />
            </div>
          )}
        </div>
        <div className={styles.right_container}>
          <div className={styles.address_container}>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>{originCity?.name}</h4>
                <h6>{trip.originAddress}</h6>
              </div>
            </div>
            <div className={styles.vertical_dotted_line}></div>
            <div className={styles.route_info_row}>
              <i className="bi bi-geo-alt-fill secondary-color route-info-icon h4"></i>
              <div className={styles.route_info_text}>
                <h4>{destinationCity?.name}</h4>
                <h6>{trip.destinationAddress}</h6>
              </div>
            </div>
          </div>
          <div className={styles.footer_description}>
            <div className={styles.footer_details}>
              <i className="bi bi-calendar text"></i>
              {trip.totalTrips > 1 ? (
                <span>
                  {t(`day.full.${getDayString(date).toLowerCase()}`, {
                    plural: "s",
                  })}
                </span>
              ) : (
                <span>{getFormattedDateTime(trip.startDateTime).date}</span>
              )}
            </div>
            <div className={styles.footer_details}>
              <i className="bi bi-clock text"></i>
              <span>{getFormattedDateTime(trip.startDateTime).time}</span>
            </div>
            <h3>
              {t("format.price", {
                priceInt: trip.pricePerTrip,
              })}
            </h3>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default CardTrip;
