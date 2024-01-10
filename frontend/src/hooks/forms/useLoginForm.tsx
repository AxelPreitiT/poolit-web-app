import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import {
  LoginForm,
  LoginFormFieldsType,
  LoginFormSchema,
  LoginFormSchemaType,
} from "@/forms/LoginForm";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import UsersApi from "@/api/UsersApi";
import useSuccessToast from "../toasts/useSuccessToast";
import { useLocation, useNavigate } from "react-router-dom";
import { homePath } from "@/AppRouter";

const useLoginForm = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const showSuccessToast = useSuccessToast();

  const onSubmit: SubmitHandlerReturnModel<LoginFormSchemaType> = async (
    data: LoginFormSchemaType
  ) => {
    await UsersApi.login(data.email, data.password, !!data.rememberMe);
  };

  const onSuccess = () => {
    showSuccessToast("login.success", defaultToastTimeout);
    const from = location.state?.from?.pathname;
    navigate(from || homePath, {
      replace: !!from,
    });
  };

  return useForm<LoginFormFieldsType, LoginFormSchemaType>({
    form: LoginForm,
    formSchema: LoginFormSchema,
    onSubmit,
    onSuccess,
  });
};

export default useLoginForm;
