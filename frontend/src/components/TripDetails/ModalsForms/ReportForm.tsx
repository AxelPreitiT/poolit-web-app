import { useEffect } from "react";
import styles from "./styles.module.scss";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { ModalMakeFormProps } from "./ModalMake";
import useReviewsReportsDriver from "@/hooks/reportReview/useReviewsReportsDriver";
import useReviewsReportPassangers from "@/hooks/reportReview/useReviewsPassangers";
import useReportForm from "@/hooks/forms/useReportForm";
import { getReportRelation } from "@/enums/ReportRelation";
import FormError from "@/components/forms/FormError/FormError";

const ReportForm = ({
  onClose,
  formId,
  isCurrentUserDriver,
  isUserDriver,
  setIsFetching,
  trip,
  user,
}: ModalMakeFormProps) => {
  const { t } = useTranslation();
  const { invalidateDriverReportQuery } = useReviewsReportsDriver(true, trip);
  const { invalidatePassengerReportQuery } = useReviewsReportPassangers();

  const onSuccess = () => {
    if (isUserDriver) {
      invalidateDriverReportQuery();
    } else {
      invalidatePassengerReportQuery(user);
    }
    onClose();
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
    tFormError,
    isFetching,
    reportOptions = [],
  } = useReportForm({
    trip,
    relation: getReportRelation(isCurrentUserDriver, isUserDriver),
    reported: user,
    onSuccess,
  });

  useEffect(() => {
    setIsFetching(isFetching);
  }, [isFetching, setIsFetching]);

  return (
    <Form className={styles.picker_style} id={formId} onSubmit={handleSubmit}>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reports.option_selector")}</strong>
        </label>
        <Form.Select {...register("option")}>
          {reportOptions.map((option) => (
            <option key={option.id} value={option.id}>
              {t(`reports.${option.id}`)}
            </option>
          ))}
        </Form.Select>
        <FormError error={tFormError(errors.option)} className={styles.error} />
      </div>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reports.control_label")}</strong>
        </label>
        <Form.Control as="textarea" rows={3} {...register("reason")} />
        <FormError error={tFormError(errors.reason)} className={styles.error} />
      </div>
    </Form>
  );
};

export default ReportForm;
