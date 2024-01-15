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
      children,
    }: {
      message: string;
      timeout?: number;
      title?: string;
      children?: JSX.Element;
    }
  ) => {
    addToast({
      type,
      message,
      timeout,
      title,
      children,
    });
  };

  return showToast;
};

export default useToast;
