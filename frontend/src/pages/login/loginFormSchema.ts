import i18next from "i18next";
import { z } from "zod";

const passwordMinLength: number = 3;
const passwordMaxLength: number = 20;

// Interpolations for i18next
const interpolations: Record<string, Record<string, number>> = {
  email: {},
  password: {
    min: passwordMinLength,
    max: passwordMaxLength,
  },
};

export const LoginFormSchema = z.object({
  email: z
    .string()
    .min(1, {
      message: "error.email.required",
    })
    .email({
      message: "error.email.invalid",
    })
    .trim()
    .toLowerCase(),
  password: z
    .string()
    .min(1, {
      message: "error.password.required",
    })
    .min(passwordMinLength, {
      message: "error.password.min",
    })
    .max(passwordMaxLength, {
      message: "error.password.max",
    }),
  rememberMe: z.boolean().optional(),
});

export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;

// This function is used to translate the error messages
// and interpolate the values if needed (the key has interpolation values)
export const tFormError = (key: string | undefined) =>
  key && i18next.t(key, interpolations[key.split(".")[1]]);
