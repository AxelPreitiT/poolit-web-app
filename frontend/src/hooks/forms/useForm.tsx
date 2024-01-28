import QueryError from "@/errors/QueryError";
import Form, { FormFieldsType } from "@/forms/Form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { DefaultValues, FieldError, FieldValues } from "react-hook-form";
import { SubmitHandler, useForm as useReactHookForm } from "react-hook-form";
import { ZodSchema } from "zod";
import { ModelType } from "@/models/ModelType";
import { useEffect } from "react";

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
  defaultValues,
  executeOnMount = false,
}: {
  form: Form<T>;
  formSchema: ZodSchema<F>;
  onSubmit: SubmitHandlerReturnModel<F, Model>;
  onSuccess?: (data: Model, form: F) => void;
  onError?: (error: QueryError) => void;
  defaultValues?: DefaultValues<F>;
  executeOnMount?: boolean;
}) => {
  const { handleSubmit, ...formProps } = useReactHookForm<F>({
    resolver: zodResolver(formSchema),
    defaultValues,
  });

  const { trigger, getValues } = formProps;

  const mutation = useMutation({
    mutationFn: onSubmit,
    onError: (error: QueryError) => {
      if (onError) {
        onError(error);
      }
    },
    onSuccess: (data: Model, variables: F) => {
      if (onSuccess) {
        onSuccess(data, variables);
      }
    },
  });

  const executeSubmit = (onValid?: () => void) => {
    trigger().then((isValid) => {
      if (isValid) {
        if (onValid) {
          onValid();
        }
        mutation.mutate(getValues());
      }
    });
  };

  useEffect(() => {
    if (defaultValues && executeOnMount) {
      executeSubmit();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const isFetching = mutation.isPending;

  const handleFormSubmit = handleSubmit((data: F) => mutation.mutate(data));

  const tFormError = (errorField?: FieldError): string | undefined => {
    const message = errorField?.message;
    if (message && message.startsWith("error.")) {
      return form.tFormError(message as `error.${string}`);
    }
  };

  return {
    handleSubmit: handleFormSubmit,
    tFormError,
    isFetching,
    executeSubmit,
    ...formProps,
  };
};

export default useForm;
