export const ddmmyyyyToDate = (date: string): Date => {
  const [day, month, year] = date.split("/");
  return new Date(`${year}-${month}-${day}`);
};

export const dateToDdmmyyyy = (date: Date): string => {
  const [year, month, day] = date.toISOString().split("T")[0].split("-");
  return `${day}/${month}/${year}`;
};
