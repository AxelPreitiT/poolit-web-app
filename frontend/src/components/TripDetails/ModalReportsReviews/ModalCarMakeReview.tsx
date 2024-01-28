import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import carModel from "@/models/CarModel.ts";

export interface ModalCarMakeReviewProps {
    closeModal: () => void;
    car: carModel | null;
}

const ModalCarMakeReview = ({ closeModal, car}: ModalCarMakeReviewProps) => {
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
                        <h1>poner las cosas aca</h1>
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

export default ModalCarMakeReview;
