import { homePath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import PoolitLogo from "@/images/poolit.svg";
import styles from "./styles.module.scss";

const RedirectHomeLogo = () => {
  const { t } = useTranslation();

  return (
    <Link to={homePath} className={styles.logoContainer}>
      <img src={PoolitLogo} alt={t("poolit.logo")} className={styles.logoImg} />
    </Link>
  );
};

export default RedirectHomeLogo;
