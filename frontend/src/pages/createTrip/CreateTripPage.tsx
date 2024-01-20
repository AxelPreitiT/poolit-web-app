import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import CreateTripForm from "./CreateTripForm";
import useGetAllCities from "@/hooks/cities/useGetAllCities";
import useGetUserCars from "@/hooks/cars/useGetUserCars";

const CreateTripPage = () => {
  const { t } = useTranslation();
  const { isLoading: isLoadingCities, cities } = useGetAllCities();
  const { isLoading: isLoadingUserCars, cars } = useGetUserCars();

  // Todo: Loading spinner
  if (isLoadingCities || isLoadingUserCars) {
    return <div>Loading...</div>;
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
