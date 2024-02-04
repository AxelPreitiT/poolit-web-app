import LoadingScreen from "@/components/loading/LoadingScreen";
import useCarBrands from "@/hooks/cars/useCarBrands";
import useCarFeatures from "@/hooks/cars/useCarFeatures";
import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import CreateCarForm from "./CreateCarForm";

const CreateCarPage = () => {
  const { t } = useTranslation();
  const { isLoading: isCarFeaturesLoading, carFeatures } = useCarFeatures();
  const { isLoading: isCarBrandsLoading, carBrands } = useCarBrands();

  if (isCarFeaturesLoading || isCarBrandsLoading) {
    return <LoadingScreen />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.containerHeader}>
        <h1 className="secondary-text">{t("create_car.title")}</h1>
        <hr className="secondary-text" />
      </div>
      <div>
        <CreateCarForm carFeatures={carFeatures} carBrands={carBrands} />
      </div>
    </div>
  );
};

export default CreateCarPage;
