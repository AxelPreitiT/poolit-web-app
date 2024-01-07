import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import {
  RegisterForm,
  RegisterFormFieldsType,
  RegisterFormSchema,
  RegisterFormSchemaType,
} from "@/forms/RegisterForm";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { SubmitHandler } from "react-hook-form";
import { useTranslation } from "react-i18next";
import useForm from "./useForm";

const useRegisterForm = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandler<RegisterFormSchemaType> = async (data) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.Error,
        message: t("register.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return useForm<RegisterFormFieldsType, RegisterFormSchemaType>(
    RegisterForm,
    RegisterFormSchema,
    onSubmit
  );
};

export default useRegisterForm;
