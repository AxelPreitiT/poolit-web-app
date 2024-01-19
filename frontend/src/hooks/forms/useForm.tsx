import QueryError from "@/errors/QueryError";
import Form, { FormFieldsType } from "@/forms/Form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FieldError, FieldValues } from "react-hook-form";
import { SubmitHandler, useForm as useReactHookForm } from "react-hook-form";
import { ZodSchema } from "zod";
import { ModelType } from "@/models/ModelType";

export type SubmitHandlerReturnModel<
  F extends FieldValues,
  Model extends ModelType,
> = SubmitHandler<F> & ((data: F) => Promise<Model>);

const useForm = <
  T extends FormFieldsType,
  F extends FieldValues,
  Model extends ModelType,
>({
  form,
  formSchema,
  onSubmit,
  onSuccess,
  onError,
}: {
  form: Form<T>;
  formSchema: ZodSchema;
  onSubmit: SubmitHandlerReturnModel<F, Model>;
  onSuccess?: (data: Model) => void;
  onError?: (error: QueryError) => void;
}) => {
  const { handleSubmit, ...formProps } = useReactHookForm<F>({
    resolver: zodResolver(formSchema),
  });

  const mutation = useMutation({
    mutationFn: onSubmit,
    onError: (error: QueryError) => {
      if (onError) {
        onError(error);
      }
    },
    onSuccess: (data: Model) => {
      if (onSuccess) {
        onSuccess(data);
      }
    },
  });

  const handleFormSubmit = handleSubmit((data: F) => mutation.mutate(data));

  const tFormError = (errorField?: FieldError): string | undefined => {
    const message = errorField?.message;
    if (message && message.startsWith("error.")) {
      return form.tFormError(message as `error.${string}`);
    }
  };

  return { handleSubmit: handleFormSubmit, tFormError, ...formProps };
};

export default useForm;
