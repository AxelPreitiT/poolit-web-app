import { ZodBoolean, ZodOptional, z } from "zod";
import FormField from "./FormField";

export type MultitripZodType = ZodOptional<ZodBoolean>;

export default class MultitripFormField extends FormField {
  constructor(name: string) {
    super(name);
  }

  public getSchema(): MultitripZodType {
    return z.boolean().optional();
  }
}
