import useApproveReportForm from "@/hooks/forms/useApproveReportForm";
import styles from "./styles.module.scss";
import { Form, Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import PrivateReportModel from "@/models/PrivateReportModel";
import ModalReportFooter from "./ModalReportFooter";
import ReportApproveForm from "./ReportApproveForm";

export interface ModalReportAcceptProps {
  onSuccess: () => void;
  closeModal: () => void;
  report: PrivateReportModel;
}

const ModalReportAccept = ({
  onSuccess,
  closeModal,
  report,
}: ModalReportAcceptProps) => {
  const { t } = useTranslation();
  const approveReportForm = useApproveReportForm({ report, onSuccess });

  const { handleSubmit, isFetching } = approveReportForm;

  return (
    <Form onSubmit={handleSubmit}>
      <Modal.Header closeButton>
        <h4 className={styles.secondary_color_title}>
          {t("admin.report.accept")}
        </h4>
      </Modal.Header>
      <Modal.Body>
        <ReportApproveForm approveReportForm={approveReportForm} />
      </Modal.Body>
      <ModalReportFooter onClose={closeModal} isFetching={isFetching} />
    </Form>
  );
};

export default ModalReportAccept;
