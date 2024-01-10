import { loginPath } from "@/AppRouter";
import QueryError from "@/errors/QueryError";
import RequestError from "@/errors/RequestError";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import { useLocation, useNavigate } from "react-router-dom";
import useErrorToast from "../toasts/useErrorToast";

const useQueryError = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const showErrorToast = useErrorToast();

  const onQueryError = (error: QueryError, timeout?: number) => {
    showErrorToast(error.getI18nKey(), timeout);

    if (
      error instanceof RequestError &&
      error instanceof UnauthorizedResponseError &&
      location.pathname !== loginPath
    ) {
      navigate(loginPath, { state: { from: location }, replace: true });
    }
  };

  return onQueryError;
};

export default useQueryError;
