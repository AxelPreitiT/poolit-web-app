const commaFloatRegex = /^-?\d+(,\d+)?$/;
const dotFloatRegex = /^-?\d+(\.\d+)?$/;

export const parseInputFloat = (value: string): number | string => {
  if (commaFloatRegex.test(value)) {
    return parseFloat(value.replace(",", "."));
  }
  if (dotFloatRegex.test(value)) {
    return parseFloat(value);
  }
  return value;
};
