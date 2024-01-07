import FieldInterpolation from "./FieldInterpolation";

class RegexFieldInterpolation extends FieldInterpolation {
  public static readonly KEY: string = "format";

  constructor(format: string) {
    super(RegexFieldInterpolation.KEY, format);
  }
}

export default RegexFieldInterpolation;
