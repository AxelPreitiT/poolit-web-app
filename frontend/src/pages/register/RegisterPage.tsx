import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import RegisterBanner from "@/images/register-banner.jpg";
import { Link } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";

const RegisterPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.bannerContainer}>
        <img src={RegisterBanner} alt={t("register.banner")} />
        <div className={styles.logoContainer}>
          <RedirectHomeLogo />
        </div>
      </div>
      <div className={styles.formContainer}>
        <form className={styles.form}>
          <div className={styles.userPicContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.profile_image")}
            </h4>
            <div className={styles.profilePic}>
              <i className="bi bi-person-circle light-text"></i>
            </div>
          </div>
          <hr className="light-text" />
          <div className={styles.userInfoContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.personal_info")}
            </h4>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <input
                  type="text"
                  id="name"
                  className="form-control"
                  placeholder={t("register.name")}
                />
              </div>
              <div className={styles.inputItem}>
                <input
                  type="text"
                  id="lastname"
                  className="form-control"
                  placeholder={t("register.last_name")}
                />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <input
                  type="email"
                  id="email"
                  className="form-control"
                  placeholder={t("register.email")}
                />
              </div>
              <div className={styles.inputItem}>
                <input
                  type="text"
                  id="phone"
                  className="form-control"
                  placeholder={t("register.telephone")}
                />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <input
                  type="password"
                  id="password"
                  className="form-control"
                  placeholder={t("register.password")}
                />
              </div>
              <div className={styles.inputItem}>
                <input
                  type="password"
                  id="password2"
                  className="form-control"
                  placeholder={t("register.confirm_password")}
                />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <select id="city" className="form-select">
                  <option value="-1">{t("register.residence_city")}</option>
                </select>
              </div>
            </div>
          </div>
          <hr className="light-text" />
          <div className={styles.preferencesContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.preferences")}
            </h4>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <label className="light-text">
                  {t("language.language_selector")}
                </label>
                <select id="language" className="form-select">
                  <option value="es">{t("language.spanish")}</option>
                  <option value="en">{t("language.english")}</option>
                </select>
              </div>
            </div>
          </div>
          <div className={styles.submitContainer}>
            <button type="submit" className="btn secondary-btn">
              <h5>{t("register.register")}</h5>
            </button>
          </div>
        </form>
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
  );
};

export default RegisterPage;
