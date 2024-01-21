import { months } from "@/utils/date/months";
import { weekDays } from "@/utils/date/weekDays";
import { useTranslation } from "react-i18next";
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
  ...props
}: Omit<CalendarProps, "onChange"> & DatePickerProps & IDatePickerProps) => {
  const { t } = useTranslation();

  const tWeekDays = weekDays.map((day) => t(`day.short.${day.toLowerCase()}`));
  const tMonths = months.map((month) => t(`month.full.${month.toLowerCase()}`));

  return (
    <ReactDatePicker
      {...props}
      weekDays={tWeekDays}
      months={tMonths}
      format="DD/MM/YYYY"
      onChange={(date: DateObject) => onPick(date?.toDate())}
    />
  );
};

export default DatePicker;
