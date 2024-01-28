import CarModel from "@/models/CarModel";
import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import {
  EditCarForm,
  EditCarFormSchema,
  EditCarFormSchemaType,
} from "@/forms/EditCarForm";
import CarService from "@/services/CarService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import CarFeatureModel from "@/models/CarFeatureModel";

interface EditCarFormHookProps {
  car: CarModel;
  carFeatures?: CarFeatureModel[];
  onSuccess?: () => void;
}

const useEditCarForm = ({
  car,
  carFeatures = [],
  onSuccess: onSuccessProp,
}: EditCarFormHookProps) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<
    EditCarFormSchemaType,
    void
  > = async (data: EditCarFormSchemaType) => {
    return await CarService.updateCar(car, data);
  };

  const onSuccess = () => {
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("edit_car.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: "edit_car.error.default",
    };
    onQueryError({ error, title, timeout, customMessages });
  };

  return useForm({
    form: EditCarForm,
    formSchema: EditCarFormSchema,
    onSubmit,
    onSuccess,
    onError,
    defaultValues: {
      car_description: car.infoCar,
      seats: car.seats,
      car_features: carFeatures.map((feature) => feature.id),
      image: new File([], ""),
    },
  });
};

export default useEditCarForm;
