import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useSuccessToast = () => {
  const showToast = useToast();

  const showSuccessToast = ({
    message,
    timeout,
    title,
    children,
  }: {
    message: string;
    timeout?: number;
    title?: string;
    children?: JSX.Element;
  }) => {
    showToast(ToastType.SUCCESS, { message, timeout, title, children });
  };

  return showSuccessToast;
};

export default useSuccessToast;
