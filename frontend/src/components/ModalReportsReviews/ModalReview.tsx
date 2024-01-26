import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import PassangerModel from "@/models/PassangerModel.ts";
import {useTranslation} from "react-i18next";
import DriverReportReviewComponent from "@/components/ModalReportsReviews/DriverReportReviewComponent.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import EmptyList from "@/components/emptyList/EmptyList.tsx";

export interface ModalReviewProps {
    closeModal: () => void;
    passangers: PassangerModel[];
    driver: userPublicModel;
    isDriver: boolean;
}

const ModalReview= ({ closeModal, passangers, driver, isDriver}: ModalReviewProps) => {
    const { t } = useTranslation();

    return (
        <div className={styles.propProfile}>
            <Modal.Header closeButton>
                <Modal.Title><h2 className={styles.titleModal}>{t('modal.review.title')}</h2></Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className={styles.categoryContainer}>
                    {!isDriver &&
                        <div className={styles.passangerContainer}>
                            <div className={styles.titleContainer}>
                                <i className="bi bi-person-fill h3"></i>
                                <h3>{t('modal.driver')}</h3>
                            </div>
                            <DriverReportReviewComponent driver={driver} closeModal={closeModal}/>
                        </div>}
                    {passangers.length != 0 &&
                        <div className={styles.passangerContainer}>
                            <div className={styles.titleContainer}>
                                <i className="bi bi-people-fill h3"></i>
                                <h3><h3>{t('modal.passangers')}</h3></h3>
                            </div>
                        </div>}
                    {isDriver && passangers.length == 0 &&
                        <EmptyList
                            text={t("modal.report.empty")}
                            icon={"people-fill"}
                        />}
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
