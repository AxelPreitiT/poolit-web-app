import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import CreateTripForm from "./CreateTripForm";
import useAllCities from "@/hooks/cities/useAllCities";
import useUserCars from "@/hooks/cars/useUserCars";
import LoadingScreen from "@/components/loading/LoadingScreen";

const CreateTripPage = () => {
  const { t } = useTranslation();
  const { isLoading: isLoadingCities, cities } = useAllCities();
  const { isLoading: isLoadingUserCars, cars } = useUserCars();

  if (isLoadingCities || isLoadingUserCars) {
    return <LoadingScreen />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.containerHeader}>
        <h1 className="secondary-text">{t("create_trip.title")}</h1>
        <hr className="secondary-text" />
      </div>
      <div>
        <CreateTripForm cities={cities} cars={cars} />
      </div>
    </div>
  );
};

export default CreateTripPage;
