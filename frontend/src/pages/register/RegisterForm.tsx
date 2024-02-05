import { Controller } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { Button, Form, InputGroup } from "react-bootstrap";
import styles from "./styles.module.scss";
import FormError from "@/components/forms/FormError/FormError";
import LoadingButton from "@/components/buttons/LoadingButton";
import useRegisterForm from "@/hooks/forms/useRegisterForm";
import CityModel from "@/models/CityModel";
import CitySelector, {
  citySelectorDefaultValue,
} from "@/components/forms/CitySelector/CitySelector";
import { BsFillEyeFill, BsFillEyeSlashFill } from "react-icons/bs";
import { useState } from "react";

const RegisterForm = ({ cities }: { cities?: CityModel[] }) => {
  const { t, i18n } = useTranslation();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    tFormError,
    isFetching,
  } = useRegisterForm();

  return (
      <Form className={styles.form} onSubmit={handleSubmit}>
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
                  type="text"
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
              <InputGroup size="sm">
                <Form.Control
                    type={showPassword ? "text" : "password"}
                    id="password"
                    size="sm"
                    placeholder={t("register.password")}
                    {...register("password")}
                />
                <Button
                    className={styles.showButton}
                    onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <BsFillEyeFill /> : <BsFillEyeSlashFill />}
                </Button>
              </InputGroup>
              <FormError error={tFormError(errors.password)} />
            </div>
            <div className={styles.inputItem}>
              <InputGroup size="sm">
                <Form.Control
                    type={showConfirmPassword ? "text" : "password"}
                    id="confirmPassword"
                    size="sm"
                    placeholder={t("register.confirm_password")}
                    {...register("confirm_password")}
                />
                <Button
                    className={styles.showButton}
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                >
                  {showConfirmPassword ? (
                      <BsFillEyeFill />
                  ) : (
                      <BsFillEyeSlashFill />
                  )}
                </Button>
              </InputGroup>
              <FormError error={tFormError(errors.confirm_password)} />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.inputItem}>
              <Controller
                  name="city"
                  defaultValue={citySelectorDefaultValue}
                  control={control}
                  render={({ field: { onChange, value } }) => (
                      <CitySelector
                          cities={cities}
                          defaultOption={t("register.residence_city")}
                          placeholder={t("register.residence_city")}
                          size="sm"
                          onChange={(event) => onChange(parseInt(event.target.value))}
                          value={value}
                      />
                  )}
              />
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
                <Form.Select
                    id="locale"
                    size="sm"
                    {...register("locale")}
                    placeholder={t("language.language")}
                    defaultValue={i18n.language}
                >
                  <option value="en">{t("language.english")}</option>
                  <option value="es">{t("language.spanish")}</option>
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
              isLoading={isFetching}
          >
            <h5>{t("register.register")}</h5>
          </LoadingButton>
        </div>
      </Form>
  );
};

export default RegisterForm;
