export const defaultToastTimeout = 10000;

type ToastProps = {
  message: string;
  title?: string;
  timeout?: number;
  show: boolean;
  onClose: () => void;
  styles?: {
    readonly toast: string;
    readonly toastHeader: string;
    readonly toastBody: string;
  };
};

export default ToastProps;
