import FieldInterpolation from "./FieldInterpolation";

class MinValueFieldInterpolation extends FieldInterpolation {
  public static readonly KEY: string = "min";

  constructor(value: number) {
    super(MinValueFieldInterpolation.KEY, value);
  }
}

export default MinValueFieldInterpolation;
