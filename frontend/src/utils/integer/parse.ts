const integerRegex = /^-?\d+$/;

export const parseInputInteger = (value: string): number | undefined => {
  if (!integerRegex.test(value)) {
    return undefined;
  }
  const parsedValue = parseInt(value, 10);
  return isNaN(parsedValue) ? undefined : parsedValue;
};
