import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import {
  VerifyAccountForm,
  VerifyAccountFormFieldsType,
  VerifyAccountFormSchema,
  VerifyAccountFormSchemaType,
} from "@/forms/VerifyAccountForm";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { SubmitHandler } from "react-hook-form";
import { useTranslation } from "react-i18next";
import useForm from "./useForm";

const useVerifyAccountForm = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandler<VerifyAccountFormSchemaType> = async (data) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.Error,
        message: t("verify_account.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return useForm<VerifyAccountFormFieldsType, VerifyAccountFormSchemaType>(
    VerifyAccountForm,
    VerifyAccountFormSchema,
    onSubmit
  );
};

export default useVerifyAccountForm;
