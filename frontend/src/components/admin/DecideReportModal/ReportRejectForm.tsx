import styles from "./styles.module.scss";
import {Form} from "react-bootstrap";
import {useTranslation} from "react-i18next";


const ReportRejectForm = () => {
    const { t } = useTranslation();



    return (
        <div>
            <div>
                <label className="report-option-label">
                    <strong className="text">{t('admin.report.explainReject')}</strong>
                    <span className={styles.italic_text}>{t('admin.report.notifyReject')}</span>
                </label>
                <Form.Control as="textarea" rows={3}/>
            </div>
        </div>
    );
};

export default ReportRejectForm;