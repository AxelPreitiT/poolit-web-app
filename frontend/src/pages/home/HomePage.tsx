import PoolitLogo from "@/images/poolit.svg";
import styles from "./styles.module.scss";
import { Image } from "react-bootstrap";
import useAuthentication from "@/hooks/auth/useAuthentication";
import Tutorial from "./Tutorial";
import { useCurrentUser } from "@/hooks/users/useCurrentUser";
import useRecommendedTrips from "@/hooks/trips/useRecommendedTrips";
import RecommendedTripsList from "./RecommendedTripsList";

const HomePage = () => {
  const { isLoading: isLoadingAuth, isAuthenticated } = useAuthentication();
  const { isLoading: isLoadingCurrentUser, currentUser } = useCurrentUser({
    enabled: isAuthenticated,
  });
  const { isLoading: isLoadingRecommendedTrips, recommendedTrips } =
    useRecommendedTrips(currentUser);

  // Todo: Create loading screen
  if (isLoadingAuth || isLoadingCurrentUser || isLoadingRecommendedTrips) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.banner}>
        <div className={styles.bannerContainer}>
          <div className={styles.textContainer}>
            <div className={styles.title}>
              <p>¡Viaja con</p>
              <Image src={PoolitLogo} alt="Poolit" />
              <p>!</p>
            </div>
            <div className={styles.subtitle}>
              <div className={styles.subtitleRow}>
                <span>
                  Con
                  <span className="secondary-text mx-1">Poolit</span>
                  podrás compartir tus viajes con otros usuarios que tengan el
                  mismo destino, reduciendo los costos de transporte, el tráfico
                  y la emisión de gases contaminantes. Además, podrás conocer a
                  nuevas personas y hacer conexiones mientras viajas.
                </span>
              </div>
              <div className={styles.subtitleRow}>
                <p>
                  ¡Unite a la comunidad y comenzá a viajar de manera
                  inteligente!
                </p>
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
