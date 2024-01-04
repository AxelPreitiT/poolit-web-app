import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import RegisterBanner from "@/images/register-banner.jpg";
import { Link } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";
import { RegisterFormSchemaType } from "./registerFormSchema";
import { SubmitHandler } from "react-hook-form";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import RegisterForm from "./RegisterForm";

const RegisterPage = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandler<RegisterFormSchemaType> = async (data) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.Error,
        message: t("register.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

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
        <RegisterForm onSubmit={onSubmit} />
        <div className={styles.footerContainer}>
          <hr className="light-text mt-auto" />
          <div className={styles.loginContainer}>
            <h6 className={styles.title + " light-text"}>
              {t("register.already_registered")}
            </h6>
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
