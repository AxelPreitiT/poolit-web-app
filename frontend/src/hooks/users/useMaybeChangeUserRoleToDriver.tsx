import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useCurrentUser } from "./useCurrentUser";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import ConflictResponseError from "@/errors/ConflictResponseError";
import { useMutation } from "@tanstack/react-query";
import UsersRoles from "@/enums/UsersRoles";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";
import UserService from "@/services/UserService";

interface MaybeChangeUserRoleToDriver {
  onSuccess?: () => void;
  onError?: (error: Error) => void;
  onIgnore?: () => void;
}

const useMaybeChangeUserRoleToDriver = ({
  onSuccess: onSuccessProp,
  onError: onErrorProp,
  onIgnore: onIgnoreProp,
}: MaybeChangeUserRoleToDriver = {}) => {
  const { t } = useTranslation();
  const { discovery, isError: isDiscoveryError } = useDiscovery();
  const { currentUser, invalidate: invalidateCurrentUser } = useCurrentUser();
  const onQueryError = useQueryError();

  const onSuccess = (tried: boolean) => {
    if (tried) {
      invalidateCurrentUser();
      onSuccessProp?.();
    } else {
      onIgnoreProp?.();
    }
  };

  const onError = (error: Error) => {
    const title = t("change_role.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: "change_role.error.default",
      [ConflictResponseError.ERROR_ID]: "change_role.error.conflict",
    };
    onQueryError({ error, timeout, title, customMessages });
    onErrorProp?.(error);
  };

  const mutation = useMutation({
    mutationFn: async () => {
      if (!discovery || isDiscoveryError) {
        throw new DiscoveryMissingError();
      }
      if (!currentUser || currentUser.role !== UsersRoles.USER) {
        return false;
      }
      await UserService.updateUserRoleToDriver(
        discovery.usersUriTemplate,
        currentUser
      );
      return true;
    },
    onError,
    onSuccess,
  });

  const maybeChangeUserRoleToDriver = async () => {
    await mutation.mutateAsync();
  };

  const isLoading = mutation.isPending;

  return {
    maybeChangeUserRoleToDriver,
    isLoading,
  };
};

export default useMaybeChangeUserRoleToDriver;
