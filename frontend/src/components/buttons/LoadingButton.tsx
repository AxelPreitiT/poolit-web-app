import { Button } from "react-bootstrap";
import LoadingWheel from "../loading/LoadingWheel";
import styles from "./styles.module.scss";

interface LoadingButtonProps {
  isLoading: boolean;
  children: React.ReactNode;
  className?: string;
  type: "button" | "submit" | "reset" | undefined;
  showSpinner?: boolean;
}

const LoadingButton = ({
  isLoading,
  children,
  className,
  type,
  showSpinner = true,
}: LoadingButtonProps) => (
  <Button disabled={isLoading} className={className} type={type}>
    {isLoading && showSpinner ? (
      <LoadingWheel
        iconClassName={styles.loadingIcon}
        description=""
        descriptionClassName="visually-hidden"
      />
    ) : (
      children
    )}
  </Button>
);

export default LoadingButton;
