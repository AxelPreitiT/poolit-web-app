import styles from "./styles.module.scss";
import {Form} from "react-bootstrap";
import {useTranslation} from "react-i18next";


const ReportApproveForm = () => {
    const { t } = useTranslation();



    return (
        <div>
            <div>
                <label className="report-option-label">
                    <strong className="text">{t('admin.report.explainApprove')}</strong>
                    <div><span className="italic-text">{t('admin.report.notifyApprove')}</span></div>
                </label>
                <Form.Control as="textarea" rows={3}/>
                <div className={styles.danger}>
                    <i className="bi bi-exclamation-diamond-fill  h6"></i>
                    <span className="h6 mx-1">
                  {t("admin.report.approveWarning")}
                    </span>
                </div>
            </div>
        </div>
    );
};

export default ReportApproveForm;