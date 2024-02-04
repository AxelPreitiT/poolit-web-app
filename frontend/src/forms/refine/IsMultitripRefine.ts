const isMultitripRefine =
  <A extends object>(multitripField: keyof A, lastDateField: keyof A) =>
  (data: A) => {
    const isMultitrip = data[multitripField] as boolean;
    const lastDate = data[lastDateField] as Date | undefined | null;
    return !isMultitrip || (lastDate !== undefined && lastDate !== null);
  };

export default isMultitripRefine;
