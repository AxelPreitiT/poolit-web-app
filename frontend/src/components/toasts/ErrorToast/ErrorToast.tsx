import ToastProps from "../ToastProps";
import styles from "./styles.module.scss";
import { Toast } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const ErrorToast = ({
  onClose,
  timeout,
  show = true,
  message,
  title,
}: ToastProps) => {
  const { t } = useTranslation();

  return (
    <Toast
      show={show}
      delay={timeout}
      autohide={!!timeout}
      className={styles.toast}
      onClose={onClose}
    >
      <Toast.Header className={styles.toastHeader} closeVariant="white">
        <div>
          <i className="bi bi-exclamation-circle light-text" />
          <strong className="light-text">{title || t("error.default")}</strong>
        </div>
      </Toast.Header>
      <Toast.Body className={styles.toastBody}>
        <span className="light-text">{message}</span>
      </Toast.Body>
    </Toast>
  );
};

export default ErrorToast;
