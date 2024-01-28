import { ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type CarBrandZodType = ZodString;

export default class CarBrandFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: CarBrandZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): CarBrandZodType {
    return this.schema;
  }
}
