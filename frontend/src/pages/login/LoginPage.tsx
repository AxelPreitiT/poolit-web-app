import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import PoolitLogo from "@/images/poolit.svg";
import { useTranslation } from "react-i18next";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import FormError from "@/components/forms/FormError/FormError";
import {
  LoginFormSchema,
  LoginFormSchemaType,
  tLogin,
} from "./loginFormSchema";

const LoginPage = () => {
  const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormSchemaType>({
    resolver: zodResolver(LoginFormSchema),
  });

  const onSubmit: SubmitHandler<LoginFormSchemaType> = (data) => {
    console.log(data);
  };

  return (
    <div className={styles.mainContainer}>
      <div className={styles.logoContainer}>
        <Link to="/">
          <img src={PoolitLogo} alt={t("poolit.logo")} />
        </Link>
      </div>
      <div className={styles.loginContainer}>
        <form
          className={styles.formContainer}
          onSubmit={handleSubmit(onSubmit)}
        >
          <div>
            <h3 className="light-text fw-bold">{t("login.title")}</h3>
            <hr className="light-text" />
          </div>
          <div className={styles.inputContainer}>
            <div className={styles.emailContainer}>
              <input
                type="email"
                id="email"
                className="form-control"
                placeholder={t("login.email")}
                {...register("email")}
              />
              <FormError error={tLogin(errors.email?.message)} />
            </div>
            <div className={styles.passwordContainer}>
              <input
                type="password"
                id="password"
                className="form-control"
                placeholder={t("login.password")}
                {...register("password")}
              />
              <FormError error={tLogin(errors.password?.message)} />
            </div>
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
