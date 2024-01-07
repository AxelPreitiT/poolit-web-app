import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import {
  LoginForm,
  LoginFormFieldsType,
  LoginFormSchema,
  LoginFormSchemaType,
} from "@/forms/LoginForm";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { SubmitHandler } from "react-hook-form";
import { useTranslation } from "react-i18next";
import useForm from "./useForm";

const useLoginForm = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandler<LoginFormSchemaType> = async (data) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.Error,
        message: t("login.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return useForm<LoginFormFieldsType, LoginFormSchemaType>(
    LoginForm,
    LoginFormSchema,
    onSubmit
  );
};

export default useLoginForm;
