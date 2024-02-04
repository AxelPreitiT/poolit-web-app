import FormError from "@/components/forms/FormError/FormError";
import useRejectReportForm from "@/hooks/forms/useRejectReportForm";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";

interface ReportRejectFormProps {
  rejectReportForm: ReturnType<typeof useRejectReportForm>;
}

const ReportRejectForm = ({ rejectReportForm }: ReportRejectFormProps) => {
  const { t } = useTranslation();

  const {
    register,
    formState: { errors },
    tFormError,
  } = rejectReportForm;

  return (
    <div>
      <strong className="text">{t("admin.report.explainReject")}</strong>
      <div>
        <span className="italic-text">{t("admin.report.notifyReject")}</span>
      </div>
      <Form.Control as="textarea" rows={3} {...register("reason")} />
      <FormError error={tFormError(errors.reason)} className={styles.error} />
    </div>
  );
};

export default ReportRejectForm;
