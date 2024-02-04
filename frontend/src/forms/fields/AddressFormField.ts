import { ZodString } from "zod";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";

export type AddressZodType = ZodString;
const addressMinLength: number = 5;
const addressMaxLength: number = 50;

export default class AddressFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: AddressZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(addressMinLength)
      .hasMaxLength(addressMaxLength)
      .trim()
      .build();
  }

  public getSchema(): AddressZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
