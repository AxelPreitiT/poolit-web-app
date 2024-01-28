import CarFeaturesPills from "@/components/car/CarFeaturesPills/CarFeaturesPills";
import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import propStyles from "../prop/styles.module.scss";

interface CarFeaturesProp {
  prop: string;
  carFeatures: CarFeatureModel[];
}

const CarFeaturesProp = ({ prop, carFeatures }: CarFeaturesProp) => {
  if (!carFeatures || carFeatures.length === 0) {
    return null;
  }

  return (
    <div>
      <p className={propStyles.propHeader}>{prop}</p>
      <CarFeaturesPills
        carFeatures={carFeatures}
        viewOnly={true}
        activePillClassName={styles.activePill}
      />
    </div>
  );
};

export default CarFeaturesProp;
