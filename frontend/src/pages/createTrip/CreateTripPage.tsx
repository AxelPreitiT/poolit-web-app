import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import CreateTripForm from "./CreateTripForm";

const CreateTripPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.containerHeader}>
        <h1 className="secondary-text">{t("create_trip.title")}</h1>
        <hr className="secondary-text" />
      </div>
      <div>
        <CreateTripForm />
      </div>
    </div>
  );
};

export default CreateTripPage;
