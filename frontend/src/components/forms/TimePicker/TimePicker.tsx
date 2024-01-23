import { useRef } from "react";
import { Button, Form, InputGroup } from "react-bootstrap";
import { BiCheck } from "react-icons/bi";
import { BsClockFill } from "react-icons/bs";
import ReactDatePicker, {
  CalendarProps,
  DateObject,
  DatePickerProps,
  DatePickerRef,
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
  const timePickerRef = useRef<DatePickerRef>();
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
      ref={timePickerRef}
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
    >
      <hr className="primary-text mt-1" />
      <Button
        className="w-100"
        onClick={() => timePickerRef.current?.closeCalendar()}
        variant="white"
        size="sm"
      >
        <BiCheck className="primary-text h5" />
      </Button>
    </ReactDatePicker>
  );
};

export default TimePicker;
