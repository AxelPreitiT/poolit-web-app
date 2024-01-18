import { ZodOptional, ZodType } from "zod";
import FieldInterpolationsBuilder from "../interpolations/FieldInterpolationsBuilder";
import FieldInterpolation from "../interpolations/FieldInterpolation";

abstract class FormFieldBuilder<T extends ZodType | ZodOptional<ZodType>> {
  protected name: string;
  protected schema: T;
  protected interpolationsBuilder: FieldInterpolationsBuilder;
  private builded: boolean;

  constructor(schema: T, name: string) {
    this.schema = schema;
    this.interpolationsBuilder = new FieldInterpolationsBuilder();
    this.builded = false;
    this.name = name;
  }

  protected setSchema<V>(
    callbackFn:
      | ((this: T, val: V, message: string) => T)
      | ((this: T, message: string) => T)
      | ((this: T) => T),
    { value, message }: { value?: V; message?: `error.${string}` } = {}
  ): void {
    if (this.builded) {
      throw new Error("Field already builded");
    }
    if (value && message) {
      this.schema = (
        callbackFn as (this: T, val: V, message: string) => T
      ).call(this.schema, value, message);
    } else if (message) {
      this.schema = (callbackFn as (this: T, message: string) => T).call(
        this.schema,
        message
      );
    } else {
      this.schema = (callbackFn as (this: T) => T).call(this.schema);
    }
  }

  protected setInterpolationsBuilder<V>(
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
