import CarModel from "@/models/CarModel";
import { Image, ImageProps } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface CarImageProps {
  car?: CarModel;
  className?: string;
}

const CarImage = ({ car, className, ...props }: CarImageProps & ImageProps) => {
  const { t } = useTranslation();

  if (!car) {
    return null;
  }

  return (
    <Image
      {...props}
      className={className}
      src={car.imageUri}
      alt={t("car.image", { carInfo: car.infoCar })}
      fluid
    />
  );
};

export default CarImage;
