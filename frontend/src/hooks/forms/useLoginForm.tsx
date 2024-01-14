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
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import useAuthentication from "../api/useAuthentication";
import AccountNotVerifiedError from "@/errors/AccountNotVerifiedError";
import useQueryError from "../errors/useQueryError";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useLoginForm = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const showSuccessToast = useSuccessToast();
  const { invalidateAuthentication } = useAuthentication();
  const onQueryError = useQueryError();

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
    const from = location.state?.from;
    navigate(from || homePath, {
      replace: true,
    });
    await invalidateAuthentication();
  };

  const onError = (error: QueryError) => {
    const title = t("login.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnauthorizedResponseError.ERROR_ID]: "login.error.unauthorized",
      [AccountNotVerifiedError.ERROR_ID]: "login.error.account_not_verified",
      [UnknownResponseError.ERROR_ID]: "login.error.default",
    };
    onQueryError({ error, title, timeout, customMessages });
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
