import ToastType from "@/enums/ToastType";

export const defaultToastTimeout = 10000;

type ToastProps = {
  message: string;
  title?: string;
  timeout?: number;
  show: boolean;
  onClose: () => void;
  type: ToastType;
};

export default ToastProps;
