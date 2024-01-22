export const getNextDay = (date: Date): Date => {
  const tomorrow = new Date(date);
  tomorrow.setDate(tomorrow.getDate() + 1);
  return tomorrow;
};
