import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import LoginForm from "./LoginForm";
import { registerPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";

const LoginPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.primaryContainer}>
        <LoginForm />
        <div className={styles.primaryFooterContainer}>
          <hr className="light-text" />
          <h5 className="light-text">{t("register.not_registered")}</h5>
          <Link to={registerPath}>
            <h5 className="link-text fw-bold">{t("register.register")}</h5>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
