import { ZodNumber, z } from "zod";
import FormFieldBuilder from "./FormFieldBuilder";
import MinValueFieldInterpolation from "../interpolations/MinValueFieldInterpolation";
import MaxValueFieldInterpolation from "../interpolations/MaxValueFieldInterpolation";

class FormFieldNumberBuilder extends FormFieldBuilder<ZodNumber> {
  constructor(name: string) {
    super(
      z.number({
        errorMap: (error, ctx) => {
          if (ctx.data === undefined && error.code === "invalid_type") {
            return {
              message: `error.${this.name}.required`,
            };
          }
          let message: string;
          switch (error.code) {
            case "too_small":
              message = `error.${this.name}.${MinValueFieldInterpolation.KEY}`;
              break;
            case "too_big":
              message = `error.${this.name}.${MaxValueFieldInterpolation.KEY}`;
              break;
            default:
              message = `error.${this.name}.invalid`;
              break;
          }
          return { message };
        },
      }),
      name
    );
  }

  public hasMinValue(minValue: number): FormFieldNumberBuilder {
    this.setSchema(this.schema.min, {
      value: minValue - 0.000000000000001,
      message: `error.${this.name}.${MinValueFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMinValue,
      minValue
    );
    return this;
  }

  public hasMaxValue(maxValue: number): FormFieldNumberBuilder {
    this.setSchema(this.schema.max, {
      value: maxValue,
      message: `error.${this.name}.${MaxValueFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMaxValue,
      maxValue
    );
    return this;
  }
}

export default FormFieldNumberBuilder;
