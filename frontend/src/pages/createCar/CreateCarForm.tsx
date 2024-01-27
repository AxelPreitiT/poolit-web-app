import CarBrandModel from "@/models/CarBrandModel";
import CarFeatureModel from "@/models/CarFeatureModel";
import styles from "./styles.module.scss";
import CarBrandSelector from "@/components/forms/CarBrandSelector/CarBrandSelector";
import FormError from "@/components/forms/FormError/FormError";
import { Form } from "react-bootstrap";
import ImageInput from "@/components/forms/ImageInput/ImageInput";
import CarFeaturesPills from "@/components/car/CarFeaturesPills/CarFeaturesPills";
import { useTranslation } from "react-i18next";
import { BsCarFrontFill } from "react-icons/bs";
import { useState } from "react";
import LoadingButton from "@/components/buttons/LoadingButton";
import { BiCheck } from "react-icons/bi";

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

  return (
    <div>
      <Form className={styles.form}>
        <div className={styles.formRow}>
          <div className={styles.formColumn}>
            <div className={styles.formHeader}>
              <span className="secondary-text">{t("create_car.car_info")}</span>
            </div>
            <div className={styles.formGroup}>
              <div className={styles.formItem}>
                <CarBrandSelector
                  carBrands={carBrands}
                  defaultOption={t("create_car.car_brand")}
                  size="sm"
                />
                <FormError error="error" className={styles.formError} />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.car_description")}
                  size="sm"
                />
                <FormError error="error" className={styles.formError} />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.car_plate")}
                  size="sm"
                />
                <FormError error="error" className={styles.formError} />
              </div>
              <div className={styles.formItem}>
                <Form.Control
                  placeholder={t("create_car.available_seats")}
                  size="sm"
                />
                <FormError error="error" className={styles.formError} />
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
                <ImageInput
                  preview={preview}
                  previewAlt={t("create_car.car_image")}
                  placeholder={<BsCarFrontFill className="light-text" />}
                  onImageUpload={(image: File) => {
                    setPreview(image ? URL.createObjectURL(image) : undefined);
                  }}
                  className={styles.imageInput}
                />
              </div>
              <FormError error="error" className={styles.formError} />
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
                onChange={() => {}}
                pillClassName={styles.pill}
                activePillClassName={styles.activePill}
              />
              <FormError error="error" className={styles.formError} />
            </div>
          </div>
        </div>
        <div className={styles.submitContainer}>
          <LoadingButton
            className="secondary-btn btn"
            type="submit"
            isLoading={false}
          >
            <BiCheck className="h2 light-text" />
            <span className="light-text h3">{t("create_car.create")}</span>
          </LoadingButton>
        </div>
      </Form>
    </div>
  );
};

export default CreateCarForm;
