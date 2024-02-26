import { ZodOptional, ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type ReviewCommentZodType = ZodOptional<ZodString>;
const maxLength = 200;

export default class ReviewCommentFormField extends FormField {
  private schema: ReviewCommentZodType;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    const [preSchema, interpolations] = new FormFieldStringBuilder(name)
      .hasMaxLength(maxLength)
      .build();
    this.schema = preSchema.optional();
    this.interpolations = interpolations;
  }

  public getSchema(): ReviewCommentZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
