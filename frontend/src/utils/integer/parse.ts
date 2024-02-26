const integerRegex = /^-?\d+$/;

export const parseInputInteger = (
  value: string | number
): number | string | undefined => {
  if (typeof value === "number") {
    return value;
  }
  if (!integerRegex.test(value)) {
    return value || undefined;
  }
  const parsedValue = parseInt(value, 10);
  return isNaN(parsedValue) ? value || undefined : parsedValue;
};
