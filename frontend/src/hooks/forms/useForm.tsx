import { zodResolver } from "@hookform/resolvers/zod";
import { FieldError, FieldValues } from "react-hook-form";
import { SubmitHandler, useForm as useReactHookForm } from "react-hook-form";
import { ZodSchema } from "zod";

const useForm = <F extends FieldValues>(
  formSchema: ZodSchema,
  onSubmit: SubmitHandler<F>
) => {
  const { handleSubmit, ...formProps } = useReactHookForm<F>({
    resolver: zodResolver(formSchema),
  });

  const handleFormSubmit = handleSubmit(onSubmit);

  const tFormError = (errorField?: FieldError): string | undefined => {
    const message = errorField?.message;
    if (message && message.startsWith("error.")) {
      return message;
    }
  };

  return { handleSubmit: handleFormSubmit, tFormError, ...formProps };
};

export default useForm;
