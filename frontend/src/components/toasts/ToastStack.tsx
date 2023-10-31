import ToastContainer from "react-bootstrap/ToastContainer";

interface ToastStackProps {
  position:
    | "top-start"
    | "top-center"
    | "top-end"
    | "middle-start"
    | "middle-center"
    | "middle-end"
    | "bottom-start"
    | "bottom-center"
    | "bottom-end";
  toasts: React.ReactNode[];
  className?: string;
}

const ToastStack = ({ position, toasts, className }: ToastStackProps) => (
  <ToastContainer className={className} position={position}>
    {toasts}
  </ToastContainer>
);

export default ToastStack;
