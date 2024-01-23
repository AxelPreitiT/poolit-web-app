import { FaCarSide } from "react-icons/fa";
import styles from "./styles.module.scss";
import TripsSearch from "@/components/search/TripsSearch";
import useAllCities from "@/hooks/cities/useAllCities";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import { useTranslation } from "react-i18next";

const SearchPage = () => {
  const { t } = useTranslation();
  const { isLoading: isCitiesLoading, cities } = useAllCities();
  const { isLoading: isCarFeaturesLoading, carFeatures } = useCarFeatures();

  // TODO: Add loading screen
  if (isCitiesLoading || isCarFeaturesLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.searchContainer}>
        <TripsSearch cities={cities} carFeatures={carFeatures} />
      </div>
      <div className={styles.contentContainer}>
        <div className={styles.noResultsContainer}>
          <div className={styles.noResultsHeader}>
            <FaCarSide className="secondary-text" />
            <span>{t("search.no_results.title")}</span>
          </div>
          <div className={styles.noResultsBody}>
            <span>{t("search.no_results.body")}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
