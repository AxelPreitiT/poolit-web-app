import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import { useState } from "react";

interface CarFeaturesPillsProps {
  carFeatures: CarFeatureModel[];
}

const CarFeaturesPills = ({ carFeatures }: CarFeaturesPillsProps) => {
  const [selectedCarFeatures, setSelectedCarFeatures] = useState<string[]>([]);

  const toggleCarFeature = (carFeatureId: string) =>
    setSelectedCarFeatures((prevSelectedCarFeatures: string[]) => {
      if (prevSelectedCarFeatures.includes(carFeatureId)) {
        return prevSelectedCarFeatures.filter(
          (selectedCarFeature) => selectedCarFeature !== carFeatureId
        );
      } else {
        return [...prevSelectedCarFeatures, carFeatureId];
      }
    });

  return (
    <div className={styles.mainContainer}>
      {carFeatures.map((carFeature) => (
        <div
          key={carFeature.id}
          className={styles.pillContainer}
          onClick={() => toggleCarFeature(carFeature.id)}
        >
          <label
            className={
              selectedCarFeatures.includes(carFeature.id)
                ? styles.activePill
                : styles.pill
            }
          >
            {carFeature.name}
          </label>
        </div>
      ))}
    </div>
  );
};

export default CarFeaturesPills;
