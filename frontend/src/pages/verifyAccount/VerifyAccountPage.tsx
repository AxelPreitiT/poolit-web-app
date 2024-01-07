import { useTranslation } from "react-i18next";
import styles from "../login/styles.module.scss";
import { Link } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";
import VerifyAccountForm from "./VerifyAccountForm";

const VerifyAccountPage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.primaryContainer}>
        <VerifyAccountForm />
        <div className={styles.primaryFooterContainer}>
          <hr className="light-text" />
          <h5 className="light-text">{t("verify_account.already_verified")}</h5>
          <Link to={loginPath}>
            <h5 className="link-text fw-bold">{t("login.title")}</h5>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default VerifyAccountPage;
