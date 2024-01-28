import {useState} from "react";
import styles from "./styles.module.scss";
import {Form, FormSelect} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import ReportsOptions from "@/enums/ReportsOptions.ts";


const ReviewForm = () => {
    const { t } = useTranslation();
    const [selectedOption, setSelectedOption] = useState<ReportsOptions>(ReportsOptions.HARASSMENT);

    const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedOption(e.target.value as ReportsOptions);
    };


    return (
        <div className={styles.picker_style}>
            <div>
                <label className="report-option-label">
                    <strong className="text">{t('reports.option_selector')}</strong>
                </label>
                <FormSelect
                    id="reportPicker"
                    value={selectedOption}
                    onChange={handleOptionChange}
                >
                    {Object.values(ReportsOptions).map((option) => (
                        <option key={option} value={option}>
                            {t(`reports.${option}`)}
                        </option>
                    ))}
                </FormSelect>
            </div>
            <div>
                <label className="report-option-label">
                    <strong className="text">{t("reviews.rating")}</strong>
                </label>

            </div>
            <div>
                <label className="report-option-label">
                    <strong className="text">{t('reports.control_label')}</strong>
                </label>
                <Form.Control as="textarea" rows={3}/>
            </div>
        </div>
    );
};

export default ReviewForm;
