import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import TripModel from "@/models/TripModel.ts";
import { Link } from "react-router-dom";
import LoadingWheel from "@/components/loading/LoadingWheel";
import useCityByUri from "@/hooks/cities/useCityByUri";
import useCarByUri from "@/hooks/cars/useCarByUri";
import {tripDetailsPath} from "@/AppRouter.tsx";
import getTotalTrips from "@/functions/getTotalTrips.ts";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import useRolePassanger from "@/hooks/passanger/useRolePassanger.tsx";

const CardTripProfile = (trip: TripModel) => {
  const { t } = useTranslation();
  const {currentUser} =  useCurrentUser();
  const isDriver = trip.driverUri==currentUser?.selfUri
  const {
    isLoading: isLoadingRole,
    currentPassanger: currentPassanger,
  } = useRolePassanger(isDriver, trip?.passengersUriTemplate);

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
    isCarError || isLoadingRole) {
    return <LoadingWheel description={t("trip.loading_one")} />;
  }

  const startDateTimeValue = isDriver ? trip.startDateTime : currentPassanger?.startDateTime || "";
  const endDateTimeValue = isDriver? trip.endDateTime : currentPassanger?.endDateTime || "";
  const totalTrips = getTotalTrips(new Date(startDateTimeValue), new Date(endDateTimeValue))

  return (
    <Link
      to={tripDetailsPath.replace(":id", trip.tripId.toString())}
      className={styles.link_container}
    >
      <div className={styles.card_info}>
        <div className={styles.data_container}>
          <div className={styles.route_container}>
            <i className="bi bi-geo-alt"></i>
            <div className={styles.horizontal_dotted_line}></div>
            <i className="bi bi-geo-alt-fill"></i>
          </div>
          <div className={styles.address_container}>
            <div className={styles.route_info_text}>
              <h3>{originCity?.name}</h3>
              <span className="text">{trip.originAddress}</span>
            </div>
            <div className={styles.route_info_text}>
              <h3>{destinationCity?.name}</h3>
              <span style={{ textAlign: "right" }}>
                {trip.destinationAddress}
              </span>
            </div>
          </div>
          <div className={styles.extra_info_container}>
            <div className={styles.calendar_container}>
              <i className="bi bi-calendar text"></i>
              {totalTrips == 1 ? (
                <div className={styles.format_date}>
                  <span className="text">PONER DIA</span>
                  <span className={styles.date_text}>
                    {startDateTimeValue}
                  </span>
                </div>
              ) : (
                <div className={styles.format_date}>
                  <span className="text">PONER DIA</span>
                  <span className={styles.date_text}>
                    {t("format.recurrent_date", {
                      initial_date: startDateTimeValue,
                      final_date: endDateTimeValue,
                    })}
                  </span>
                </div>
              )}
            </div>
            <div className={styles.calendar_container}>
              <i className="bi bi-clock"></i>
              <span>{getFormattedDateTime(trip.startDateTime).time}</span>
            </div>
            <div>
              <h2 className={styles.price_format}>
                {t("format.price", {
                  priceInt: trip.pricePerTrip})}
              </h2>
            </div>
          </div>
        </div>
        <div className={styles.img_container}>
          {!car ? (
            <LoadingWheel
              description={t("car.loading")}
              containerClassName={styles.loadingContainer}
              iconClassName={styles.loadingIcon}
              descriptionClassName={styles.loadingDescription}
            />
          ) : (
            <img className={styles.car_container} src={car.imageUri} />
          )}
        </div>
      </div>
    </Link>
  );
};

export default CardTripProfile;
