const sameWeekDayRefine =
  <T extends object>(initialDateField: keyof T, finalDateField: keyof T) =>
  (form: T) => {
    const initialDate = form[initialDateField] as Date;
    const finalDate = form[finalDateField] as Date | undefined | null;
    return (
      finalDate === null ||
      finalDate === undefined ||
      initialDate.getDay() === finalDate.getDay()
    );
  };

export default sameWeekDayRefine;
