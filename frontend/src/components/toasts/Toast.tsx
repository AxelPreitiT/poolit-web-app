import ToastProps from "./ToastProps";
import { Toast as BToast } from "react-bootstrap";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";

const Toast = ({
  onClose,
  timeout,
  show = true,
  message,
  title,
  type,
}: ToastProps) => {
  const { t } = useTranslation();

  return (
    <BToast
      show={show}
      delay={timeout}
      autohide={!!timeout}
      className={styles[`${type}-toast`]}
      onClose={onClose}
    >
      <BToast.Header closeVariant="white" className={styles[`${type}-header`]}>
        <div>
          <i className="bi bi-exclamation-circle light-text" />
          <strong className="light-text">
            {title || t(`toast.${type}.title`)}
          </strong>
        </div>
      </BToast.Header>
      <BToast.Body className={styles.body}>
        <span className="light-text">
          {message || t(`toast.${type}.message`)}
        </span>
      </BToast.Body>
    </BToast>
  );
};

export default Toast;
