import LoadingWheel from "./LoadingWheel";
import styles from "./styles.module.scss";

interface LoadingScreenProps {
  description?: string;
}

const LoadingScreen = ({ description }: LoadingScreenProps) => (
  <LoadingWheel
    containerClassName={styles.screen}
    iconClassName={styles.icon}
    descriptionClassName={styles.description}
    description={description}
  />
);

export default LoadingScreen;
