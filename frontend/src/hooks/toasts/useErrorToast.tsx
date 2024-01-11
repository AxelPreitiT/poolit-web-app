import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useErrorToast = () => {
  const showToast = useToast();

  const showErrorToast = ({
    message,
    timeout,
    title,
  }: {
    message: string;
    timeout?: number;
    title?: string;
  }) => {
    showToast(ToastType.ERROR, { message, timeout, title });
  };

  return showErrorToast;
};

export default useErrorToast;
