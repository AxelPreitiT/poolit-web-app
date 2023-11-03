import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";
import RegisterBanner from "@/images/register-banner.jpg";
import { Link } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import RedirectHomeLogo from "@/components/links/RedirectHomeLogo/RedirectHomeLogo";
import { Form } from "react-bootstrap";
import ImageInput from "@/components/forms/ImageInput/ImageInput";
import { useState } from "react";
import {
  RegisterFormSchema,
  RegisterFormSchemaType,
  tFormError,
} from "./registerFormSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { SubmitHandler, useForm } from "react-hook-form";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { Controller } from "react-hook-form";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";

const RegisterPage = () => {
  const { t } = useTranslation();
  const [preview, setPreview] = useState<string | undefined>(undefined);
  const addToast = useToastStackStore((state) => state.addToast);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors, isSubmitting },
  } = useForm<RegisterFormSchemaType>({
    resolver: zodResolver(RegisterFormSchema),
  });

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
      <div className={styles.logoContainer}>
        <RedirectHomeLogo />
      </div>
      <div className={styles.bannerContainer}>
        <img src={RegisterBanner} alt={t("register.banner")} />
        <div className={styles.logoContainer}>
          <RedirectHomeLogo />
        </div>
      </div>
      <div className={styles.formContainer}>
        <Form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
          <div className={styles.userPicContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.profile_image")}
            </h4>
            <Controller
              name="profilePic"
              control={control}
              render={({ field: { onChange } }) => (
                <ImageInput
                  id="profilePic"
                  preview={preview}
                  previewAlt={t("register.profile_image")}
                  onImageUpload={(image: File) => {
                    onChange(image);
                    setPreview(image ? URL.createObjectURL(image) : undefined);
                  }}
                  placeholder={
                    <i className="bi bi-person-circle light-text h1"></i>
                  }
                  className={styles.imageInput}
                />
              )}
            />
            <FormError error={tFormError(errors.profilePic?.message)} />
          </div>
          <hr className="light-text" />
          <div className={styles.userInfoContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.personal_info")}
            </h4>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <Form.Control
                  type="text"
                  id="name"
                  size="sm"
                  placeholder={t("register.name")}
                  {...register("name")}
                />
                <FormError error={tFormError(errors.name?.message)} />
              </div>
              <div className={styles.inputItem}>
                <Form.Control
                  type="text"
                  id="lastName"
                  size="sm"
                  placeholder={t("register.last_name")}
                  {...register("lastName")}
                />
                <FormError error={tFormError(errors.lastName?.message)} />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <Form.Control
                  type="email"
                  id="email"
                  size="sm"
                  placeholder={t("register.email")}
                  {...register("email")}
                />
                <FormError error={tFormError(errors.email?.message)} />
              </div>
              <div className={styles.inputItem}>
                <Form.Control
                  type="text"
                  id="telephone"
                  size="sm"
                  placeholder={t("register.telephone")}
                  {...register("telephone")}
                />
                <FormError error={tFormError(errors.telephone?.message)} />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <Form.Control
                  type="password"
                  id="password"
                  size="sm"
                  placeholder={t("register.password")}
                  {...register("password")}
                />
                <FormError error={tFormError(errors.password?.message)} />
              </div>
              <div className={styles.inputItem}>
                <Form.Control
                  type="password"
                  id="confirmPassword"
                  size="sm"
                  placeholder={t("register.confirm_password")}
                  {...register("confirmPassword")}
                />
                <FormError
                  error={tFormError(errors.confirmPassword?.message)}
                />
              </div>
            </div>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <Form.Select id="cityId" size="sm" {...register("cityId")}>
                  <option value="-1">{t("register.residence_city")}</option>
                </Form.Select>
                <FormError error={tFormError(errors.cityId?.message)} />
              </div>
            </div>
          </div>
          <hr className="light-text" />
          <div className={styles.preferencesContainer}>
            <h4 className={styles.title + " light-text"}>
              {t("register.preferences")}
            </h4>
            <div className={styles.formRow}>
              <div className={styles.inputItem}>
                <Form.Group>
                  <Form.Label className="light-text">
                    {t("language.language_selector")}
                  </Form.Label>
                  <Form.Select id="locale" size="sm" {...register("locale")}>
                    <option value="es">{t("language.spanish")}</option>
                    <option value="en">{t("language.english")}</option>
                  </Form.Select>
                </Form.Group>
                <FormError error={tFormError(errors.locale?.message)} />
              </div>
            </div>
          </div>
          <div className={styles.submitContainer}>
            <LoadingButton
              type="submit"
              className="btn secondary-btn"
              isLoading={isSubmitting}
            >
              <h5>{t("register.register")}</h5>
            </LoadingButton>
          </div>
        </Form>
        <hr className="light-text mt-auto" />
        <div className={styles.loginContainer}>
          <h5 className={styles.title + " light-text"}>
            {t("register.already_registered")}
          </h5>
          <Link to={loginPath}>
            <h5 className="link-text fw-bold">{t("login.login")}</h5>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
