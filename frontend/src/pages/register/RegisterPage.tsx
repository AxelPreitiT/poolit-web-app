import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import RegisterBanner from "@/images/register-banner.jpg";
import { Link } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";
import RegisterForm from "./RegisterForm";
import useAllCities from "@/hooks/cities/useAllCities";
import LoadingScreen from "@/components/loading/LoadingScreen";

const RegisterPage = () => {
  const { isLoading, data: cities } = useAllCities();
  const { t } = useTranslation();

  if (isLoading) {
    return <LoadingScreen />;
  }

  return (
    <div className={styles.mainContainer}>
      <div className={styles.bannerContainer}>
        <img
          src={RegisterBanner}
          alt={t("register.banner")}
          className={styles.banner}
        />
        <div className={styles.logoContainer}>
          <RedirectHomeLogo />
        </div>
      </div>
      <div className={styles.formContainer}>
        <RegisterForm cities={cities} />
        <div className={styles.footerContainer}>
          <hr className="light-text mt-auto" />
          <div className={styles.loginContainer}>
            <h5 className={styles.title + " light-text"}>
              {t("register.already_registered")}
            </h5>
            <Link to={loginPath}>
              <h5 className="link-text fw-bold">{t("login.login")}</h5>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
