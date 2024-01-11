import ToastType from "@/enums/ToastType";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";

const useToast = () => {
  const addToast = useToastStackStore((state) => state.addToast);

  const showToast = (
    type: ToastType,
    {
      message,
      timeout,
      title,
    }: { message: string; timeout?: number; title?: string }
  ) => {
    addToast({
      type,
      message,
      timeout,
      title,
    });
  };

  return showToast;
};

export default useToast;
