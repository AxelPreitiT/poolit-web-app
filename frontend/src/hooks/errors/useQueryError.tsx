import { loginPath } from "@/AppRouter";
import QueryError from "@/errors/QueryError";
import RequestError from "@/errors/RequestError";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import { useLocation, useNavigate } from "react-router-dom";
import useErrorToast from "../toasts/useErrorToast";
import ResponseError from "@/errors/ResponseError";
import CurrentUserUriMissingError from "@/errors/CurrentUserUriMissingError";
import { useTranslation } from "react-i18next";

const useQueryError = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const showErrorToast = useErrorToast();

  const onQueryError = ({
    error,
    timeout,
    title,
  }: {
    error: QueryError;
    timeout?: number;
    title?: string;
  }) => {
    showErrorToast({
      message: t(error.getI18nKey()),
      timeout,
      title,
    });

    if (
      ((error instanceof ResponseError &&
        error instanceof UnauthorizedResponseError) ||
        (error instanceof RequestError &&
          error instanceof CurrentUserUriMissingError)) &&
      location.pathname !== loginPath
    ) {
      navigate(loginPath, { state: { from: location }, replace: true });
    }
  };

  return onQueryError;
};

export default useQueryError;
