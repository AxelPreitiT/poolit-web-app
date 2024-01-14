import { loginPath } from "@/AppRouter";
import QueryError from "@/errors/QueryError";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import { useLocation, useNavigate } from "react-router-dom";
import useErrorToast from "../toasts/useErrorToast";
import CurrentUserUriMissingError from "@/errors/CurrentUserUriMissingError";
import { useTranslation } from "react-i18next";
import ResponseError from "@/errors/ResponseError";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useQueryError = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const showErrorToast = useErrorToast();

  const getMessage = (
    error: QueryError,
    customMessages?: Record<string, string>
  ) => {
    const errorId = error.getErrorId();
    return (
      (customMessages?.[errorId]
        ? t(customMessages[errorId])
        : t(error.getI18nKey())) +
      (error instanceof ResponseError &&
      !(error instanceof UnknownResponseError)
        ? ` (${error.getStatusCode()} - ${error.getStatusText()})`
        : "")
    );
  };

  const onQueryError = ({
    error,
    timeout,
    title,
    customMessages,
  }: {
    error: Error;
    timeout?: number;
    title?: string;
    customMessages?: Record<string, string>;
  }) => {
    const queryError =
      error instanceof QueryError ? error : new UnknownResponseError(error);
    showErrorToast({
      message: getMessage(queryError, customMessages),
      timeout,
      title,
      children: queryError.getChildren(),
    });

    if (
      (error instanceof UnauthorizedResponseError ||
        error instanceof CurrentUserUriMissingError) &&
      location.pathname !== loginPath
    ) {
      navigate(loginPath, { state: { from: location }, replace: true });
    }
  };

  return onQueryError;
};

export default useQueryError;
