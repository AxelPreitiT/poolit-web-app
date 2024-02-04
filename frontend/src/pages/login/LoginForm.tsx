import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";
import { Form } from "react-bootstrap";
import useLoginForm from "@/hooks/forms/useLoginForm";
import { useState } from "react";

const LoginForm = () => {
  const { t } = useTranslation();
  const [unauthorizedError, setUnauthorizedError] = useState("");
  const {
    register,
    formState: { errors },
    handleSubmit,
    tFormError,
    isFetching,
  } = useLoginForm({
    onUnauthorized: () => setUnauthorizedError(t("login.error.unauthorized")),
  });

  return (
    <Form className={styles.formContainer} onSubmit={handleSubmit}>
      <div>
        <h3 className="light-text fw-bold text-start">{t("login.title")}</h3>
        <hr className="light-text" />
      </div>
      <div className={styles.inputContainer}>
        <div className={styles.emailContainer}>
          <Form.Control
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
        <div className={styles.errorContainer}>
          <FormError error={unauthorizedError} />
        </div>
        <div className={styles.rememberContainer}>
          <Form.Check type="checkbox" id="remember">
            <Form.Check.Input type="checkbox" {...register("remember_me")} />
            <Form.Check.Label className="light-text">
              {t("login.remember")}
            </Form.Check.Label>
          </Form.Check>
        </div>
      </div>
      <LoadingButton
        isLoading={isFetching}
        type="submit"
        className="btn secondary-btn"
        onClick={() => setUnauthorizedError("")}
      >
        <h5>{t("login.login")}</h5>
      </LoadingButton>
    </Form>
  );
};

export default LoginForm;
