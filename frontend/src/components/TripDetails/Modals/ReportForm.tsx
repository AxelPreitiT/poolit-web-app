import ReportsOptions from "@/enums/ReportsOptions.ts";
import {useState} from "react";
import styles from "./styles.module.scss";
import {Form, FormSelect} from "react-bootstrap";


const ReportForm = () => {
    const [selectedOption, setSelectedOption] = useState<ReportsOptions>(ReportsOptions.HARASSMENT);

    const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedOption(e.target.value as ReportsOptions);
    };

    return (
        <div className={styles.picker_style}>
            <div>
                <label className="report-option-label">
                    <strong className="text">Why do you want to report it?</strong>
                </label>
                <FormSelect
                    id="reportPicker"
                    value={selectedOption}
                    onChange={handleOptionChange}
                >
                    {Object.values(ReportsOptions).map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </FormSelect>
            </div>
            <div>
                <label className="report-option-label">
                    <strong className="text">Why do you want to report it?</strong>
                </label>
                <Form.Control as="textarea" rows={3} placeholder={"pepe"} />
            </div>
        </div>
    );
};

export default ReportForm;
