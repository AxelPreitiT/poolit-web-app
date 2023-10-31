import styles from "./styles.module.scss";
import { Toast, CloseButton } from "react-bootstrap";

interface ErrorToastProps {
  onClose?: () => void;
  timer?: number;
  show?: boolean;
  message: string;
}

const ErrorToast = ({
  onClose,
  timer,
  show = true,
  message,
}: ErrorToastProps) => (
  <Toast
    show={show}
    delay={timer}
    autohide={!!timer}
    className={styles.toast}
    onClose={onClose}
  >
    <Toast.Body className={styles.toastBody}>
      <strong className="light-text">{message}</strong>
      <CloseButton variant="white" onClick={onClose} />
    </Toast.Body>
  </Toast>
);

export default ErrorToast;
