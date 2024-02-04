import PoolitLogo from "@/images/poolit.svg";
import styles from "./styles.module.scss";
import { Image } from "react-bootstrap";
import useAuthentication from "@/hooks/auth/useAuthentication";
import Tutorial from "./Tutorial";
import { useCurrentUser } from "@/hooks/users/useCurrentUser";
import useRecommendedTrips from "@/hooks/trips/useRecommendedTrips";
import RecommendedTripsList from "./RecommendedTripsList";
import { useTranslation } from "react-i18next";
import TripsSearch from "@/components/search/TripsSearch/TripsSearch";
import useAllCities from "@/hooks/cities/useAllCities";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import useSearchTripsForm from "@/hooks/forms/useSearchTripsForm";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import { searchPath } from "@/AppRouter";
import { createTripsSearchParams } from "@/functions/tripsSearchParams";
import { useNavigate } from "react-router-dom";
import LoadingScreen from "@/components/loading/LoadingScreen";

const HomePage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isAuthenticated = useAuthentication();
  const { isLoading: isLoadingCurrentUser, currentUser } = useCurrentUser();
  const { isLoading: isLoadingRecommendedTrips, recommendedTrips } =
    useRecommendedTrips(currentUser);
  const { isLoading: isLoadingCities, cities } = useAllCities();
  const { isLoading: isLoadingCarFeatures, carFeatures } = useCarFeatures();

  const onSearchSuccess = ({ data }: { data: SearchTripsFormSchemaType }) => {
    const searchParams = createTripsSearchParams(data);
    navigate(`${searchPath}?${searchParams}`);
  };
  const searchForm = useSearchTripsForm({
    onSuccess: onSearchSuccess,
  });

  if (
    (isAuthenticated && isLoadingCurrentUser) ||
    (currentUser && isLoadingRecommendedTrips) ||
    isLoadingCities ||
    isLoadingCarFeatures
  ) {
    return <LoadingScreen />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.banner}>
        <div className={styles.bannerContainer}>
          <div className={styles.textContainer}>
            <div className={styles.title}>
              <p>{t("home.title.travel_with")}</p>
              <Image src={PoolitLogo} alt="Poolit" />
              <p>{t("home.title.exclamation")}</p>
            </div>
            <div className={styles.subtitle}>
              <div className={styles.subtitleRow}>
                <span>
                  {t("home.subtitle.with")}
                  <span className="secondary-text mx-1">
                    {t("poolit.text")}
                  </span>
                  {t("home.subtitle.description")}
                </span>
              </div>
              <div className={styles.subtitleRow}>
                <span>{t("home.subtitle.join")}</span>
              </div>
            </div>
          </div>
          <div className={styles.searchContainer}>
            <TripsSearch
              cities={cities}
              carFeatures={carFeatures}
              searchForm={searchForm}
            />
          </div>
        </div>
      </div>
      {recommendedTrips && recommendedTrips.length > 0 ? (
        <RecommendedTripsList recommendedTrips={recommendedTrips} />
      ) : (
        <Tutorial />
      )}
    </div>
  );
};

export default HomePage;
