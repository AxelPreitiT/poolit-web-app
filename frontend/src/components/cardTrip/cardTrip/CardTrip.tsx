import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import TripModel from "@/models/TripModel.ts";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { getDayString } from "@/utils/date/dayString.ts";
import useCityByUri from "@/hooks/cities/useCityByUri";
import useCarByUri from "@/hooks/cars/useCarByUri";
import tripModel from "@/models/TripModel.ts";
import getTotalTrips from "@/functions/getTotalTrips.ts";
import StarRating from "@/components/stars/StarsRanking.tsx";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import { tripDetailsPath } from "@/AppRouter.tsx";
import ImageService from "@/services/ImageService.ts";

const CardTrip = ({
  trip,
  className,
  extraData,
  searchParams,
}: {
  trip: TripModel;
  className?: string;
  extraData?: (trip: tripModel) => {
    startDate: string;
    endDate: string;
    link: string;
  };
  searchParams?: URLSearchParams;
}) => {
  const { t } = useTranslation();
  const {
    startDate: start,
    endDate: end,
    link,
  } = extraData
    ? extraData(trip)
    : {
        startDate: trip.startDateTime,
        endDate: trip.endDateTime,
        link: tripDetailsPath.replace(":tripId", trip.tripId.toString()),
      };
  const date = new Date(trip.startDateTime);
  const totalTrips = getTotalTrips(new Date(start), new Date(end));
  const { isLoading: isLoadingDriver, user: driver } = usePublicUserByUri(
    trip?.driverUri
  );
  const linkTrip = link + (searchParams ? `?${searchParams.toString()}` : "");

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
    isCarError ||
    isLoadingDriver ||
    driver === undefined
  ) {
    return <LoadingWheel description={t("trip.loading_one")} />;
  }
  const totalPrice = totalTrips * trip.pricePerTrip;
  return (
    <Link to={linkTrip} className={styles.link_container + " " + className}>
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
              <div className={styles.raiting_container}>
                <div className={styles.one_raiting}>
                  <StarRating
                    rating={car.rating}
                    className={styles.rating}
                    containerClassName={styles.rating_container}
                  />
                  <CircleImg
                    src={ImageService.getSmallImageUrl(car.imageUri)}
                    size={20}
                    className={styles.img}
                  />
                </div>
                <div className={styles.one_raiting}>
                  <StarRating
                    rating={driver.driverRating}
                    className={styles.rating}
                    containerClassName={styles.rating_container}
                  />
                  <CircleImg
                    src={ImageService.getSmallImageUrl(driver.imageUri)}
                    size={20}
                    className={styles.img}
                  />
                </div>
              </div>
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
            <div className={styles.footer_details_container}>
              <div className={styles.footer_details}>
                <i className="bi bi-calendar text"></i>
                {start != end ? (
                  <span>
                    {t(`day.full.${getDayString(date).toLowerCase()}`, {
                      plural: "s",
                    })}
                  </span>
                ) : (
                  <span>{getFormattedDateTime(start).date}</span>
                )}
              </div>
              <div className={styles.footer_details}>
                <i className="bi bi-clock text"></i>
                <span>{getFormattedDateTime(trip.startDateTime).time}</span>
              </div>
            </div>
            <span className={styles.price}>
              {t("format.price", {
                priceInt: totalPrice,
              })}
            </span>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default CardTrip;
