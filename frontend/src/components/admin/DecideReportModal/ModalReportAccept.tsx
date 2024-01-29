import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import {useTranslation} from "react-i18next";

export interface ModalReportAcceptProps {
    closeModal: () => void;
    reportProcessForm: React.ReactNode;
}

const ModalReportAccept = ({ closeModal, reportProcessForm}: ModalReportAcceptProps) => {
    const { t } = useTranslation();

    return (
        <div>
            <Modal.Header>
            <div className={styles.reportFormHeader}>
                <div className={styles.reportFormTitle}>
                    <h4>{t('admin.report.accept')}</h4>
                    <button type="button" className={styles.close_btn} onClick={closeModal} aria-label="Close"></button>
                    <hr></hr>
                </div>
            </div>
            </Modal.Header>
            <Modal.Body>
                <div>
                    {reportProcessForm}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className={styles.backBtn} onClick={closeModal}>
                    {t('modal.close')}
                </Button>
                <Button className={styles.acceptBtn} onClick={closeModal}>
                    {t('modal.submit')}
                </Button>
            </Modal.Footer>
        </div>

    );
};

export default ModalReportAccept;