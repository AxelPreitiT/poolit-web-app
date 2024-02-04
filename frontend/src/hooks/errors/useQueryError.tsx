import QueryError from "@/errors/QueryError";
import useErrorToast from "../toasts/useErrorToast";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError";
import BadRequestResponseError from "@/errors/BadRequestResponseError";
import BadRequestModal from "@/components/forms/BadRequestModal/BadRequestModal";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import CurrentUserUriMissingError from "@/errors/CurrentUserUriMissingError";
import { loginPath } from "@/AppRouter";
import { useLocation, useNavigate } from "react-router-dom";
import JoinTripUnauthenticatedError from "@/errors/JoinTripUnauthenticatedError";

const childrenByErrorId: Record<
  string,
  (error: QueryError) => JSX.Element | undefined
> = {
  [BadRequestResponseError.ERROR_ID]: (error: QueryError) =>
    (error instanceof BadRequestResponseError && (
      <BadRequestModal
        errors={(error as BadRequestResponseError).getErrors()}
        className="mt-1"
      />
    )) ||
    undefined,
};

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
    return customMessages?.[errorId]
      ? t(customMessages[errorId])
      : t(error.getI18nKey());
  };

  const getChildren = (error: QueryError) => {
    const errorId = error.getErrorId();
    const children = childrenByErrorId[errorId];
    return (children && children(error)) || undefined;
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
      timeout: error instanceof BadRequestResponseError ? undefined : timeout,
      title,
      children: getChildren(queryError),
    });

    if (
      (error instanceof UnauthorizedResponseError ||
        error instanceof CurrentUserUriMissingError ||
        error instanceof JoinTripUnauthenticatedError) &&
      location.pathname !== loginPath
    ) {
      navigate(loginPath, { state: { from: location }, replace: true });
    }
  };

  return onQueryError;
};

export default useQueryError;
