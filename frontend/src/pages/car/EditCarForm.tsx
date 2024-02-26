import CarModel from "@/models/CarModel";
import { Button, Form } from "react-bootstrap";
import styles from "./styles.module.scss";
import ProfilePropHeader from "@/components/profile/prop/ProfilePropHeader";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import CarBrandModel from "@/models/CarBrandModel";
import CarFeatureModel from "@/models/CarFeatureModel";
import CarFeaturesPills from "@/components/car/CarFeaturesPills/CarFeaturesPills";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import { BiCheck, BiX } from "react-icons/bi";
import LoadingButton from "@/components/buttons/LoadingButton";
import useEditCarForm from "@/hooks/forms/useEditCarForm";
import { useCallback, useState } from "react";
import { Controller } from "react-hook-form";
import { BsCarFrontFill } from "react-icons/bs";
import EditableProfileImg from "@/components/profile/img/EditableProfileImg";
import { parseInputInteger } from "@/utils/integer/parse";

interface EditCarFormProps {
  car: CarModel;
  carBrand?: CarBrandModel;
  carFeatures?: CarFeatureModel[];
  initialCarFeatures?: CarFeatureModel[];
  onSuccess?: () => void;
  onCancel?: () => void;
}

const EditCarForm = ({
  car,
  carBrand,
  carFeatures = [],
  initialCarFeatures = [],
  onSuccess,
  onCancel,
}: EditCarFormProps) => {
  const { t } = useTranslation();
  const [initialPreview] = useState<string>(car.imageUri);
  const [preview, setPreview] = useState<string>(car.imageUri);
  const initialSelectedCarFeatures = initialCarFeatures.map(
    (feature) => feature.id
  );

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    tFormError,
    isFetching,
    setValue,
  } = useEditCarForm({
    car,
    carFeatures: initialCarFeatures,
    onSuccess,
  });

  const setCarFeatures = useCallback(
    (value: string[]) => setValue("car_features", value),
    [setValue]
  );

  return (
    <Form className={styles.form} onSubmit={handleSubmit}>
      <div className={styles.headerContainer}>
        <Controller
          name="image"
          control={control}
          render={({ field: { onChange } }) => (
            <EditableProfileImg
              className={styles.imageInput}
              onImageUpload={(image: File) => {
                setPreview(image ? URL.createObjectURL(image) : initialPreview);
                onChange(image);
              }}
              preview={preview}
              previewAlt={t("edit_car.image")}
              placeholder={<BsCarFrontFill className="light-text" />}
            />
          )}
        />
        <FormError
          error={tFormError(errors.image)}
          className={styles.formError}
        />
      </div>
      <div className={styles.carInfo}>
        <div className={styles.carInfoItem}>
          <ProfilePropHeader prop={t("car.prop.description")} />
          <Form.Control
            type="text"
            placeholder={t("car.prop.description")}
            {...register("car_description")}
          />
          <FormError
            error={tFormError(errors.car_description)}
            className={styles.formError}
          />
        </div>
        <div className={styles.carInfoItem}>
          <ProfileProp
            prop={t("car.prop.brand")}
            text={carBrand?.name || t("car_brands.unknown")}
          />
        </div>
        <div className={styles.carInfoItem}>
          <ProfilePropHeader prop={t("car.prop.seats")} />
          <Form.Control
            type="text"
            placeholder={t("car.prop.seats")}
            {...register("seats", {
              setValueAs: parseInputInteger,
            })}
          />
          <FormError
            error={tFormError(errors.seats)}
            className={styles.formError}
          />
        </div>
        <div className={styles.carInfoItem}>
          <ProfileProp prop={t("car.prop.plate")} text={car.plate} />
        </div>
        <div className={styles.carInfoItem}>
          <ProfilePropHeader prop={t("car.prop.features")} />
          <CarFeaturesPills
            carFeatures={carFeatures}
            initialSelectedCarFeatures={initialSelectedCarFeatures}
            pillClassName={styles.pill}
            activePillClassName={styles.activePill}
            onSelect={setCarFeatures}
          />
        </div>
        <div className={styles.carInfoItem}>
          <ProfileStars prop={t("car.prop.rating")} rating={car.rating} />
        </div>
      </div>
      <div className={styles.editButtonContainer}>
        <Button
          className="primary-btn"
          type="button"
          onClick={() => onCancel && onCancel()}
        >
          <BiX className="light-text h4" />
          <span className="light-text h4">{t("edit_car.cancel")}</span>
        </Button>
        <LoadingButton
          className="secondary-btn"
          type="submit"
          isLoading={isFetching}
        >
          <BiCheck className="light-text h4" />
          <span className="light-text h4">{t("edit_car.save")}</span>
        </LoadingButton>
      </div>
    </Form>
  );
};

export default EditCarForm;
