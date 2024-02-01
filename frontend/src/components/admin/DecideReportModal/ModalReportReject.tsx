import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import {useTranslation} from "react-i18next";

export interface ModalReportRejectProps {
    closeModal: () => void;
    reportProcessForm: React.ReactNode;
}

const ModalReportReject = ({ closeModal, reportProcessForm}: ModalReportRejectProps) => {
    const { t } = useTranslation();

    return (
        <div>
            <Modal.Header>

            <h4 className={styles.danger}>{t('admin.report.reject')}</h4>
            <button type="button" className="btn-close" onClick={closeModal} aria-label="Close"></button>

            </Modal.Header>
            <Modal.Body>
                <div>
                    {reportProcessForm}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary"  className="later-btn" onClick={closeModal}>
                    {t('modal.close')}
                </Button>
                <Button variant="danger" className="danger-btn" onClick={closeModal}>
                    {t('modal.submit')}
                </Button>
            </Modal.Footer>
        </div>

    );
};

export default ModalReportReject;