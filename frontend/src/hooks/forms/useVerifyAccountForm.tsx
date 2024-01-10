import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import ToastType from "@/enums/ToastType";
import {
  VerifyAccountForm,
  VerifyAccountFormFieldsType,
  VerifyAccountFormSchema,
  VerifyAccountFormSchemaType,
} from "@/forms/VerifyAccountForm";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { useTranslation } from "react-i18next";
import useForm, { SubmitHandlerReturnModel } from "./useForm";

const useVerifyAccountForm = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const onSubmit: SubmitHandlerReturnModel<
    VerifyAccountFormSchemaType
  > = async (data) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)).then(() => {
      console.log(data);
      addToast({
        type: ToastType.ERROR,
        message: t("verify_account.error"),
        timeout: defaultToastTimeout,
      });
    });
  };

  return useForm<VerifyAccountFormFieldsType, VerifyAccountFormSchemaType>({
    form: VerifyAccountForm,
    formSchema: VerifyAccountFormSchema,
    onSubmit,
  });
};

export default useVerifyAccountForm;
