import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import {
  LoginForm,
  LoginFormFieldsType,
  LoginFormSchema,
  LoginFormSchemaType,
} from "@/forms/LoginForm";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import useSuccessToast from "../toasts/useSuccessToast";
import { useLocation, useNavigate } from "react-router-dom";
import { homePath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import UserService from "@/services/UserService";
import QueryError from "@/errors/QueryError";
import useErrorToast from "../toasts/useErrorToast";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import useAuthentication from "../api/useAuthentication";
import AccountNotVerifiedError from "@/errors/AccountNotVerifiedError";

const useLoginForm = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const showErrorToast = useErrorToast();
  const showSuccessToast = useSuccessToast();
  const { retryAuthentication } = useAuthentication();

  const onSubmit: SubmitHandlerReturnModel<LoginFormSchemaType> = async (
    data: LoginFormSchemaType
  ) => {
    await UserService.login(data.email, data.password, !!data.rememberMe);
  };

  const onSuccess = async () => {
    showSuccessToast({
      title: t("login.success.title"),
      message: t("login.success.message"),
      timeout: defaultToastTimeout,
    });
    await retryAuthentication();
    const from = location.state?.from;
    navigate(from || homePath, {
      replace: true,
    });
  };

  const onError = (error: QueryError) => {
    showErrorToast({
      message: t(
        error instanceof UnauthorizedResponseError
          ? "login.error.unauthorized"
          : error instanceof AccountNotVerifiedError
          ? "login.error.account_not_verified"
          : "login.error.default"
      ),
      timeout: defaultToastTimeout,
      title: t("login.error.title"),
    });
  };

  return useForm<LoginFormFieldsType, LoginFormSchemaType>({
    form: LoginForm,
    formSchema: LoginFormSchema,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useLoginForm;
