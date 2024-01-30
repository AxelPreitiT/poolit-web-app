import styles from "../ModalReportsReviews/styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import carModel from "@/models/CarModel.ts";

export interface ModalCarMakeReviewProps {
    closeModal: () => void;
    car: carModel | null;
    reportForm: React.ReactNode;
}

const ModalMakeCar = ({ closeModal, car, reportForm}: ModalCarMakeReviewProps) => {
    const { t } = useTranslation();

    return (
        (car != null &&
            <div className={styles.propProfile}>
                <div className={styles.reportFormHeader}>
                    <div className={styles.imgCointainer}>
                        <CircleImg src={car.imageUri} size={70}/>
                    </div>
                    <div className={styles.reportFormTitle}>
                        <h3>{car.infoCar}</h3>
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

export default ModalMakeCar;
