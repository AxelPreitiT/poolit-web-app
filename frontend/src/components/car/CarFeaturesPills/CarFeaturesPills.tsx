import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import { useState } from "react";

interface CarFeaturesPillsProps {
  initialSelectedCarFeatures?: string[];
  carFeatures: CarFeatureModel[];
  onSelect?: (selectedCarFeatures: string[]) => void;
  pillClassName?: string;
  activePillClassName?: string;
}

const CarFeaturesPills = ({
  carFeatures,
  onSelect,
  initialSelectedCarFeatures,
  pillClassName,
  activePillClassName,
}: CarFeaturesPillsProps) => {
  const [selectedCarFeatures, setSelectedCarFeatures] = useState<string[]>(
    initialSelectedCarFeatures || []
  );

  const toggleCarFeature = (carFeatureId: string) => {
    let newSelectedCarFeatures: string[] = [];
    if (selectedCarFeatures.includes(carFeatureId)) {
      newSelectedCarFeatures = selectedCarFeatures.filter(
        (selectedCarFeature) => selectedCarFeature !== carFeatureId
      );
    } else {
      newSelectedCarFeatures = [...selectedCarFeatures, carFeatureId];
    }
    setSelectedCarFeatures(newSelectedCarFeatures);
    onSelect && onSelect(newSelectedCarFeatures);
  };

  return (
    <div className={styles.mainContainer}>
      {carFeatures.map((carFeature) => (
        <div
          key={carFeature.id}
          onClick={() => toggleCarFeature(carFeature.id)}
        >
          <label
            className={
              selectedCarFeatures.includes(carFeature.id)
                ? styles.activePill + " " + activePillClassName
                : styles.pill + " " + pillClassName
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
