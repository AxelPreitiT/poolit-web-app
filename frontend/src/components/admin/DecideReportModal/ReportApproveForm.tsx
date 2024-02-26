import useApproveReportForm from "@/hooks/forms/useApproveReportForm";
import styles from "./styles.module.scss";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import FormError from "@/components/forms/FormError/FormError";

interface ReportApproveFormProps {
  approveReportForm: ReturnType<typeof useApproveReportForm>;
}

const ReportApproveForm = ({ approveReportForm }: ReportApproveFormProps) => {
  const { t } = useTranslation();

  const {
    register,
    formState: { errors },
    tFormError,
  } = approveReportForm;

  return (
    <div>
      <label className="report-option-label">
        <strong className="text">{t("admin.report.explainApprove")}</strong>
        <div>
          <span className="italic-text">{t("admin.report.notifyApprove")}</span>
        </div>
      </label>
      <Form.Control as="textarea" rows={3} {...register("reason")} />
      <FormError error={tFormError(errors.reason)} className={styles.error} />
      <div className={styles.danger}>
        <i className="bi bi-exclamation-diamond-fill h6"></i>
        <span className="h6 ms-1">{t("admin.report.approveWarning")}</span>
      </div>
    </div>
  );
};

export default ReportApproveForm;
