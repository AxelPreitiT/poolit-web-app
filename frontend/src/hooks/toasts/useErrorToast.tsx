import ToastType from "@/enums/ToastType";
import useToast from "./useToast";

const useErrorToast = () => {
  const showToast = useToast();

  const showErrorToast = ({
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
    showToast(ToastType.ERROR, { message, timeout, title, children });
  };

  return showErrorToast;
};

export default useErrorToast;
