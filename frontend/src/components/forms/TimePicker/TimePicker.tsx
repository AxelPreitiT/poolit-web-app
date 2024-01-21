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
    />
  );
};

export default TimePicker;
