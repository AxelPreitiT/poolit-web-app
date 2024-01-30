import styles from "../ModalReportsReviews/styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import userPublicModel from "@/models/UserPublicModel.ts";
import {useTranslation} from "react-i18next";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";

export interface ModalMakeReportProps {
    closeModal: () => void;
    user: userPublicModel | null;
    reportForm: React.ReactNode;
}

const ModalMake = ({ closeModal, user, reportForm}: ModalMakeReportProps) => {
    const { t } = useTranslation();

    return (
        (user != null &&
        <div className={styles.propProfile}>
            <div className={styles.reportFormHeader}>
                <div className={styles.imgCointainer}>
                    <CircleImg src={user.imageUri} size={70}/>
                </div>
                <div className={styles.reportFormTitle}>
                    <h3>
                    {t("format.name", {
                        name: user.username,
                        surname: user.surname})
                    }</h3>
                    <hr></hr>
                </div>
            </div>
            <Modal.Body>
                <div className={styles.categoryContainer}>
                    {reportForm}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className={styles.backBtn} onClick={closeModal}>
                    {t('modal.close')}
                </Button>
                <Button className={styles.submitBtn} onClick={closeModal}>
                    {t('modal.submit')}
                </Button>
            </Modal.Footer>
        </div>
        )
    );
};

export default ModalMake;
