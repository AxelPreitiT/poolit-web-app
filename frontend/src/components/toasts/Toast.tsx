import ToastProps from "./ToastProps";
import { Toast as BToast } from "react-bootstrap";
import styles from "./styles.module.scss";

const Toast = ({
  onClose,
  timeout,
  show = true,
  message,
  title,
  type,
}: ToastProps) => (
  <BToast
    show={show}
    delay={timeout}
    autohide={!!timeout}
    className={styles[`${type}-toast`]}
    onClose={onClose}
  >
    <BToast.Header
      className={styles.toastHeader}
      closeVariant="white"
      color={styles[`${type}-header`]}
    >
      <div>
        <i className="bi bi-exclamation-circle light-text" />
        <strong className="light-text">{title}</strong>
      </div>
    </BToast.Header>
    <BToast.Body className={styles.body}>
      <span className="light-text">{message}</span>
    </BToast.Body>
  </BToast>
);

export default Toast;
