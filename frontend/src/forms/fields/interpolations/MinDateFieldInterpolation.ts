import FieldInterpolation from "./FieldInterpolation";

class MinDateFieldInterpolation extends FieldInterpolation {
  public static readonly KEY: string = "min";

  constructor(value: string) {
    super(MinDateFieldInterpolation.KEY, value);
  }
}

export default MinDateFieldInterpolation;
