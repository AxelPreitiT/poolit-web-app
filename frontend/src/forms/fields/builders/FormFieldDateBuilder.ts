import { ZodDate, z } from "zod";
import FormFieldBuilder from "./FormFieldBuilder";
import MinDateFieldInterpolation from "../interpolations/MinDateFieldInterpolation";
import MaxDateFieldInterpolation from "../interpolations/MaxDateFieldInterpolation";

class FormFieldDateBuilder extends FormFieldBuilder<ZodDate> {
  constructor(name: string) {
    super(
      z.coerce.date({
        required_error: `error.${name}.required`,
        invalid_type_error: `error.${name}.invalid`,
      }),
      name
    );
  }

  public hasMinDate(minValue: Date): FormFieldDateBuilder {
    this.setSchema(this.schema.min, {
      value: minValue,
      message: `error.${this.name}.${MinDateFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder<Date>(
      this.interpolationsBuilder.setMinDate,
      minValue
    );
    return this;
  }

  public hasMaxDate(maxValue: Date): FormFieldDateBuilder {
    this.setSchema(this.schema.max, {
      value: maxValue,
      message: `error.${this.name}.${MaxDateFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMaxDate,
      maxValue
    );
    return this;
  }
}

export default FormFieldDateBuilder;
