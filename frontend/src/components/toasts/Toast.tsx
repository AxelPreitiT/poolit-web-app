import ToastProps from "./ToastProps";
import { Toast as BToast } from "react-bootstrap";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ToastType from "@/enums/ToastType";

const toastIconByType: Record<ToastType, string> = {
  [ToastType.ERROR]: "bi-exclamation-circle",
  [ToastType.SUCCESS]: "bi-check-circle",
  [ToastType.WARNING]: "bi-exclamation-triangle",
};

const Toast = ({
  onClose,
  timeout,
  show = true,
  message,
  title,
  type,
  children,
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
          <i className={`bi ${toastIconByType[type]} light-text`} />
          <strong className="light-text">
            {title || t(`toast.${type}.title`)}
          </strong>
        </div>
      </BToast.Header>
      <BToast.Body className={styles.body}>
        <p className="light-text">{message || t(`toast.${type}.message`)}</p>
        {children}
      </BToast.Body>
    </BToast>
  );
};

export default Toast;
