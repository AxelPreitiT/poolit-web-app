"use client";

import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import PoolitLogo from "@/assets/poolit.svg";
import { useTranslation } from "react-i18next";

const LoginPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <Link to="/">
          <img src={PoolitLogo} alt={t("poolit.logo")} />
        </Link>
      </div>
      <div className={styles.loginContainer}>
        <form className={styles.formContainer}>
          <div>
            <h3 className="light-text fw-bold">{t("login.title")}</h3>
            <hr className="light-text" />
          </div>
          <div className={styles.inputContainer}>
            <input
              type="email"
              id="email"
              name="email"
              className="form-control"
              placeholder={t("login.email")}
            />
            <input
              type="password"
              id="password"
              name="password"
              className="form-control"
              placeholder={t("login.password")}
            />
            <div className={styles.rememberContainer}>
              <input
                type="checkbox"
                id="remember"
                name="remember"
                className="form-check-input mt-0"
              />
              <label htmlFor="remember" className="light-text">
                {t("login.remember")}
              </label>
            </div>
          </div>
          <button type="submit" className="btn secondary-btn">
            <h5>{t("login.login")}</h5>
          </button>
        </form>
        <div className={styles.loginFooterContainer}>
          <hr className="light-text" />
          <h4 className="light-text">{t("register.not_registered")}</h4>
          <Link to="/register">
            <h5 className="link-text fw-bold">{t("register.register")}</h5>
          </Link>
        </div>
      </div>
      <div className={styles.footerContainer}>
        <h6>{t("verify_email.request")}</h6>
        <Link to="/verify-email">
          <h6 className="link-text fw-bold">{t("verify_email.here")}</h6>
        </Link>
      </div>
    </div>
  );
};

export default LoginPage;
