import PoolitLogo from "@/images/poolit.svg";
import styles from "./styles.module.scss";
import { Image } from "react-bootstrap";
import useAuthentication from "@/hooks/auth/useAuthentication";
import Tutorial from "./Tutorial";
import { useCurrentUser } from "@/hooks/users/useCurrentUser";
import useRecommendedTrips from "@/hooks/trips/useRecommendedTrips";
import RecommendedTripsList from "./RecommendedTripsList";
import { useTranslation } from "react-i18next";

const HomePage = () => {
  const { t } = useTranslation();
  const { isLoading: isLoadingAuth, isAuthenticated } = useAuthentication();
  const { isLoading: isLoadingCurrentUser, currentUser } = useCurrentUser({
    enabled: isAuthenticated,
  });
  const { isLoading: isLoadingRecommendedTrips, recommendedTrips } =
    useRecommendedTrips(currentUser);

  // Todo: Create loading screen
  if (
    isLoadingAuth ||
    (isAuthenticated && isLoadingCurrentUser) ||
    (currentUser && isLoadingRecommendedTrips)
  ) {
    return <div>Loading...</div>;
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
                <p>{t("home.subtitle.join")}</p>
              </div>
            </div>
          </div>
          <div className={styles.searchContainer}></div>
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
