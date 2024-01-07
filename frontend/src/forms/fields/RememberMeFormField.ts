import { ZodBoolean, ZodOptional, z } from "zod";
import FormField from "./FormField";

export type RememberMeZodType = ZodOptional<ZodBoolean>;

export default class RememberMeFormField extends FormField {
  constructor(name: string) {
    super(name);
  }

  public getSchema(): RememberMeZodType {
    return z.boolean().optional();
  }
}
