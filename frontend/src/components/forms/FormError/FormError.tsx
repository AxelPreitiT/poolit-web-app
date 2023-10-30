import styles from "./styles.module.scss";

interface FormErrorProps {
  error: string | undefined;
}

// This component is used to display the error message of a form field
const FormError = ({ error }: FormErrorProps) =>
  error && (
    <div className={styles.errorContainer}>
      <i className="bi bi-exclamation-circle-fill"></i>
      <span>{error}</span>
    </div>
  );

export default FormError;
