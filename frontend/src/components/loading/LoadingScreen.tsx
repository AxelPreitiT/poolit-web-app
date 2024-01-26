import LoadingWheel from "./LoadingWheel";
import styles from "./styles.module.scss";

const LoadingScreen = () => (
  <LoadingWheel
    containerClassName={styles.screen}
    iconClassName={styles.icon}
    descriptionClassName={styles.description}
  />
);

export default LoadingScreen;
