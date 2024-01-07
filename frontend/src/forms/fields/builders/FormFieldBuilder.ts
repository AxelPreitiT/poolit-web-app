import { ZodType } from "zod";
import FieldInterpolationsBuilder from "../interpolations/FieldInterpolationsBuilder";
import FieldInterpolation, {
  FieldInterpolationValue,
} from "../interpolations/FieldInterpolation";

abstract class FormFieldBuilder<T extends ZodType> {
  protected schema: T;
  protected interpolationsBuilder: FieldInterpolationsBuilder;
  private builded: boolean;

  constructor(schema: T) {
    this.schema = schema;
    this.interpolationsBuilder = new FieldInterpolationsBuilder();
    this.builded = false;
  }

  protected setSchema<V>(
    callbackFn:
      | ((this: T, val: V, message: string) => T)
      | ((this: T, message: string) => T)
      | ((this: T) => T),
    value?: V,
    message?: `error.${string}`
  ): void {
    if (this.builded) {
      throw new Error("Field already builded");
    }
    if (value && message) {
      (callbackFn as (this: T, val: V, message: string) => T).call(
        this.schema,
        value,
        message
      );
    } else if (message) {
      (callbackFn as (this: T, message: string) => T).call(
        this.schema,
        message
      );
    } else {
      (callbackFn as (this: T) => T).call(this.schema);
    }
  }

  protected setInterpolationsBuilder<V extends FieldInterpolationValue>(
    callbackFn: (this: FieldInterpolationsBuilder, value: V) => void,
    value: V
  ): void {
    if (this.builded) {
      throw new Error("Field already builded");
    }
    callbackFn.call(this.interpolationsBuilder, value);
  }

  public build(): [T, FieldInterpolation[]] {
    this.builded = true;
    return [this.schema, this.interpolationsBuilder.build()];
  }
}

export default FormFieldBuilder;
