import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";
import { Form } from "react-bootstrap";
import useLoginForm from "@/hooks/forms/useLoginForm";

const LoginForm = () => {
  const { t } = useTranslation();
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    tFormError,
  } = useLoginForm();
  console.log(errors);

  return (
    <Form className={styles.formContainer} onSubmit={handleSubmit}>
      <div>
        <h3 className="light-text fw-bold">{t("login.title")}</h3>
        <hr className="light-text" />
      </div>
      <div className={styles.inputContainer}>
        <div className={styles.emailContainer}>
          <Form.Control
            type="email"
            id="email"
            placeholder={t("login.email")}
            {...register("email")}
          />
          <FormError error={tFormError(errors.email)} />
        </div>
        <div className={styles.passwordContainer}>
          <Form.Control
            type="password"
            id="password"
            placeholder={t("login.password")}
            {...register("password")}
          />
          <FormError error={tFormError(errors.password)} />
        </div>
        <div className={styles.rememberContainer}>
          <Form.Check type="checkbox" id="remember">
            <Form.Check.Input type="checkbox" {...register("rememberMe")} />
            <Form.Check.Label className="light-text">
              {t("login.remember")}
            </Form.Check.Label>
          </Form.Check>
        </div>
      </div>
      <LoadingButton
        isLoading={isSubmitting}
        spinnerClassName="text-light"
        type="submit"
        className="btn secondary-btn"
      >
        <h5>{t("login.login")}</h5>
      </LoadingButton>
    </Form>
  );
};

export default LoginForm;
