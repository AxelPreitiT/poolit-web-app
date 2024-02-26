import { getDatetime } from "@/utils/date/datetime";

const nowOrLaterRefine =
  <A extends object>(dateField: keyof A, timeField: keyof A) =>
  (data: A) => {
    if (!data[dateField] || !data[timeField]) {
      return true;
    }
    const dateWithTime = getDatetime(
      data[dateField] as Date,
      data[timeField] as string
    );
    const now = new Date();
    return dateWithTime >= now;
  };

export default nowOrLaterRefine;
