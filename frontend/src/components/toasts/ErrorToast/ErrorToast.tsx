import ToastProps from "../ToastProps";
import styles from "./styles.module.scss";
import { Toast, CloseButton } from "react-bootstrap";

const ErrorToast = ({ onClose, timeout, show = true, message }: ToastProps) => (
  <Toast
    show={show}
    delay={timeout}
    autohide={!!timeout}
    className={styles.toast}
    onClose={onClose}
  >
    <Toast.Body className={styles.toastBody}>
      <strong className="light-text">{message}</strong>
      <CloseButton className="btn-close-white" onClick={onClose} />
    </Toast.Body>
  </Toast>
);

export default ErrorToast;
