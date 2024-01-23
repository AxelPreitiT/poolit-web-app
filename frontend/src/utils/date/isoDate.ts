const padNumber = (num: number): string => (num < 10 ? `0${num}` : `${num}`);

export const getIsoDate = (date: Date): string =>
  `${date.getFullYear()}-${padNumber(date.getMonth() + 1)}-${padNumber(
    date.getDate()
  )}`;
