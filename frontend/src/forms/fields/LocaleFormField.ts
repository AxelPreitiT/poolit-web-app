import { ZodLiteral, ZodUnion, z } from "zod";
import FormField from "./FormField";

export type LocaleZodType = ZodUnion<
  readonly [ZodLiteral<string>, ZodLiteral<string>]
>;
const locales: readonly [ZodLiteral<string>, ZodLiteral<string>] = [
  z.literal("en"),
  z.literal("es"),
] as const;

export default class LocaleFormField extends FormField {
  constructor(name: string) {
    super(name);
  }

  public getSchema(): LocaleZodType {
    return z.union(locales);
  }
}
