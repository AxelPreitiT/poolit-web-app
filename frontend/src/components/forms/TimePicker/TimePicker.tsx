import ReactDatePicker, { DateObject } from "react-multi-date-picker";
import ReactTimePicker from "react-multi-date-picker/plugins/time_picker";

interface TimePickerProps {
  inputClass?: string;
  placeholder?: string;
  containerClassName?: string;
  value?: string;
  onChange: (value: string) => void;
}

const TimePicker = ({
  inputClass,
  placeholder,
  containerClassName,
  value,
  onChange,
}: TimePickerProps) => (
  <ReactDatePicker
    disableDayPicker
    value={value || ""}
    onChange={(date: DateObject) => onChange(date?.toString())}
    format={"HH:mm"}
    inputClass={inputClass}
    placeholder={placeholder}
    containerClassName={containerClassName}
    plugins={[<ReactTimePicker hideSeconds />]}
  />
);

export default TimePicker;
