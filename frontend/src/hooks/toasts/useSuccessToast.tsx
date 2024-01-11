import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useSuccessToast = () => {
  const showToast = useToast();

  const showSuccessToast = ({
    message,
    timeout,
    title,
  }: {
    message: string;
    timeout?: number;
    title?: string;
  }) => {
    showToast(ToastType.SUCCESS, { message, timeout, title });
  };

  return showSuccessToast;
};

export default useSuccessToast;
