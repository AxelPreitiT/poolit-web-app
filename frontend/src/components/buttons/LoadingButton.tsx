import { Button } from "react-bootstrap";
import LoadingWheel from "../loading/LoadingWheel";
import styles from "./styles.module.scss";

interface LoadingButtonProps {
  isLoading: boolean;
  children: React.ReactNode;
  className?: string;
  type: "button" | "submit" | "reset" | undefined;
  formId?: string;
  showSpinner?: boolean;
  disabled?: boolean;
}

const LoadingButton = ({
  isLoading,
  children,
  className,
  type,
  formId,
  showSpinner = true,
  disabled = false,
}: LoadingButtonProps) => (
  <Button
    disabled={isLoading || disabled}
    className={className}
    type={type}
    form={formId}
  >
    {isLoading && showSpinner ? (
      <LoadingWheel
        iconClassName={styles.loadingIcon}
        description={null}
        descriptionClassName="visually-hidden"
      />
    ) : (
      children
    )}
  </Button>
);

export default LoadingButton;
