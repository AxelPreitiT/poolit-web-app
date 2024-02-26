import UserPrivateModel from "@/models/UserPrivateModel";
import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import {
  EditProfileForm,
  EditProfileFormSchema,
  EditProfileFormSchemaType,
} from "@/forms/EditProfileForm";
import UserService from "@/services/UserService";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import CityModel from "@/models/CityModel";

interface EditProfileFormHookProps {
  user: UserPrivateModel;
  city: CityModel;
  onSuccess?: () => void;
}

const useEditProfileForm = ({
  user,
  city,
  onSuccess: onSuccessProp,
}: EditProfileFormHookProps) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<
    EditProfileFormSchemaType,
    void
  > = async (data: EditProfileFormSchemaType) => {
    return await UserService.updateUser(user, data);
  };

  const onSuccess = () => {
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("edit_profile.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: "edit_profile.error.default",
    };
    onQueryError({ error, title, timeout, customMessages });
  };

  return useForm({
    form: EditProfileForm,
    formSchema: EditProfileFormSchema,
    onSubmit,
    onSuccess,
    onError,
    defaultValues: {
      name: user.username,
      last_name: user.surname,
      telephone: user.phone,
      city: city.id,
      locale: user.mailLocale,
      image: new File([], ""),
    },
  });
};

export default useEditProfileForm;
