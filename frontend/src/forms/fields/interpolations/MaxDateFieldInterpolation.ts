import FieldInterpolation from "./FieldInterpolation";

class MaxDateFieldInterpolation extends FieldInterpolation {
  public static readonly KEY: string = "max";

  constructor(value: string) {
    super(MaxDateFieldInterpolation.KEY, value);
  }
}

export default MaxDateFieldInterpolation;
