import { Button, Form, InputGroup } from "react-bootstrap";
import { BsClockFill } from "react-icons/bs";
import ReactDatePicker, {
  CalendarProps,
  DateObject,
  DatePickerProps,
} from "react-multi-date-picker";
import ReactTimePicker from "react-multi-date-picker/plugins/time_picker";

interface ITimePickerProps {
  onPick: (time: string) => void;
  value: string;
}

const TimePicker = ({
  onPick,
  value,
  inputClass,
  placeholder,
  ...props
}: Omit<CalendarProps, "onChange"> & DatePickerProps & ITimePickerProps) => {
  const [hours, minutes] = (value || "").split(":");
  const timeValue =
    hours && minutes
      ? new DateObject().set({
          hour: parseInt(hours),
          minute: parseInt(minutes),
        })
      : undefined;
  return (
    <ReactDatePicker
      {...props}
      disableDayPicker
      format="HH:mm"
      onChange={(date: DateObject) => onPick(date?.toString() || "")}
      plugins={[<ReactTimePicker hideSeconds mStep={5} />]}
      value={timeValue}
      render={(value, onFocus, onChange) => (
        <InputGroup size="sm" className={inputClass}>
          <Button className="secondary-btn" onClick={onFocus}>
            <BsClockFill className="light-text" />
          </Button>
          <Form.Control
            type="text"
            placeholder={placeholder}
            size="sm"
            onFocus={onFocus}
            value={value || ""}
            onChange={onChange}
          />
        </InputGroup>
      )}
    />
  );
};

export default TimePicker;
