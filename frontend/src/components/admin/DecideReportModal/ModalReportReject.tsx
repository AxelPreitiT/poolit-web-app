import PrivateReportModel from "@/models/PrivateReportModel";
import styles from "./styles.module.scss";
import { Form, Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import useRejectReportForm from "@/hooks/forms/useRejectReportForm";
import ModalReportFooter from "./ModalReportFooter";
import ReportRejectForm from "./ReportRejectForm";

export interface ModalReportRejectProps {
  closeModal: () => void;
  onSuccess: () => void;
  report: PrivateReportModel;
}

const ModalReportReject = ({
  closeModal,
  onSuccess,
  report,
}: ModalReportRejectProps) => {
  const { t } = useTranslation();

  const rejectReportForm = useRejectReportForm({ report, onSuccess });

  const { handleSubmit, isFetching } = rejectReportForm;

  return (
    <Form onSubmit={handleSubmit}>
      <Modal.Header closeButton>
        <h4 className={styles.danger}>{t("admin.report.reject")}</h4>
      </Modal.Header>
      <Modal.Body>
        <ReportRejectForm rejectReportForm={rejectReportForm} />
      </Modal.Body>
      <ModalReportFooter onClose={closeModal} isFetching={isFetching} />
    </Form>
  );
};

export default ModalReportReject;
