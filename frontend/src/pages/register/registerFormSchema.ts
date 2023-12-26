import i18next from "i18next";
import { z } from "zod";

const imageMaxSize = 10 * 1024 * 1024; // 10 MB
const nameMinLength = 2;
const nameMaxLength = 20;
const lastNameMinLength = 2;
const lastNameMaxLength = 30;
const emailMaxLength = 50;
const telephoneRegExp = /^(\+\d{1,3}\s?)?\d{2,4}\s?\d{4}\s?\d{4}$/;
const passwordMinLength = 3;
const passwordMaxLength = 20;
const cityMinId = 1;
const locales = [z.literal("en"), z.literal("es")] as const;

// Interpolations for i18next
const interpolations: Record<string, Record<string, number>> = {
  profile_pic: {
    max_size: imageMaxSize / 1024 / 1024,
  },
  name: {
    min: nameMinLength,
    max: nameMaxLength,
  },
  last_name: {
    min: lastNameMinLength,
    max: lastNameMaxLength,
  },
  email: {
    max: emailMaxLength,
  },
  telephone: {},
  password: {
    min: passwordMinLength,
    max: passwordMaxLength,
  },
  confirm_password: {},
  city_id: {
    min: cityMinId,
  },
  locale: {},
};

export const RegisterFormSchema = z
  .object({
    profilePic: z.custom<File>(
      (file) => {
        return !file || (file instanceof File && file.size <= imageMaxSize);
      },
      {
        message: "error.profile_pic.max_size",
      }
    ),
    name: z
      .string()
      .min(1, {
        message: "error.name.required",
      })
      .min(nameMinLength, {
        message: "error.name.min",
      })
      .max(nameMaxLength, {
        message: "error.name.max",
      })
      .trim(),
    lastName: z
      .string()
      .min(1, {
        message: "error.last_name.required",
      })
      .min(lastNameMinLength, {
        message: "error.last_name.min",
      })
      .max(lastNameMaxLength, {
        message: "error.last_name.max",
      })
      .trim(),
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
    telephone: z
      .string()
      .min(1, {
        message: "error.telephone.required",
      })
      .regex(telephoneRegExp, {
        message: "error.telephone.invalid",
      })
      .trim(),
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
    confirmPassword: z.string(),
    cityId: z.preprocess(
      (val) => parseInt(z.string().parse(val), 10),
      z.number().min(cityMinId, {
        message: "error.city_id.required",
      })
    ),
    locale: z.union(locales).default("en"),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "error.confirm_password.invalid",
    path: ["confirmPassword"],
  });

export type RegisterFormSchemaType = z.infer<typeof RegisterFormSchema>;

// This function is used to translate the error messages
// and interpolate the values if needed (the key has interpolation values)
export const tFormError = (key: string | undefined) =>
  key && i18next.t(key, interpolations[key.split(".")[1]]);
