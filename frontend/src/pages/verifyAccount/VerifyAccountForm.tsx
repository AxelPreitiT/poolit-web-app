import useVerifyAccountForm from "@/hooks/forms/useVerifyAccountForm";
import { useTranslation } from "react-i18next";
import styles from "../login/styles.module.scss";
import { Form } from "react-bootstrap";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";

const VerifyAccountForm = () => {
  const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    tFormError,
  } = useVerifyAccountForm();

  return (
    <Form className={styles.formContainer} onSubmit={handleSubmit}>
      <div>
        <h3 className="light-text fw-bold text-start">
          {t("verify_account.title")}
        </h3>
        <hr className="light-text" />
      </div>
      <div className={styles.inputContainer}>
        <div className={styles.emailContainer}>
          <Form.Control
            id="email"
            placeholder={t("verify_account.email")}
            {...register("email")}
          />
          <FormError error={tFormError(errors.email)} />
        </div>
      </div>
      <LoadingButton
        isLoading={isSubmitting}
        spinnerClassName="text-light"
        type="submit"
        className="btn secondary-btn"
      >
        <h5>{t("verify_account.send")}</h5>
      </LoadingButton>
    </Form>
  );
};

export default VerifyAccountForm;
