import { useTranslation } from "react-i18next";
import { FaCarSide } from "react-icons/fa";
import styles from "./styles.module.scss";

const NoResultsSearch = () => {
  const { t } = useTranslation();
  return (
    <div className={styles.noResultsContainer}>
      <div className={styles.noResultsHeader}>
        <FaCarSide className="secondary-text" />
        <span>{t("search.no_results.title")}</span>
      </div>
      <div className={styles.noResultsBody}>
        <span>{t("search.no_results.body")}</span>
      </div>
    </div>
  );
};

export default NoResultsSearch;
