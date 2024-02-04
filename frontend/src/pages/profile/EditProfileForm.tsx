import CityModel from "@/models/CityModel";
import UserPrivateModel from "@/models/UserPrivateModel";
import styles from "./styles.module.scss";
import { BsPersonCircle } from "react-icons/bs";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";
import EditableProfileImg from "@/components/profile/img/EditableProfileImg";
import { Button, Form } from "react-bootstrap";
import ProfilePropHeader from "@/components/profile/prop/ProfilePropHeader";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import { BiCheck, BiX } from "react-icons/bi";
import LoadingButton from "@/components/buttons/LoadingButton";
import useEditProfileForm from "@/hooks/forms/useEditProfileForm";
import { Controller } from "react-hook-form";
import CitySelector from "@/components/forms/CitySelector/CitySelector";
import { useState } from "react";

interface EditProfileFormProps {
  user: UserPrivateModel;
  city: CityModel;
  cities?: CityModel[];
  onSuccess?: () => void;
  onCancel?: () => void;
}

const EditProfileForm = ({
  user,
  city,
  cities = [],
  onSuccess,
  onCancel,
}: EditProfileFormProps) => {
  const { t } = useTranslation();
  const [initialPreview] = useState<string>(user.imageUri);
  const [preview, setPreview] = useState<string>(user.imageUri);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    isFetching,
    tFormError,
  } = useEditProfileForm({
    user,
    city,
    onSuccess,
  });

  return (
    <Form onSubmit={handleSubmit} className={styles.form}>
      <div className={styles.headerContainer}>
        <Controller
          name="image"
          control={control}
          render={({ field: { onChange } }) => (
            <EditableProfileImg
              className={styles.imageInput}
              preview={preview}
              previewAlt={t("edit_profile.image")}
              placeholder={<BsPersonCircle className="light-text" />}
              onImageUpload={(image: File) => {
                onChange(image);
                setPreview(image ? URL.createObjectURL(image) : initialPreview);
              }}
            />
          )}
        />
        <FormError
          error={tFormError(errors.image)}
          className={styles.formError}
        />
      </div>
      <div className={styles.profileInfo}>
        <div className={styles.profileInfoItem}>
          <ProfilePropHeader prop={t("edit_profile.name")} />
          <Form.Control
            type="text"
            placeholder={t("edit_profile.name")}
            {...register("name")}
          />
          <FormError
            error={tFormError(errors.name)}
            className={styles.formError}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfilePropHeader prop={t("edit_profile.surname")} />
          <Form.Control
            type="text"
            placeholder={t("edit_profile.surname")}
            {...register("last_name")}
          />
          <FormError
            error={tFormError(errors.last_name)}
            className={styles.formError}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfileProp prop={t("profile.props.email")} text={user.email} />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfilePropHeader prop={t("profile.props.phone")} />
          <Form.Control
            type="text"
            placeholder={t("profile.props.phone")}
            {...register("telephone")}
          />
          <FormError
            error={tFormError(errors.telephone)}
            className={styles.formError}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfilePropHeader prop={t("profile.props.neighborhood")} />
          <Controller
            name="city"
            control={control}
            render={({ field: { onChange, value } }) => (
              <CitySelector
                cities={cities}
                value={value}
                defaultOption={null}
                onChange={(event) => onChange(parseInt(event.target.value))}
                placeholder={t("profile.props.neighborhood")}
              />
            )}
          />
          <FormError
            error={tFormError(errors.city)}
            className={styles.formError}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfilePropHeader prop={t("profile.props.language")} />
          <Form.Select
            id="locale"
            {...register("locale")}
            placeholder={t("profile.props.language")}
          >
            <option value="en">{t("language.english")}</option>
            <option value="es">{t("language.spanish")}</option>
          </Form.Select>
          <FormError
            error={tFormError(errors.locale)}
            className={styles.formError}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfileProp
            prop={t("profile.props.trips")}
            text={user.tripCount.toString()}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfileStars
            prop={t("profile.props.rating_driver")}
            rating={user.driverRating}
          />
        </div>
        <div className={styles.profileInfoItem}>
          <ProfileStars
            prop={t("profile.props.rating_passenger")}
            rating={user.passengerRating}
          />
        </div>
      </div>
      <div className={styles.editButtonContainer}>
        <Button
          className="primary-btn"
          type="button"
          onClick={() => onCancel && onCancel()}
        >
          <BiX className="light-text h4" />
          <span className="light-text h4">{t("profile.cancel")}</span>
        </Button>
        <LoadingButton
          className="secondary-btn"
          type="submit"
          isLoading={isFetching}
        >
          <BiCheck className="light-text h4" />
          <span className="light-text h4">{t("profile.save")}</span>
        </LoadingButton>
      </div>
    </Form>
  );
};

export default EditProfileForm;
