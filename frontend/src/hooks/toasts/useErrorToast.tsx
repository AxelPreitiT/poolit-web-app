import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useErrorToast = () => {
  const showToast = useToast();

  const showErrorToast = (i18nKey: string, timeout?: number) => {
    showToast(ToastType.ERROR, i18nKey, timeout);
  };

  return showErrorToast;
};

export default useErrorToast;
