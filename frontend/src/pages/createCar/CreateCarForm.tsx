import CarBrandModel from "@/models/CarBrandModel";
import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import CarBrandSelector, {
  carBrandSelectorDefaultValue,
} from "@/components/forms/CarBrandSelector/CarBrandSelector";
import FormError from "@/components/forms/FormError/FormError";
import { Form } from "react-bootstrap";
import ImageInput from "@/components/forms/ImageInput/ImageInput";
import CarFeaturesPills from "@/components/car/CarFeaturesPills/CarFeaturesPills";
import { useTranslation } from "react-i18next";
import { BsCarFrontFill } from "react-icons/bs";
import { useCallback, useState } from "react";
import LoadingButton from "@/components/buttons/LoadingButton";
import { BiCheck } from "react-icons/bi";
import useCreateCarForm from "@/hooks/forms/useCreateCarForm";
import { Controller } from "react-hook-form";
import { parseInputInteger } from "@/utils/integer/parse";

interface CreateCarFormProps {
  carFeatures?: CarFeatureModel[];
  carBrands?: CarBrandModel[];
}

const CreateCarForm = ({
  carBrands = [],
  carFeatures = [],
}: CreateCarFormProps) => {
  const { t } = useTranslation();
  const [preview, setPreview] = useState<string | undefined>(undefined);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    tFormError,
    isFetching,
    setValue,
  } = useCreateCarForm();

  const setCarFeatures = useCallback(
    (value: string[]) => setValue("car_features", value),
    [setValue]
  );

  return (
    <div>
      <Form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.formRow}>
          <div className={styles.formColumn}>
            <div className={styles.formHeader}>
              <span className="secondary-text">{t("create_car.car_info")}</span>
            </div>
            <div className={styles.formGroup}>
              <div className={styles.formItem}>
                <Controller
                  name="car_brand"
                  defaultValue={carBrandSelectorDefaultValue}
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <CarBrandSelector
                      carBrands={carBrands}
                      defaultOption={t("create_car.car_brand")}
                      size="sm"
                      onChange={onChange}
                      value={value}
                    />
                  )}
                />
                <FormError
                  error={tFormError(errors.car_brand)}
                  className={styles.formError}
                />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.car_description")}
                  size="sm"
                  {...register("car_description")}
                />
                <FormError
                  error={tFormError(errors.car_description)}
                  className={styles.formError}
                />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.car_plate")}
                  size="sm"
                  {...register("car_plate")}
                />
                <FormError
                  error={tFormError(errors.car_plate)}
                  className={styles.formError}
                />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.available_seats")}
                  size="sm"
                  {...register("seats", {
                    setValueAs: parseInputInteger,
                  })}
                />
                <FormError
                  error={tFormError(errors.seats)}
                  className={styles.formError}
                />
              </div>
            </div>
          </div>
          <div className={styles.formColumn}>
            <div className={styles.formHeader}>
              <span className="secondary-text">
                {t("create_car.car_image")}
              </span>
            </div>
            <div className={styles.formGroup}>
              <div className={styles.formImage}>
                <Controller
                  name="image"
                  control={control}
                  render={({ field: { onChange } }) => (
                    <ImageInput
                      preview={preview}
                      previewAlt={t("create_car.car_image")}
                      placeholder={<BsCarFrontFill className="light-text" />}
                      onImageUpload={(image: File) => {
                        setPreview(
                          image ? URL.createObjectURL(image) : undefined
                        );
                        onChange(image);
                      }}
                      className={styles.imageInput}
                    />
                  )}
                />
              </div>
              <FormError
                error={tFormError(errors.image)}
                className={styles.formError}
              />
            </div>
          </div>
        </div>
        <div className={styles.formRowFull}>
          <div className={styles.formColumn}>
            <div className={styles.formHeader}>
              <span className="secondary-text">
                {t("create_car.car_features")}
              </span>
            </div>
            <div className={styles.formGroup}>
              <CarFeaturesPills
                carFeatures={carFeatures}
                onSelect={setCarFeatures}
                pillClassName={styles.pill}
                activePillClassName={styles.activePill}
              />
            </div>
          </div>
        </div>
        <div className={styles.submitContainer}>
          <LoadingButton
            className={"secondary-btn btn " + styles.submit}
            type="submit"
            isLoading={isFetching}
          >
            <BiCheck className="light-text" />
            <span className="light-text h3">{t("create_car.create")}</span>
          </LoadingButton>
        </div>
      </Form>
    </div>
  );
};

export default CreateCarForm;
