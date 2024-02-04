export const getToday = (): Date => {
  const now: Date = new Date();
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
  return today;
};
