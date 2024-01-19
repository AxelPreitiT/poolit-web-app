import { useTranslation } from "react-i18next";
import ReactDatePicker, { DateObject } from "react-multi-date-picker";

interface DatePickerProps {
  inputClass?: string;
  placeholder?: string;
  containerClassName?: string;
  value?: Date;
  onChange: (value: Date) => void;
}

const DatePicker = ({
  inputClass,
  placeholder,
  containerClassName,
  value,
  onChange,
}: DatePickerProps) => {
  const { i18n } = useTranslation();
  return (
    <ReactDatePicker
      value={value || ""}
      onChange={(date: DateObject) => onChange(date?.toDate())}
      format={i18n.language === "en" ? "MM/DD/YYYY" : "DD/MM/YYYY"}
      inputClass={inputClass}
      placeholder={placeholder}
      containerClassName={containerClassName}
    />
  );
};

export default DatePicker;
