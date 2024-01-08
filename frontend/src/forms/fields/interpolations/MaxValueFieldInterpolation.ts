import FieldInterpolation from "./FieldInterpolation";

class MaxValueFieldInterpolation extends FieldInterpolation {
  public static readonly KEY: string = "max";

  constructor(value: number) {
    super(MaxValueFieldInterpolation.KEY, value);
  }
}

export default MaxValueFieldInterpolation;
