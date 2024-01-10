import Toast from "../Toast";
import ToastProps from "../ToastProps";
import styles from "./styles.module.scss";

const ErrorToast = (props: ToastProps) => (
  <Toast
    {...props}
    styles={{
      toast: styles.errorToast,
      toastHeader: styles.errorToastHeader,
      toastBody: styles.errorToastBody,
    }}
  />
);

export default ErrorToast;
