import { months } from "@/utils/date/months";
import { weekDays } from "@/utils/date/weekDays";
import { Button, Form, InputGroup } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { BsCalendarFill } from "react-icons/bs";
import ReactDatePicker, {
  CalendarProps,
  DateObject,
  DatePickerProps,
} from "react-multi-date-picker";

interface IDatePickerProps {
  onPick: (date: Date | undefined) => void;
}

const DatePicker = ({
  onPick,
  placeholder,
  disabled = false,
  inputClass,
  ...props
}: Omit<CalendarProps, "onChange"> & DatePickerProps & IDatePickerProps) => {
  const { t } = useTranslation();

  const tWeekDays = weekDays.map((day) => t(`day.short.${day.toLowerCase()}`));
  const tMonths = months.map((month) => t(`month.full.${month.toLowerCase()}`));

  return (
    <ReactDatePicker
      {...props}
      calendarPosition="bottom"
      fixMainPosition={true}
      weekDays={tWeekDays}
      months={tMonths}
      disabled={disabled}
      onOpenPickNewDate={false}
      format="DD/MM/YYYY"
      onChange={(date: DateObject) => onPick(date?.toDate())}
      render={(value, onFocus, onChange) => (
        <InputGroup size="sm" className={inputClass}>
          <Button className="secondary-btn" onClick={onFocus}>
            <BsCalendarFill className="light-text" />
          </Button>
          <Form.Control
            type="text"
            placeholder={placeholder}
            size="sm"
            onFocus={onFocus}
            value={value || ""}
            onChange={onChange}
            disabled={disabled}
          />
        </InputGroup>
      )}
    />
  );
};

export default DatePicker;
