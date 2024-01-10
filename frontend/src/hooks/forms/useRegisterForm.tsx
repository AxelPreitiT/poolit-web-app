import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import {
  RegisterForm,
  RegisterFormFieldsType,
  RegisterFormSchema,
  RegisterFormSchemaType,
} from "@/forms/RegisterForm";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { useTranslation } from "react-i18next";
import useForm, { SubmitHandlerReturnModel } from "./useForm";

const useRegisterForm = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandlerReturnModel<RegisterFormSchemaType> = async (
    data: RegisterFormSchemaType
  ) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.ERROR,
        message: t("register.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return useForm<RegisterFormFieldsType, RegisterFormSchemaType>({
    form: RegisterForm,
    formSchema: RegisterFormSchema,
    onSubmit,
  });
};

export default useRegisterForm;
