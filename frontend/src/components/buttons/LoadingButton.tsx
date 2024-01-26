import { Button, Spinner } from "react-bootstrap";

interface LoadingButtonProps {
  isLoading: boolean;
  children: React.ReactNode;
  className?: string;
  spinnerClassName?: string;
  type: "button" | "submit" | "reset" | undefined;
  spinnerSize?: "sm" | undefined;
  showSpinner?: boolean;
}

const LoadingButton = ({
  isLoading,
  children,
  className,
  spinnerClassName,
  type,
  spinnerSize,
  showSpinner = true,
}: LoadingButtonProps) => (
  <Button disabled={isLoading} className={className} type={type}>
    {isLoading && showSpinner ? (
      <Spinner
        className={spinnerClassName}
        animation="border"
        size={spinnerSize}
      />
    ) : (
      children
    )}
  </Button>
);

export default LoadingButton;
