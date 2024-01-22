import { Button, Spinner } from "react-bootstrap";

interface LoadingButtonProps {
  isLoading: boolean;
  children: React.ReactNode;
  className?: string;
  spinnerClassName?: string;
  type: "button" | "submit" | "reset" | undefined;
  spinnerSize?: "sm" | undefined;
}

const LoadingButton = ({
  isLoading,
  children,
  className,
  spinnerClassName,
  type,
  spinnerSize,
}: LoadingButtonProps) => (
  <Button disabled={isLoading} className={className} type={type}>
    {isLoading ? (
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
