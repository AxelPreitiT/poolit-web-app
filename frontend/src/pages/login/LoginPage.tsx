import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { SubmitHandler } from "react-hook-form";
import { LoginFormSchemaType } from "./loginFormSchema";
import LoginForm from "./LoginForm";
import { useState } from "react";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import ToastType from "@/enums/ToastType";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { registerPath, verifyEmailPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";

const LoginPage = () => {
  const { t } = useTranslation();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandler<LoginFormSchemaType> = async (data) => {
    setIsSubmitting(true);
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      setIsSubmitting(false);
      console.log(data);
      addToast({
        type: ToastType.Error,
        message: t("login.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.loginContainer}>
        <LoginForm onSubmit={onSubmit} isSubmitting={isSubmitting} />
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
          {t("verify_email.request")}
          <Link to={verifyEmailPath}>
            <h6 className="link-text fw-bold">{t("verify_email.here")}</h6>
          </Link>
        </h6>
      </div>
    </div>
  );
};

export default LoginPage;
