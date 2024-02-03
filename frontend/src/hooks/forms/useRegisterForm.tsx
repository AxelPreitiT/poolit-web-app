import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import {
  RegisterForm,
  RegisterFormSchema,
  RegisterFormSchemaType,
} from "@/forms/RegisterForm";
import { useTranslation } from "react-i18next";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import UserService from "@/services/UserService";
import useSuccessToast from "../toasts/useSuccessToast";
import { useNavigate } from "react-router-dom";
import { loginPath } from "@/AppRouter";
import ConflictResponseError from "@/errors/ConflictResponseError";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useQueryError from "../errors/useQueryError";
import QueryError from "@/errors/QueryError";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";

const useRegisterForm = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { discovery, isError: isDiscoveryError } = useDiscovery();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<
    RegisterFormSchemaType,
    void
  > = async (data: RegisterFormSchemaType) => {
    if (!discovery || isDiscoveryError) {
      throw new DiscoveryMissingError();
    }
    return await UserService.register(discovery.usersUriTemplate, data);
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("register.success.title"),
      message: t("register.success.message"),
      timeout: defaultToastTimeout,
    });
    navigate(loginPath, { replace: true });
  };

  const onError = (error: QueryError) => {
    const title = t("register.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [ConflictResponseError.ERROR_ID]: "register.error.email_already_exists",
      [UnknownResponseError.ERROR_ID]: "register.error.default",
    };
    onQueryError({ error, title, timeout, customMessages });
  };

  return useForm({
    form: RegisterForm,
    formSchema: RegisterFormSchema,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useRegisterForm;
