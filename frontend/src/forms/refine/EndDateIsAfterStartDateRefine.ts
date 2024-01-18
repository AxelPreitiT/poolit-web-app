const endDateIsAfterStartDateRefine =
  <T extends object>(startDateField: keyof T, endDateField: keyof T) =>
  (form: T) => {
    const startDateValue = form[startDateField] as Date;
    const endDateValue = form[endDateField] as Date | undefined;
    return endDateValue === undefined || startDateValue < endDateValue;
  };

export default endDateIsAfterStartDateRefine;
