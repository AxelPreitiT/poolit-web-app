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

            <h4 className={styles.secondary_color_title}>{t('admin.report.accept')}</h4>
            <button type="button" className={styles.close_btn} onClick={closeModal} aria-label="Close"></button>

            </Modal.Header>
            <Modal.Body>
                <div>
                    {reportProcessForm}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={closeModal}>
                    {t('modal.close')}
                </Button>
                <Button variant="secondary" className="secondary-btn" onClick={closeModal}>
                    {t('modal.submit')}
                </Button>
            </Modal.Footer>
        </div>

    );
};

export default ModalReportAccept;