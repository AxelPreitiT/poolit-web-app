import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import { useState } from "react";

interface CarFeaturesPillsProps {
  initialSelectedCarFeatures?: string[];
  carFeatures: CarFeatureModel[];
  onSelect?: (selectedCarFeatures: string[]) => void;
  pillClassName?: string;
  activePillClassName?: string;
  viewOnly?: boolean;
}

const CarFeaturesPills = ({
  carFeatures,
  onSelect,
  initialSelectedCarFeatures,
  pillClassName,
  activePillClassName,
  viewOnly = false,
}: CarFeaturesPillsProps) => {
  const [selectedCarFeatures, setSelectedCarFeatures] = useState<string[]>(
    viewOnly
      ? carFeatures.map((carFeatures) => carFeatures.name)
      : initialSelectedCarFeatures || []
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

  const getPillClassName = (carFeatureId: string) => {
    let className =
      selectedCarFeatures.includes(carFeatureId) || viewOnly
        ? styles.activePill + " " + activePillClassName
        : styles.pill + " " + pillClassName;
    if (viewOnly) {
      className += " " + styles.disabledPill;
    }
    return className;
  };

  return (
    <div className={styles.mainContainer}>
      {carFeatures.map((carFeature) => (
        <div
          key={carFeature.id}
          onClick={() => (viewOnly ? null : toggleCarFeature(carFeature.id))}
        >
          <label className={getPillClassName(carFeature.id)}>
            {carFeature.name}
          </label>
        </div>
      ))}
    </div>
  );
};

export default CarFeaturesPills;
