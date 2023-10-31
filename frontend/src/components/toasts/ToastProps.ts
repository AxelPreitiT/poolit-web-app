export const defaultToastTimeout = 10000;

type ToastProps = {
  message: string;
  title?: string;
  timeout?: number;
  show: boolean;
  onClose: () => void;
};

export default ToastProps;
