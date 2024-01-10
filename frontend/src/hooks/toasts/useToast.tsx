import ToastType from "@/enums/ToastType";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { useTranslation } from "react-i18next";

const useToast = () => {
  const { t } = useTranslation();
  const addToast = useToastStackStore((state) => state.addToast);

  const showToast = (type: ToastType, i18nKey: string, timeout?: number) => {
    addToast({
      type,
      message: t(i18nKey),
      timeout,
    });
  };

  return showToast;
};

export default useToast;
