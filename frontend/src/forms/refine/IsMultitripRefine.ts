const isMultitripRefine =
  <A extends object>(multitripField: keyof A, lastDateField: keyof A) =>
  (data: A) => {
    const isMultitrip = data[multitripField] as boolean;
    const lastDate = data[lastDateField] as Date | undefined;
    return !isMultitrip || lastDate !== undefined;
  };

export default isMultitripRefine;
