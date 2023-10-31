import styles from "./styles.module.scss";
import { Link } from "react-router-dom";
import PoolitLogo from "@/images/poolit.svg";
import { useTranslation } from "react-i18next";
import { SubmitHandler } from "react-hook-form";
import { LoginFormSchemaType } from "./loginFormSchema";
import LoginForm from "./LoginForm";
import { Dispatch, SetStateAction, useState } from "react";
import ToastStack from "@/components/toasts/ToastStack";
import ErrorToast from "@/components/toasts/ErrorToast/ErrorToast";

const SubmitErrorToastStack = ({
  submitErrors,
  setSubmitErrors,
}: {
  submitErrors: string[];
  setSubmitErrors: Dispatch<SetStateAction<string[]>>;
}) => (
  <ToastStack
    position="bottom-end"
    className="mb-2 me-2"
    toasts={submitErrors.map((error, index) => (
      <ErrorToast
        message={error}
        timer={10000}
        key={index}
        show={!!error}
        onClose={() =>
          setSubmitErrors((submitErrors) => {
            const newSubmitErrors = [...submitErrors];
            newSubmitErrors.splice(index, 1, "");
            return newSubmitErrors;
          })
        }
      />
    ))}
  />
);

const LoginPage = () => {
  const { t } = useTranslation();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitErrors, setSubmitErrors] = useState<string[]>([]);

  const onSubmit: SubmitHandler<LoginFormSchemaType> = async (data) => {
    setIsSubmitting(true);
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      setIsSubmitting(false);
      console.log(data);
      setSubmitErrors([...submitErrors, t("login.error")]);
    });
  };

  return (
    <>
      <div className={styles.mainContainer}>
        <div className={styles.logoContainer}>
          <Link to="/">
            <img src={PoolitLogo} alt={t("poolit.logo")} />
          </Link>
        </div>
        <div className={styles.loginContainer}>
          <LoginForm onSubmit={onSubmit} isSubmitting={isSubmitting} />
          <div className={styles.loginFooterContainer}>
            <hr className="light-text" />
            <h4 className="light-text">{t("register.not_registered")}</h4>
            <Link to="/register">
              <h5 className="link-text fw-bold">{t("register.register")}</h5>
            </Link>
          </div>
        </div>
        <div className={styles.footerContainer}>
          <h6>
            {t("verify_email.request")}
            <Link to="/verify-email">
              <h6 className="link-text fw-bold">{t("verify_email.here")}</h6>
            </Link>
          </h6>
        </div>
      </div>
      <SubmitErrorToastStack
        submitErrors={submitErrors}
        setSubmitErrors={setSubmitErrors}
      />
    </>
  );
};

export default LoginPage;
