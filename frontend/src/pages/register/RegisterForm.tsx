import { useState } from "react";
import { Controller } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { Form } from "react-bootstrap";
import styles from "./styles.module.scss";
import ImageInput from "@/components/forms/ImageInput/ImageInput";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";
import useRegisterForm from "@/hooks/forms/useRegisterForm";

const RegisterForm = () => {
  const { t } = useTranslation();
  const [preview, setPreview] = useState<string | undefined>(undefined);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors, isSubmitting },
    tFormError,
  } = useRegisterForm();

  return (
    <Form className={styles.form} onSubmit={handleSubmit}>
      <div className={styles.userPicContainer}>
        <h4 className={styles.title + " light-text"}>
          {t("register.profile_image")}
        </h4>
        <Controller
          name="image"
          control={control}
          render={({ field: { onChange } }) => (
            <ImageInput
              id="image"
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
        <FormError error={tFormError(errors.image)} />
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
            <FormError error={tFormError(errors.name)} />
          </div>
          <div className={styles.inputItem}>
            <Form.Control
              type="text"
              id="lastName"
              size="sm"
              placeholder={t("register.last_name")}
              {...register("last_name")}
            />
            <FormError error={tFormError(errors.last_name)} />
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
            <FormError error={tFormError(errors.email)} />
          </div>
          <div className={styles.inputItem}>
            <Form.Control
              type="text"
              id="telephone"
              size="sm"
              placeholder={t("register.telephone")}
              {...register("telephone")}
            />
            <FormError error={tFormError(errors.telephone)} />
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
            <FormError error={tFormError(errors.password)} />
          </div>
          <div className={styles.inputItem}>
            <Form.Control
              type="password"
              id="confirmPassword"
              size="sm"
              placeholder={t("register.confirm_password")}
              {...register("confirm_password")}
            />
            <FormError error={tFormError(errors.confirm_password)} />
          </div>
        </div>
        <div className={styles.formRow}>
          <div className={styles.inputItem}>
            <Form.Select id="cityId" size="sm" {...register("city")}>
              <option value="-1">{t("register.residence_city")}</option>
            </Form.Select>
            <FormError error={tFormError(errors.city)} />
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
            <FormError error={tFormError(errors.locale)} />
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
  );
};

export default RegisterForm;
