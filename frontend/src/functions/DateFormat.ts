interface FormattedDateTime {
  date: string;
  time: string;
}

const getFormattedDateTime = (
  dateTimeString: string | undefined
): FormattedDateTime => {
  if (!dateTimeString) {
    return { date: "", time: "" };
  }
  // const [date, time] = dateTimeString.split('T');
  // return { date: date || '', time: time || '' };
  // if (!dateTimeString) {
  //   return { date: "", time: "" };
  // }
  const [date, time] = dateTimeString.split("T");
  const [year, month, day] = date ? date.split("-") : ["", "", ""];
  const [hour, minutes] = time ? time.split(":") : ["", ""];

  return {
    date: `${day}/${month}/${year}`,
    time: `${hour}:${minutes}`,
  };
};

export default getFormattedDateTime;
