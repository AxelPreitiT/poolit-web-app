import { useTranslation } from "react-i18next";
import { GiCarWheel } from "react-icons/gi";
import styles from "./styles.module.scss";

interface LoadingWheelProps {
  containerClassName?: string;
  iconClassName?: string;
  descriptionClassName?: string;
  description?: string;
}

const LoadingWheel = ({
  containerClassName,
  iconClassName,
  description,
  descriptionClassName,
}: LoadingWheelProps) => {
  const { t } = useTranslation();
  const defaultDescription = t("loading.default");

  return (
    <div className={styles.container + " " + containerClassName}>
      <GiCarWheel className={styles.rotate + " " + iconClassName} />
      <span className={styles.ellipsis + " " + descriptionClassName}>
        {description || defaultDescription}
      </span>
    </div>
  );
};

export default LoadingWheel;
