import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import {
  CreateCarForm,
  CreateCarFormSchema,
  CreateCarFormSchemaType,
} from "@/forms/CreateCarForm";
import CarService from "@/services/CarService";
import { useNavigateOnCarCreation } from "../cars/useReturnOnCarCreation";
import { profilePath } from "@/AppRouter";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";

const useCreateCarForm = () => {
  const { t } = useTranslation();
  const { discovery, isError: isDiscoveryError } = useDiscovery();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();
  const navigateOnCarCreation = useNavigateOnCarCreation();

  const onSubmit: SubmitHandlerReturnModel<
    CreateCarFormSchemaType,
    void
  > = async (data: CreateCarFormSchemaType) => {
    if (!discovery || isDiscoveryError) {
      throw new DiscoveryMissingError();
    }
    await CarService.createCar(discovery.carsUriTemplate, data);
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("create_car.success.title"),
      message: t("create_car.success.message"),
    });
    navigateOnCarCreation(profilePath);
  };

  const onError = (error: Error) => {
    const title = t("create_car.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: "create_car.error.default",
    };
    onQueryError({ error, title, timeout, customMessages });
  };

  return useForm({
    form: CreateCarForm,
    formSchema: CreateCarFormSchema,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useCreateCarForm;
