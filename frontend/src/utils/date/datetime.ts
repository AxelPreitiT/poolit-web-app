export const getDatetime = (date: Date, time: string): Date => {
  const [hours, minutes] = time.split(":");
  const datetime = new Date(date).setHours(Number(hours), Number(minutes));
  return new Date(datetime);
};
