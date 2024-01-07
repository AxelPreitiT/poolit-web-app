import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import LoginForm from "./LoginForm";
import { registerPath, verifyAccountPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";

const LoginPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.loginContainer}>
        <LoginForm />
        <div className={styles.loginFooterContainer}>
          <hr className="light-text" />
          <h4 className="light-text">{t("register.not_registered")}</h4>
          <Link to={registerPath}>
            <h5 className="link-text fw-bold">{t("register.register")}</h5>
          </Link>
        </div>
      </div>
      <div className={styles.footerContainer}>
        <h6>
          {t("verify_account.request")}
          <Link to={verifyAccountPath}>
            <h6 className="link-text fw-bold">{t("verify_account.here")}</h6>
          </Link>
        </h6>
      </div>
    </div>
  );
};

export default LoginPage;
