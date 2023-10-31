import { zodResolver } from "@hookform/resolvers/zod";
import {
  LoginFormSchema,
  LoginFormSchemaType,
  tFormError,
} from "./loginFormSchema";
import styles from "./styles.module.scss";
import { SubmitHandler, useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";

interface LoginFormProps {
  onSubmit: SubmitHandler<LoginFormSchemaType>;
  isSubmitting: boolean;
}

const LoginForm = ({ onSubmit, isSubmitting }: LoginFormProps) => {
  const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormSchemaType>({
    resolver: zodResolver(LoginFormSchema),
  });

  return (
    <form className={styles.formContainer} onSubmit={handleSubmit(onSubmit)}>
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
          <FormError error={tFormError(errors.email?.message)} />
        </div>
        <div className={styles.passwordContainer}>
          <input
            type="password"
            id="password"
            className="form-control"
            placeholder={t("login.password")}
            {...register("password")}
          />
          <FormError error={tFormError(errors.password?.message)} />
        </div>
        <div className={styles.rememberContainer}>
          <input
            type="checkbox"
            id="remember"
            className="form-check-input mt-0"
            {...register("rememberMe")}
          />
          <label htmlFor="remember" className="light-text">
            {t("login.remember")}
          </label>
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
    </form>
  );
};

export default LoginForm;
