import { useTranslation } from "react-i18next";
import ToastProps from "./ToastProps";
import { Toast as BToast } from "react-bootstrap";

const Toast = ({
  onClose,
  timeout,
  show = true,
  message,
  title,
  styles = {
    toast: "",
    toastHeader: "",
    toastBody: "",
  },
}: ToastProps) => {
  const { t } = useTranslation();

  return (
    <BToast
      show={show}
      delay={timeout}
      autohide={!!timeout}
      className={styles.toast}
      onClose={onClose}
    >
      <BToast.Header className={styles.toastHeader} closeVariant="white">
        <div>
          <i className="bi bi-exclamation-circle light-text" />
          <strong className="light-text">{title || t("error.title")}</strong>
        </div>
      </BToast.Header>
      <BToast.Body className={styles.toastBody}>
        <span className="light-text">{message}</span>
      </BToast.Body>
    </BToast>
  );
};

export default Toast;
