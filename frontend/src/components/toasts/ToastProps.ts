import { ToastState } from "@/stores/ToastStackStore/ToastStackStore";

export const defaultToastTimeout = 10000;

type ToastProps = ToastState & {
  onClose: () => void;
};

export default ToastProps;
