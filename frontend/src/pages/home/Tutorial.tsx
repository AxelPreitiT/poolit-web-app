import HomeTutorialOne from "@/images/home-tutorial-one.jpg";
import HomeTutorialTwo from "@/images/home-tutorial-two.jpg";
import HomeTutorialThree from "@/images/home-tutorial-three.jpg";
import styles from "./styles.module.scss";
import { Image } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const Tutorial = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.tutorialContainer}>
      <div className={styles.tutorialRow}>
        <div className={styles.tutorialText}>
          <p className={styles.tutorialTitle}>{t("home.tutorial.one.title")}</p>
          <span className={styles.tutorialDescription}>
            {t("home.tutorial.one.description")}
            <span className="secondary-text ms-1 fw-semibold">
              {t("home.tutorial.one.recurrent_trips")}
            </span>
            .
          </span>
        </div>
        <div className={styles.tutorialImage}>
          <Image src={HomeTutorialOne} alt={t("home.tutorial.one.alt")} />
        </div>
      </div>
      <hr className="secondary-text" />
      <div className={styles.tutorialRow}>
        <div className={styles.tutorialText}>
          <p className={styles.tutorialTitle}>{t("home.tutorial.two.title")}</p>
          <p className={styles.tutorialDescription}>
            {t("home.tutorial.two.description")}
          </p>
        </div>
        <div className={styles.tutorialImage}>
          <Image src={HomeTutorialTwo} alt={t("home.tutorial.two.alt")} />
        </div>
      </div>
      <hr className="secondary-text" />
      <div className={styles.tutorialRow}>
        <div className={styles.tutorialText}>
          <p className={styles.tutorialTitle}>
            {t("home.tutorial.three.title")}
          </p>
          <p className={styles.tutorialDescription}>
            {t("home.tutorial.three.description")}
          </p>
        </div>
        <div className={styles.tutorialImage}>
          <Image src={HomeTutorialThree} alt={t("home.tutorial.three.alt")} />
        </div>
      </div>
    </div>
  );
};

export default Tutorial;
