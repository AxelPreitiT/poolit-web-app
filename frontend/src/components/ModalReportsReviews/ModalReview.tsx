import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import PassangerReportReviewComponent from "@/components/ModalReportsReviews/PassangerReportReviewComponent.tsx";
import PassangerModel from "@/models/PassangerModel.ts";

export interface ModalReviewProps {
    closeModal: () => void;
    passangers: PassangerModel[];
}

const ModalReview= ({ closeModal, passangers}: ModalReviewProps) => {
    return (
        <div className={styles.propProfile}>
            <Modal.Header closeButton>
                <Modal.Title><h2>Your Opinion Interes Ours</h2></Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className={styles.categoryContainer}>
                    <div className={styles.passangerContainer}>
                        <div className={styles.titleContainer}>
                            <i className="bi bi-person-circle h3"></i>
                            <h3>DRIVER</h3>
                        </div>
                        <PassangerReportReviewComponent passanger={passangers[1]} closeModal={closeModal}/>
                    </div>
                    <div className={styles.passangerContainer}>
                        <div className={styles.titleContainer}>
                            <i className="bi bi-person-circle h3"></i>
                            <h3>CAR</h3>
                        </div>
                        <PassangerReportReviewComponent passanger={passangers[1]} closeModal={closeModal}/>
                    </div>
                    <div className={styles.passangerContainer}>
                        <div className={styles.titleContainer}>
                            <i className="bi-fill bi-people h3"></i>
                            <h3>PASSANGER</h3>
                        </div>
                        <PassangerReportReviewComponent passanger={passangers[1]} closeModal={closeModal}/>
                        <PassangerReportReviewComponent passanger={passangers[1]} closeModal={closeModal}/>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className={styles.backBtn} onClick={closeModal}>
                    Close
                </Button>
            </Modal.Footer>
        </div>
    );
};

export default ModalReview;
