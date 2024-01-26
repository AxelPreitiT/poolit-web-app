import styles from "./styles.module.scss";
import PassangerModel from "@/models/PassangerModel.ts";
import usePublicUserByUri from "@/hooks/users/usePublicUserByUri.tsx";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import getFormattedDateTime from "@/functions/DateFormat.ts";
import {useTranslation} from "react-i18next";

export interface CreateReportComponentProps {
    passanger: PassangerModel;
    closeModal: () => void;
}

const CreateReportComponent = ({ passanger, closeModal }: CreateReportComponentProps) => {
    const { t } = useTranslation();
    const {isLoading, data:user} = usePublicUserByUri(passanger.userUri);

    return (
        <div className={styles.marginCointainer}>
            {(isLoading || user == undefined) ? <div>Loading...</div> :
            <Button onClick={closeModal} className={styles.userContainer}>
                <CircleImg src={user.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>{user.username}</h4>
                    {passanger.startDateTime == passanger.endDateTime ?
                        <span className={styles.spanStyle}>{getFormattedDateTime(passanger.startDateTime).date}</span> :
                        <span className={styles.spanStyle}>
                          {t("format.recurrent_date", {
                              initial_date: getFormattedDateTime(passanger.startDateTime).date,
                              final_date: getFormattedDateTime(passanger.endDateTime).date,
                          })}
                        </span>}
                </div>
            </Button>}
        </div>
    );
};


export default CreateReportComponent;
