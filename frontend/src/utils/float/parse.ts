const commaFloatRegex = /^-?\d+(,\d+)?$/;
const dotFloatRegex = /^-?\d+(\.\d+)?$/;

export const parseInputFloat = (
  value: string | number
): number | string | undefined => {
  if (typeof value === "number") {
    return value;
  }
  if (commaFloatRegex.test(value)) {
    return parseFloat(value.replace(",", "."));
  }
  if (dotFloatRegex.test(value)) {
    return parseFloat(value);
  }
  return value || undefined;
};
