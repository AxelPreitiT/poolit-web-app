import CarFeaturesPills from "@/components/car/CarFeaturesPills/CarFeaturesPills";
import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import ProfilePropHeader from "../prop/ProfilePropHeader";

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
      <ProfilePropHeader prop={prop} />
      <CarFeaturesPills
        carFeatures={carFeatures}
        viewOnly={true}
        activePillClassName={styles.activePill}
      />
    </div>
  );
};

export default CarFeaturesProp;
