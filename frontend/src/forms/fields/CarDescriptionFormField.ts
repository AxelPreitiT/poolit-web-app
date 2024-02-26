import { ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type CarDescriptionZodType = ZodString;
const carDescriptionMinLength: number = 5;
const carDescriptionMaxLength: number = 100;

export default class CarDescriptionFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: CarDescriptionZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(carDescriptionMinLength)
      .hasMaxLength(carDescriptionMaxLength)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): CarDescriptionZodType {
    return this.schema;
  }
}
