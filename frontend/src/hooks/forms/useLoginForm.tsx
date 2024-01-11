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
import ResponseError from "@/errors/ResponseError";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";

const useLoginForm = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const showErrorToast = useErrorToast();
  const showSuccessToast = useSuccessToast();

  const onSubmit: SubmitHandlerReturnModel<LoginFormSchemaType> = async (
    data: LoginFormSchemaType
  ) => {
    await UserService.login(data.email, data.password, !!data.rememberMe);
  };

  const onSuccess = () => {
    showSuccessToast({
      message: t("login.success"),
      timeout: defaultToastTimeout,
    });
    const from = location.state?.from?.pathname;
    navigate(from || homePath, {
      replace: true,
    });
  };

  const onError = (error: QueryError) => {
    showErrorToast({
      message: t(
        error instanceof ResponseError &&
          error instanceof UnauthorizedResponseError
          ? "login.error.unauthorized"
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
