const integerRegex = /^-?\d+$/;

export const parseInputInteger = (value: string): number | string => {
  if (!integerRegex.test(value)) {
    return value;
  }
  const parsedValue = parseInt(value, 10);
  return isNaN(parsedValue) ? value : parsedValue;
};
