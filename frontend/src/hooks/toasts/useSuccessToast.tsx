import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useSuccessToast = () => {
  const showToast = useToast();

  const showSuccessToast = (i18nKey: string, timeout?: number) => {
    showToast(ToastType.SUCCESS, i18nKey, timeout);
  };

  return showSuccessToast;
};

export default useSuccessToast;
